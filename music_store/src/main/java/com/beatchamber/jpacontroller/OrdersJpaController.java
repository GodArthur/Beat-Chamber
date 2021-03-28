package com.beatchamber.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.beatchamber.entities.Clients;
import com.beatchamber.entities.Orders;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.exceptions.NonexistentEntityException;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Massimo Di Girolamo
 */
@Named
@SessionScoped
public class OrdersJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(OrdersJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "music_store_persistence")
    private EntityManager em;

    public OrdersJpaController() {
    }

    public void create(Orders orders) throws RollbackFailureException {

        try {
            utx.begin();
            Clients clientNumber = orders.getClientNumber();
            if (clientNumber != null) {
                clientNumber = em.getReference(clientNumber.getClass(), clientNumber.getClientNumber());
                orders.setClientNumber(clientNumber);
            }
            em.persist(orders);
            if (clientNumber != null) {
                clientNumber.getOrdersCollection().add(orders);
                clientNumber = em.merge(clientNumber);
            }
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {

                utx.rollback();
                LOG.error("Rollback");
            } catch (IllegalStateException | SecurityException | SystemException re) {
                LOG.error("Rollback2");

                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
        }
    }

    public void edit(Orders orders) throws NonexistentEntityException, Exception {

        try {
            utx.begin();
            Orders persistentOrders = em.find(Orders.class, orders.getOrderId());
            Clients clientNumberOld = persistentOrders.getClientNumber();
            Clients clientNumberNew = orders.getClientNumber();
            if (clientNumberNew != null) {
                clientNumberNew = em.getReference(clientNumberNew.getClass(), clientNumberNew.getClientNumber());
                orders.setClientNumber(clientNumberNew);
            }
            orders = em.merge(orders);
            if (clientNumberOld != null && !clientNumberOld.equals(clientNumberNew)) {
                clientNumberOld.getOrdersCollection().remove(orders);
                clientNumberOld = em.merge(clientNumberOld);
            }
            if (clientNumberNew != null && !clientNumberNew.equals(clientNumberOld)) {
                clientNumberNew.getOrdersCollection().add(orders);
                clientNumberNew = em.merge(clientNumberNew);
            }
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orders.getOrderId();
                if (findOrders(id) == null) {
                    throw new com.beatchamber.exceptions.NonexistentEntityException("The album with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, com.beatchamber.exceptions.NonexistentEntityException, NotSupportedException, SystemException, RollbackFailureException, RollbackException, HeuristicMixedException, HeuristicRollbackException, NonexistentEntityException {

        try {
            utx.begin();
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            Clients clientNumber = orders.getClientNumber();
            if (clientNumber != null) {
                clientNumber.getOrdersCollection().remove(orders);
                clientNumber = em.merge(clientNumber);
            }
            em.remove(orders);
            utx.commit();
        } catch (NotSupportedException | SystemException | com.beatchamber.exceptions.NonexistentEntityException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }
    
    /**
     * This method will return the number of orders placed and also the number id 
     * @return int
     */
    public int findTotalOrders() {
        return findOrdersEntities(true, -1, -1).size();
    }
    
    public String addOrdersToTable(){
        
        return "index.xhtml";
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Orders.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Orders findOrders(Integer id) {
        return em.find(Orders.class, id);

    }

    public int getOrdersCount() {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Orders> rt = cq.from(Orders.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
