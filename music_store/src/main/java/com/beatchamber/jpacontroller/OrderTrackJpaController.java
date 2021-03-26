package com.beatchamber.jpacontroller;

import com.beatchamber.entities.OrderTrack;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.beatchamber.entities.Tracks;
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
public class OrderTrackJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(OrderTrackJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "music_store_persistence")
    private EntityManager em;

    public OrderTrackJpaController() {
    }

    public void create(OrderTrack orderTrack) throws RollbackFailureException {

        try {
            utx.begin();
            Tracks trackId = orderTrack.getTrackId();
            if (trackId != null) {
                trackId = em.getReference(trackId.getClass(), trackId.getTrackId());
                orderTrack.setTrackId(trackId);
            }
            em.persist(orderTrack);
            if (trackId != null) {
                trackId.getOrderTrackCollection().add(orderTrack);
                trackId = em.merge(trackId);
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

    public void edit(OrderTrack orderTrack) throws NonexistentEntityException, Exception {

        try {
            utx.begin();
            OrderTrack persistentOrderTrack = em.find(OrderTrack.class, orderTrack.getOrderId());
            Tracks trackIdOld = persistentOrderTrack.getTrackId();
            Tracks trackIdNew = orderTrack.getTrackId();
            if (trackIdNew != null) {
                trackIdNew = em.getReference(trackIdNew.getClass(), trackIdNew.getTrackId());
                orderTrack.setTrackId(trackIdNew);
            }
            orderTrack = em.merge(orderTrack);
            if (trackIdOld != null && !trackIdOld.equals(trackIdNew)) {
                trackIdOld.getOrderTrackCollection().remove(orderTrack);
                trackIdOld = em.merge(trackIdOld);
            }
            if (trackIdNew != null && !trackIdNew.equals(trackIdOld)) {
                trackIdNew.getOrderTrackCollection().add(orderTrack);
                trackIdNew = em.merge(trackIdNew);
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
                Integer id = orderTrack.getOrderId();
                if (findOrderTrack(id) == null) {
                    throw new com.beatchamber.exceptions.NonexistentEntityException("The album with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, com.beatchamber.exceptions.NonexistentEntityException, NotSupportedException, SystemException, RollbackFailureException, RollbackException, HeuristicMixedException, HeuristicRollbackException, NonexistentEntityException {

        try {
            utx.begin();
            OrderTrack orderTrack;
            try {
                orderTrack = em.getReference(OrderTrack.class, id);
                orderTrack.getOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderTrack with id " + id + " no longer exists.", enfe);
            }
            Tracks trackId = orderTrack.getTrackId();
            if (trackId != null) {
                trackId.getOrderTrackCollection().remove(orderTrack);
                trackId = em.merge(trackId);
            }
            em.remove(orderTrack);
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

    public List<OrderTrack> findOrderTrackEntities() {
        return findOrderTrackEntities(true, -1, -1);
    }

    public List<OrderTrack> findOrderTrackEntities(int maxResults, int firstResult) {
        return findOrderTrackEntities(false, maxResults, firstResult);
    }

    private List<OrderTrack> findOrderTrackEntities(boolean all, int maxResults, int firstResult) {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(OrderTrack.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public OrderTrack findOrderTrack(Integer id) {
        return em.find(OrderTrack.class, id);
    }

    public int getOrderTrackCount() {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<OrderTrack> rt = cq.from(OrderTrack.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
