/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.beatchamber.entities.Albums;
import com.beatchamber.entities.OrderAlbum;
import com.beatchamber.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kibra
 */
public class OrderAlbumJpaController implements Serializable {

    public OrderAlbumJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderAlbum orderAlbum) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrderAlbum orderAlbum) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orderAlbum.getOrderId();
                if (findOrderAlbum(id) == null) {
                    throw new NonexistentEntityException("The orderAlbum with id " + id + " no longer exists.");
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
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrderAlbum> findOrderAlbumEntities() {
        return findOrderAlbumEntities(true, -1, -1);
    }

    public List<OrderAlbum> findOrderAlbumEntities(int maxResults, int firstResult) {
        return findOrderAlbumEntities(false, maxResults, firstResult);
    }

    private List<OrderAlbum> findOrderAlbumEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderAlbum.class));
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

    public OrderAlbum findOrderAlbum(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderAlbum.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderAlbumCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderAlbum> rt = cq.from(OrderAlbum.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
