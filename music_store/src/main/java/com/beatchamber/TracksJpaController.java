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
import com.beatchamber.entities.InvoiceDetails;
import java.util.ArrayList;
import java.util.List;
import com.beatchamber.entities.CustomerReviews;
import com.beatchamber.entities.GenreToTracks;
import com.beatchamber.entities.OrderTrack;
import com.beatchamber.entities.Tracks;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kibra
 */
public class TracksJpaController implements Serializable {

    public TracksJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tracks tracks) {
        if (tracks.getInvoiceDetailsList() == null) {
            tracks.setInvoiceDetailsList(new ArrayList<InvoiceDetails>());
        }
        if (tracks.getCustomerReviewsList() == null) {
            tracks.setCustomerReviewsList(new ArrayList<CustomerReviews>());
        }
        if (tracks.getGenreToTracksList() == null) {
            tracks.setGenreToTracksList(new ArrayList<GenreToTracks>());
        }
        if (tracks.getOrderTrackCollection() == null) {
            tracks.setOrderTrackCollection(new ArrayList<OrderTrack>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Albums albumNumber = tracks.getAlbumNumber();
            if (albumNumber != null) {
                albumNumber = em.getReference(albumNumber.getClass(), albumNumber.getAlbumNumber());
                tracks.setAlbumNumber(albumNumber);
            }
            List<InvoiceDetails> attachedInvoiceDetailsList = new ArrayList<InvoiceDetails>();
            for (InvoiceDetails invoiceDetailsListInvoiceDetailsToAttach : tracks.getInvoiceDetailsList()) {
                invoiceDetailsListInvoiceDetailsToAttach = em.getReference(invoiceDetailsListInvoiceDetailsToAttach.getClass(), invoiceDetailsListInvoiceDetailsToAttach.getTablekey());
                attachedInvoiceDetailsList.add(invoiceDetailsListInvoiceDetailsToAttach);
            }
            tracks.setInvoiceDetailsList(attachedInvoiceDetailsList);
            List<CustomerReviews> attachedCustomerReviewsList = new ArrayList<CustomerReviews>();
            for (CustomerReviews customerReviewsListCustomerReviewsToAttach : tracks.getCustomerReviewsList()) {
                customerReviewsListCustomerReviewsToAttach = em.getReference(customerReviewsListCustomerReviewsToAttach.getClass(), customerReviewsListCustomerReviewsToAttach.getReviewNumber());
                attachedCustomerReviewsList.add(customerReviewsListCustomerReviewsToAttach);
            }
            tracks.setCustomerReviewsList(attachedCustomerReviewsList);
            List<GenreToTracks> attachedGenreToTracksList = new ArrayList<GenreToTracks>();
            for (GenreToTracks genreToTracksListGenreToTracksToAttach : tracks.getGenreToTracksList()) {
                genreToTracksListGenreToTracksToAttach = em.getReference(genreToTracksListGenreToTracksToAttach.getClass(), genreToTracksListGenreToTracksToAttach.getTablekey());
                attachedGenreToTracksList.add(genreToTracksListGenreToTracksToAttach);
            }
            tracks.setGenreToTracksList(attachedGenreToTracksList);
            Collection<OrderTrack> attachedOrderTrackCollection = new ArrayList<OrderTrack>();
            for (OrderTrack orderTrackCollectionOrderTrackToAttach : tracks.getOrderTrackCollection()) {
                orderTrackCollectionOrderTrackToAttach = em.getReference(orderTrackCollectionOrderTrackToAttach.getClass(), orderTrackCollectionOrderTrackToAttach.getTablekey());
                attachedOrderTrackCollection.add(orderTrackCollectionOrderTrackToAttach);
            }
            tracks.setOrderTrackCollection(attachedOrderTrackCollection);
            em.persist(tracks);
            if (albumNumber != null) {
                albumNumber.getTracksList().add(tracks);
                albumNumber = em.merge(albumNumber);
            }
            for (InvoiceDetails invoiceDetailsListInvoiceDetails : tracks.getInvoiceDetailsList()) {
                Tracks oldTrackIdOfInvoiceDetailsListInvoiceDetails = invoiceDetailsListInvoiceDetails.getTrackId();
                invoiceDetailsListInvoiceDetails.setTrackId(tracks);
                invoiceDetailsListInvoiceDetails = em.merge(invoiceDetailsListInvoiceDetails);
                if (oldTrackIdOfInvoiceDetailsListInvoiceDetails != null) {
                    oldTrackIdOfInvoiceDetailsListInvoiceDetails.getInvoiceDetailsList().remove(invoiceDetailsListInvoiceDetails);
                    oldTrackIdOfInvoiceDetailsListInvoiceDetails = em.merge(oldTrackIdOfInvoiceDetailsListInvoiceDetails);
                }
            }
            for (CustomerReviews customerReviewsListCustomerReviews : tracks.getCustomerReviewsList()) {
                Tracks oldTrackIdOfCustomerReviewsListCustomerReviews = customerReviewsListCustomerReviews.getTrackId();
                customerReviewsListCustomerReviews.setTrackId(tracks);
                customerReviewsListCustomerReviews = em.merge(customerReviewsListCustomerReviews);
                if (oldTrackIdOfCustomerReviewsListCustomerReviews != null) {
                    oldTrackIdOfCustomerReviewsListCustomerReviews.getCustomerReviewsList().remove(customerReviewsListCustomerReviews);
                    oldTrackIdOfCustomerReviewsListCustomerReviews = em.merge(oldTrackIdOfCustomerReviewsListCustomerReviews);
                }
            }
            for (GenreToTracks genreToTracksListGenreToTracks : tracks.getGenreToTracksList()) {
                Tracks oldTrackIdOfGenreToTracksListGenreToTracks = genreToTracksListGenreToTracks.getTrackId();
                genreToTracksListGenreToTracks.setTrackId(tracks);
                genreToTracksListGenreToTracks = em.merge(genreToTracksListGenreToTracks);
                if (oldTrackIdOfGenreToTracksListGenreToTracks != null) {
                    oldTrackIdOfGenreToTracksListGenreToTracks.getGenreToTracksList().remove(genreToTracksListGenreToTracks);
                    oldTrackIdOfGenreToTracksListGenreToTracks = em.merge(oldTrackIdOfGenreToTracksListGenreToTracks);
                }
            }
            for (OrderTrack orderTrackCollectionOrderTrack : tracks.getOrderTrackCollection()) {
                Tracks oldTrackIdOfOrderTrackCollectionOrderTrack = orderTrackCollectionOrderTrack.getTrackId();
                orderTrackCollectionOrderTrack.setTrackId(tracks);
                orderTrackCollectionOrderTrack = em.merge(orderTrackCollectionOrderTrack);
                if (oldTrackIdOfOrderTrackCollectionOrderTrack != null) {
                    oldTrackIdOfOrderTrackCollectionOrderTrack.getOrderTrackCollection().remove(orderTrackCollectionOrderTrack);
                    oldTrackIdOfOrderTrackCollectionOrderTrack = em.merge(oldTrackIdOfOrderTrackCollectionOrderTrack);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tracks tracks) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tracks persistentTracks = em.find(Tracks.class, tracks.getTrackId());
            Albums albumNumberOld = persistentTracks.getAlbumNumber();
            Albums albumNumberNew = tracks.getAlbumNumber();
            List<InvoiceDetails> invoiceDetailsListOld = persistentTracks.getInvoiceDetailsList();
            List<InvoiceDetails> invoiceDetailsListNew = tracks.getInvoiceDetailsList();
            List<CustomerReviews> customerReviewsListOld = persistentTracks.getCustomerReviewsList();
            List<CustomerReviews> customerReviewsListNew = tracks.getCustomerReviewsList();
            List<GenreToTracks> genreToTracksListOld = persistentTracks.getGenreToTracksList();
            List<GenreToTracks> genreToTracksListNew = tracks.getGenreToTracksList();
            Collection<OrderTrack> orderTrackCollectionOld = persistentTracks.getOrderTrackCollection();
            Collection<OrderTrack> orderTrackCollectionNew = tracks.getOrderTrackCollection();
            List<String> illegalOrphanMessages = null;
            for (InvoiceDetails invoiceDetailsListOldInvoiceDetails : invoiceDetailsListOld) {
                if (!invoiceDetailsListNew.contains(invoiceDetailsListOldInvoiceDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InvoiceDetails " + invoiceDetailsListOldInvoiceDetails + " since its trackId field is not nullable.");
                }
            }
            for (CustomerReviews customerReviewsListOldCustomerReviews : customerReviewsListOld) {
                if (!customerReviewsListNew.contains(customerReviewsListOldCustomerReviews)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CustomerReviews " + customerReviewsListOldCustomerReviews + " since its trackId field is not nullable.");
                }
            }
            for (OrderTrack orderTrackCollectionOldOrderTrack : orderTrackCollectionOld) {
                if (!orderTrackCollectionNew.contains(orderTrackCollectionOldOrderTrack)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderTrack " + orderTrackCollectionOldOrderTrack + " since its trackId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (albumNumberNew != null) {
                albumNumberNew = em.getReference(albumNumberNew.getClass(), albumNumberNew.getAlbumNumber());
                tracks.setAlbumNumber(albumNumberNew);
            }
            List<InvoiceDetails> attachedInvoiceDetailsListNew = new ArrayList<InvoiceDetails>();
            for (InvoiceDetails invoiceDetailsListNewInvoiceDetailsToAttach : invoiceDetailsListNew) {
                invoiceDetailsListNewInvoiceDetailsToAttach = em.getReference(invoiceDetailsListNewInvoiceDetailsToAttach.getClass(), invoiceDetailsListNewInvoiceDetailsToAttach.getTablekey());
                attachedInvoiceDetailsListNew.add(invoiceDetailsListNewInvoiceDetailsToAttach);
            }
            invoiceDetailsListNew = attachedInvoiceDetailsListNew;
            tracks.setInvoiceDetailsList(invoiceDetailsListNew);
            List<CustomerReviews> attachedCustomerReviewsListNew = new ArrayList<CustomerReviews>();
            for (CustomerReviews customerReviewsListNewCustomerReviewsToAttach : customerReviewsListNew) {
                customerReviewsListNewCustomerReviewsToAttach = em.getReference(customerReviewsListNewCustomerReviewsToAttach.getClass(), customerReviewsListNewCustomerReviewsToAttach.getReviewNumber());
                attachedCustomerReviewsListNew.add(customerReviewsListNewCustomerReviewsToAttach);
            }
            customerReviewsListNew = attachedCustomerReviewsListNew;
            tracks.setCustomerReviewsList(customerReviewsListNew);
            List<GenreToTracks> attachedGenreToTracksListNew = new ArrayList<GenreToTracks>();
            for (GenreToTracks genreToTracksListNewGenreToTracksToAttach : genreToTracksListNew) {
                genreToTracksListNewGenreToTracksToAttach = em.getReference(genreToTracksListNewGenreToTracksToAttach.getClass(), genreToTracksListNewGenreToTracksToAttach.getTablekey());
                attachedGenreToTracksListNew.add(genreToTracksListNewGenreToTracksToAttach);
            }
            genreToTracksListNew = attachedGenreToTracksListNew;
            tracks.setGenreToTracksList(genreToTracksListNew);
            Collection<OrderTrack> attachedOrderTrackCollectionNew = new ArrayList<OrderTrack>();
            for (OrderTrack orderTrackCollectionNewOrderTrackToAttach : orderTrackCollectionNew) {
                orderTrackCollectionNewOrderTrackToAttach = em.getReference(orderTrackCollectionNewOrderTrackToAttach.getClass(), orderTrackCollectionNewOrderTrackToAttach.getTablekey());
                attachedOrderTrackCollectionNew.add(orderTrackCollectionNewOrderTrackToAttach);
            }
            orderTrackCollectionNew = attachedOrderTrackCollectionNew;
            tracks.setOrderTrackCollection(orderTrackCollectionNew);
            tracks = em.merge(tracks);
            if (albumNumberOld != null && !albumNumberOld.equals(albumNumberNew)) {
                albumNumberOld.getTracksList().remove(tracks);
                albumNumberOld = em.merge(albumNumberOld);
            }
            if (albumNumberNew != null && !albumNumberNew.equals(albumNumberOld)) {
                albumNumberNew.getTracksList().add(tracks);
                albumNumberNew = em.merge(albumNumberNew);
            }
            for (InvoiceDetails invoiceDetailsListNewInvoiceDetails : invoiceDetailsListNew) {
                if (!invoiceDetailsListOld.contains(invoiceDetailsListNewInvoiceDetails)) {
                    Tracks oldTrackIdOfInvoiceDetailsListNewInvoiceDetails = invoiceDetailsListNewInvoiceDetails.getTrackId();
                    invoiceDetailsListNewInvoiceDetails.setTrackId(tracks);
                    invoiceDetailsListNewInvoiceDetails = em.merge(invoiceDetailsListNewInvoiceDetails);
                    if (oldTrackIdOfInvoiceDetailsListNewInvoiceDetails != null && !oldTrackIdOfInvoiceDetailsListNewInvoiceDetails.equals(tracks)) {
                        oldTrackIdOfInvoiceDetailsListNewInvoiceDetails.getInvoiceDetailsList().remove(invoiceDetailsListNewInvoiceDetails);
                        oldTrackIdOfInvoiceDetailsListNewInvoiceDetails = em.merge(oldTrackIdOfInvoiceDetailsListNewInvoiceDetails);
                    }
                }
            }
            for (CustomerReviews customerReviewsListNewCustomerReviews : customerReviewsListNew) {
                if (!customerReviewsListOld.contains(customerReviewsListNewCustomerReviews)) {
                    Tracks oldTrackIdOfCustomerReviewsListNewCustomerReviews = customerReviewsListNewCustomerReviews.getTrackId();
                    customerReviewsListNewCustomerReviews.setTrackId(tracks);
                    customerReviewsListNewCustomerReviews = em.merge(customerReviewsListNewCustomerReviews);
                    if (oldTrackIdOfCustomerReviewsListNewCustomerReviews != null && !oldTrackIdOfCustomerReviewsListNewCustomerReviews.equals(tracks)) {
                        oldTrackIdOfCustomerReviewsListNewCustomerReviews.getCustomerReviewsList().remove(customerReviewsListNewCustomerReviews);
                        oldTrackIdOfCustomerReviewsListNewCustomerReviews = em.merge(oldTrackIdOfCustomerReviewsListNewCustomerReviews);
                    }
                }
            }
            for (GenreToTracks genreToTracksListOldGenreToTracks : genreToTracksListOld) {
                if (!genreToTracksListNew.contains(genreToTracksListOldGenreToTracks)) {
                    genreToTracksListOldGenreToTracks.setTrackId(null);
                    genreToTracksListOldGenreToTracks = em.merge(genreToTracksListOldGenreToTracks);
                }
            }
            for (GenreToTracks genreToTracksListNewGenreToTracks : genreToTracksListNew) {
                if (!genreToTracksListOld.contains(genreToTracksListNewGenreToTracks)) {
                    Tracks oldTrackIdOfGenreToTracksListNewGenreToTracks = genreToTracksListNewGenreToTracks.getTrackId();
                    genreToTracksListNewGenreToTracks.setTrackId(tracks);
                    genreToTracksListNewGenreToTracks = em.merge(genreToTracksListNewGenreToTracks);
                    if (oldTrackIdOfGenreToTracksListNewGenreToTracks != null && !oldTrackIdOfGenreToTracksListNewGenreToTracks.equals(tracks)) {
                        oldTrackIdOfGenreToTracksListNewGenreToTracks.getGenreToTracksList().remove(genreToTracksListNewGenreToTracks);
                        oldTrackIdOfGenreToTracksListNewGenreToTracks = em.merge(oldTrackIdOfGenreToTracksListNewGenreToTracks);
                    }
                }
            }
            for (OrderTrack orderTrackCollectionNewOrderTrack : orderTrackCollectionNew) {
                if (!orderTrackCollectionOld.contains(orderTrackCollectionNewOrderTrack)) {
                    Tracks oldTrackIdOfOrderTrackCollectionNewOrderTrack = orderTrackCollectionNewOrderTrack.getTrackId();
                    orderTrackCollectionNewOrderTrack.setTrackId(tracks);
                    orderTrackCollectionNewOrderTrack = em.merge(orderTrackCollectionNewOrderTrack);
                    if (oldTrackIdOfOrderTrackCollectionNewOrderTrack != null && !oldTrackIdOfOrderTrackCollectionNewOrderTrack.equals(tracks)) {
                        oldTrackIdOfOrderTrackCollectionNewOrderTrack.getOrderTrackCollection().remove(orderTrackCollectionNewOrderTrack);
                        oldTrackIdOfOrderTrackCollectionNewOrderTrack = em.merge(oldTrackIdOfOrderTrackCollectionNewOrderTrack);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tracks.getTrackId();
                if (findTracks(id) == null) {
                    throw new NonexistentEntityException("The tracks with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tracks tracks;
            try {
                tracks = em.getReference(Tracks.class, id);
                tracks.getTrackId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tracks with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<InvoiceDetails> invoiceDetailsListOrphanCheck = tracks.getInvoiceDetailsList();
            for (InvoiceDetails invoiceDetailsListOrphanCheckInvoiceDetails : invoiceDetailsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tracks (" + tracks + ") cannot be destroyed since the InvoiceDetails " + invoiceDetailsListOrphanCheckInvoiceDetails + " in its invoiceDetailsList field has a non-nullable trackId field.");
            }
            List<CustomerReviews> customerReviewsListOrphanCheck = tracks.getCustomerReviewsList();
            for (CustomerReviews customerReviewsListOrphanCheckCustomerReviews : customerReviewsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tracks (" + tracks + ") cannot be destroyed since the CustomerReviews " + customerReviewsListOrphanCheckCustomerReviews + " in its customerReviewsList field has a non-nullable trackId field.");
            }
            Collection<OrderTrack> orderTrackCollectionOrphanCheck = tracks.getOrderTrackCollection();
            for (OrderTrack orderTrackCollectionOrphanCheckOrderTrack : orderTrackCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tracks (" + tracks + ") cannot be destroyed since the OrderTrack " + orderTrackCollectionOrphanCheckOrderTrack + " in its orderTrackCollection field has a non-nullable trackId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Albums albumNumber = tracks.getAlbumNumber();
            if (albumNumber != null) {
                albumNumber.getTracksList().remove(tracks);
                albumNumber = em.merge(albumNumber);
            }
            List<GenreToTracks> genreToTracksList = tracks.getGenreToTracksList();
            for (GenreToTracks genreToTracksListGenreToTracks : genreToTracksList) {
                genreToTracksListGenreToTracks.setTrackId(null);
                genreToTracksListGenreToTracks = em.merge(genreToTracksListGenreToTracks);
            }
            em.remove(tracks);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tracks> findTracksEntities() {
        return findTracksEntities(true, -1, -1);
    }

    public List<Tracks> findTracksEntities(int maxResults, int firstResult) {
        return findTracksEntities(false, maxResults, firstResult);
    }

    private List<Tracks> findTracksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tracks.class));
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

    public Tracks findTracks(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tracks.class, id);
        } finally {
            em.close();
        }
    }

    public int getTracksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tracks> rt = cq.from(Tracks.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
