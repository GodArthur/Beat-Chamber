/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber;

import com.beatchamber.entities.OrderTrack;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.beatchamber.entities.Tracks;
import com.beatchamber.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kibra
 */
public class OrderTrackJpaController implements Serializable {

    public OrderTrackJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderTrack orderTrack) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrderTrack orderTrack) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderTrack persistentOrderTrack = em.find(OrderTrack.class, orderTrack.getTablekey());
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
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orderTrack.getTablekey();
                if (findOrderTrack(id) == null) {
                    throw new NonexistentEntityException("The orderTrack with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrderTrack orderTrack;
            try {
                orderTrack = em.getReference(OrderTrack.class, id);
                orderTrack.getTablekey();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderTrack with id " + id + " no longer exists.", enfe);
            }
            Tracks trackId = orderTrack.getTrackId();
            if (trackId != null) {
                trackId.getOrderTrackCollection().remove(orderTrack);
                trackId = em.merge(trackId);
            }
            em.remove(orderTrack);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrderTrack> findOrderTrackEntities() {
        return findOrderTrackEntities(true, -1, -1);
    }

    public List<OrderTrack> findOrderTrackEntities(int maxResults, int firstResult) {
        return findOrderTrackEntities(false, maxResults, firstResult);
    }

    private List<OrderTrack> findOrderTrackEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderTrack.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public OrderTrack findOrderTrack(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderTrack.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderTrackCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderTrack> rt = cq.from(OrderTrack.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
