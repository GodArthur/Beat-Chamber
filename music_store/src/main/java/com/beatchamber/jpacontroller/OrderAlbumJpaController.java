package com.beatchamber.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.beatchamber.entities.Albums;
import com.beatchamber.entities.OrderAlbum;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.exceptions.NonexistentEntityException;
import java.util.List;
import javax.annotation.Resource;
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
public class OrderAlbumJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(OrderAlbumJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "music_store_persistence")
    private EntityManager em;

    public OrderAlbumJpaController() {
    }

    public void create(OrderAlbum orderAlbum) throws RollbackFailureException {

        try {

            utx.begin();
            Albums albumId = orderAlbum.getAlbumId();
            if (albumId != null) {
                albumId = em.getReference(albumId.getClass(), albumId.getAlbumNumber());
                orderAlbum.setAlbumId(albumId);
            }
            em.persist(orderAlbum);
            if (albumId != null) {
                albumId.getOrderAlbumCollection().add(orderAlbum);
                albumId = em.merge(albumId);
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
    
    public void create(OrderAlbum orderAlbum,EntityManager em2,UserTransaction utx2) throws RollbackFailureException {

        try {

            utx2.begin();
            Albums albumId = orderAlbum.getAlbumId();
            if (albumId != null) {
                albumId = em2.getReference(albumId.getClass(), albumId.getAlbumNumber());
                orderAlbum.setAlbumId(albumId);
            }
            em2.persist(orderAlbum);
            if (albumId != null) {
                albumId.getOrderAlbumCollection().add(orderAlbum);
                albumId = em2.merge(albumId);
            }
            utx2.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {

                utx2.rollback();
                LOG.error("Rollback");
            } catch (IllegalStateException | SecurityException | SystemException re) {
                LOG.error("Rollback2");

                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
        }
    }

    public void edit(OrderAlbum orderAlbum) throws NonexistentEntityException, Exception {

        try {
            utx.begin();
            OrderAlbum persistentOrderAlbum = em.find(OrderAlbum.class, orderAlbum.getOrderId());
            Albums albumIdOld = persistentOrderAlbum.getAlbumId();
            Albums albumIdNew = orderAlbum.getAlbumId();
            if (albumIdNew != null) {
                albumIdNew = em.getReference(albumIdNew.getClass(), albumIdNew.getAlbumNumber());
                orderAlbum.setAlbumId(albumIdNew);
            }
            orderAlbum = em.merge(orderAlbum);
            if (albumIdOld != null && !albumIdOld.equals(albumIdNew)) {
                albumIdOld.getOrderAlbumCollection().remove(orderAlbum);
                albumIdOld = em.merge(albumIdOld);
            }
            if (albumIdNew != null && !albumIdNew.equals(albumIdOld)) {
                albumIdNew.getOrderAlbumCollection().add(orderAlbum);
                albumIdNew = em.merge(albumIdNew);
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
                Integer id = orderAlbum.getOrderId();
                if (findOrderAlbum(id) == null) {
                    throw new com.beatchamber.exceptions.NonexistentEntityException("The album with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, com.beatchamber.exceptions.NonexistentEntityException, NotSupportedException, SystemException, RollbackFailureException, RollbackException, HeuristicMixedException, HeuristicRollbackException, NonexistentEntityException {

        try {
            utx.begin();
            OrderAlbum orderAlbum;
            try {
                orderAlbum = em.getReference(OrderAlbum.class, id);
                orderAlbum.getOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderAlbum with id " + id + " no longer exists.", enfe);
            }
            Albums albumId = orderAlbum.getAlbumId();
            if (albumId != null) {
                albumId.getOrderAlbumCollection().remove(orderAlbum);
                albumId = em.merge(albumId);
            }
            em.remove(orderAlbum);
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

    public List<OrderAlbum> findOrderAlbumEntities() {
        return findOrderAlbumEntities(true, -1, -1);
    }

    public List<OrderAlbum> findOrderAlbumEntities(int maxResults, int firstResult) {
        return findOrderAlbumEntities(false, maxResults, firstResult);
    }

    private List<OrderAlbum> findOrderAlbumEntities(boolean all, int maxResults, int firstResult) {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(OrderAlbum.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public OrderAlbum findOrderAlbum(Integer id) {
        return em.find(OrderAlbum.class, id);
    }

    public int getOrderAlbumCount() {
        
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<OrderAlbum> rt = cq.from(OrderAlbum.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }
}
