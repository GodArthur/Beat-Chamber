/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber;

import com.beatchamber.entities.Albums;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.beatchamber.entities.ArtistAlbums;
import java.util.ArrayList;
import java.util.List;
import com.beatchamber.entities.Tracks;
import com.beatchamber.entities.GenreToAlbum;
import com.beatchamber.entities.OrderAlbum;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kibra
 */
public class AlbumsJpaController implements Serializable {

    public AlbumsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Albums albums) {
        if (albums.getArtistAlbumsList() == null) {
            albums.setArtistAlbumsList(new ArrayList<ArtistAlbums>());
        }
        if (albums.getTracksList() == null) {
            albums.setTracksList(new ArrayList<Tracks>());
        }
        if (albums.getGenreToAlbumList() == null) {
            albums.setGenreToAlbumList(new ArrayList<GenreToAlbum>());
        }
        if (albums.getOrderAlbumCollection() == null) {
            albums.setOrderAlbumCollection(new ArrayList<OrderAlbum>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ArtistAlbums> attachedArtistAlbumsList = new ArrayList<ArtistAlbums>();
            for (ArtistAlbums artistAlbumsListArtistAlbumsToAttach : albums.getArtistAlbumsList()) {
                artistAlbumsListArtistAlbumsToAttach = em.getReference(artistAlbumsListArtistAlbumsToAttach.getClass(), artistAlbumsListArtistAlbumsToAttach.getTablekey());
                attachedArtistAlbumsList.add(artistAlbumsListArtistAlbumsToAttach);
            }
            albums.setArtistAlbumsList(attachedArtistAlbumsList);
            List<Tracks> attachedTracksList = new ArrayList<Tracks>();
            for (Tracks tracksListTracksToAttach : albums.getTracksList()) {
                tracksListTracksToAttach = em.getReference(tracksListTracksToAttach.getClass(), tracksListTracksToAttach.getTrackId());
                attachedTracksList.add(tracksListTracksToAttach);
            }
            albums.setTracksList(attachedTracksList);
            List<GenreToAlbum> attachedGenreToAlbumList = new ArrayList<GenreToAlbum>();
            for (GenreToAlbum genreToAlbumListGenreToAlbumToAttach : albums.getGenreToAlbumList()) {
                genreToAlbumListGenreToAlbumToAttach = em.getReference(genreToAlbumListGenreToAlbumToAttach.getClass(), genreToAlbumListGenreToAlbumToAttach.getTablekey());
                attachedGenreToAlbumList.add(genreToAlbumListGenreToAlbumToAttach);
            }
            albums.setGenreToAlbumList(attachedGenreToAlbumList);
            Collection<OrderAlbum> attachedOrderAlbumCollection = new ArrayList<OrderAlbum>();
            for (OrderAlbum orderAlbumCollectionOrderAlbumToAttach : albums.getOrderAlbumCollection()) {
                orderAlbumCollectionOrderAlbumToAttach = em.getReference(orderAlbumCollectionOrderAlbumToAttach.getClass(), orderAlbumCollectionOrderAlbumToAttach.getOrderId());
                attachedOrderAlbumCollection.add(orderAlbumCollectionOrderAlbumToAttach);
            }
            albums.setOrderAlbumCollection(attachedOrderAlbumCollection);
            em.persist(albums);
            for (ArtistAlbums artistAlbumsListArtistAlbums : albums.getArtistAlbumsList()) {
                Albums oldAlbumNumberOfArtistAlbumsListArtistAlbums = artistAlbumsListArtistAlbums.getAlbumNumber();
                artistAlbumsListArtistAlbums.setAlbumNumber(albums);
                artistAlbumsListArtistAlbums = em.merge(artistAlbumsListArtistAlbums);
                if (oldAlbumNumberOfArtistAlbumsListArtistAlbums != null) {
                    oldAlbumNumberOfArtistAlbumsListArtistAlbums.getArtistAlbumsList().remove(artistAlbumsListArtistAlbums);
                    oldAlbumNumberOfArtistAlbumsListArtistAlbums = em.merge(oldAlbumNumberOfArtistAlbumsListArtistAlbums);
                }
            }
            for (Tracks tracksListTracks : albums.getTracksList()) {
                Albums oldAlbumNumberOfTracksListTracks = tracksListTracks.getAlbumNumber();
                tracksListTracks.setAlbumNumber(albums);
                tracksListTracks = em.merge(tracksListTracks);
                if (oldAlbumNumberOfTracksListTracks != null) {
                    oldAlbumNumberOfTracksListTracks.getTracksList().remove(tracksListTracks);
                    oldAlbumNumberOfTracksListTracks = em.merge(oldAlbumNumberOfTracksListTracks);
                }
            }
            for (GenreToAlbum genreToAlbumListGenreToAlbum : albums.getGenreToAlbumList()) {
                Albums oldAlbumNumberOfGenreToAlbumListGenreToAlbum = genreToAlbumListGenreToAlbum.getAlbumNumber();
                genreToAlbumListGenreToAlbum.setAlbumNumber(albums);
                genreToAlbumListGenreToAlbum = em.merge(genreToAlbumListGenreToAlbum);
                if (oldAlbumNumberOfGenreToAlbumListGenreToAlbum != null) {
                    oldAlbumNumberOfGenreToAlbumListGenreToAlbum.getGenreToAlbumList().remove(genreToAlbumListGenreToAlbum);
                    oldAlbumNumberOfGenreToAlbumListGenreToAlbum = em.merge(oldAlbumNumberOfGenreToAlbumListGenreToAlbum);
                }
            }
            for (OrderAlbum orderAlbumCollectionOrderAlbum : albums.getOrderAlbumCollection()) {
                Albums oldAlbumIdOfOrderAlbumCollectionOrderAlbum = orderAlbumCollectionOrderAlbum.getAlbumId();
                orderAlbumCollectionOrderAlbum.setAlbumId(albums);
                orderAlbumCollectionOrderAlbum = em.merge(orderAlbumCollectionOrderAlbum);
                if (oldAlbumIdOfOrderAlbumCollectionOrderAlbum != null) {
                    oldAlbumIdOfOrderAlbumCollectionOrderAlbum.getOrderAlbumCollection().remove(orderAlbumCollectionOrderAlbum);
                    oldAlbumIdOfOrderAlbumCollectionOrderAlbum = em.merge(oldAlbumIdOfOrderAlbumCollectionOrderAlbum);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Albums albums) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Albums persistentAlbums = em.find(Albums.class, albums.getAlbumNumber());
            List<ArtistAlbums> artistAlbumsListOld = persistentAlbums.getArtistAlbumsList();
            List<ArtistAlbums> artistAlbumsListNew = albums.getArtistAlbumsList();
            List<Tracks> tracksListOld = persistentAlbums.getTracksList();
            List<Tracks> tracksListNew = albums.getTracksList();
            List<GenreToAlbum> genreToAlbumListOld = persistentAlbums.getGenreToAlbumList();
            List<GenreToAlbum> genreToAlbumListNew = albums.getGenreToAlbumList();
            Collection<OrderAlbum> orderAlbumCollectionOld = persistentAlbums.getOrderAlbumCollection();
            Collection<OrderAlbum> orderAlbumCollectionNew = albums.getOrderAlbumCollection();
            List<String> illegalOrphanMessages = null;
            for (ArtistAlbums artistAlbumsListOldArtistAlbums : artistAlbumsListOld) {
                if (!artistAlbumsListNew.contains(artistAlbumsListOldArtistAlbums)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ArtistAlbums " + artistAlbumsListOldArtistAlbums + " since its albumNumber field is not nullable.");
                }
            }
            for (Tracks tracksListOldTracks : tracksListOld) {
                if (!tracksListNew.contains(tracksListOldTracks)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tracks " + tracksListOldTracks + " since its albumNumber field is not nullable.");
                }
            }
            for (OrderAlbum orderAlbumCollectionOldOrderAlbum : orderAlbumCollectionOld) {
                if (!orderAlbumCollectionNew.contains(orderAlbumCollectionOldOrderAlbum)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderAlbum " + orderAlbumCollectionOldOrderAlbum + " since its albumId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ArtistAlbums> attachedArtistAlbumsListNew = new ArrayList<ArtistAlbums>();
            for (ArtistAlbums artistAlbumsListNewArtistAlbumsToAttach : artistAlbumsListNew) {
                artistAlbumsListNewArtistAlbumsToAttach = em.getReference(artistAlbumsListNewArtistAlbumsToAttach.getClass(), artistAlbumsListNewArtistAlbumsToAttach.getTablekey());
                attachedArtistAlbumsListNew.add(artistAlbumsListNewArtistAlbumsToAttach);
            }
            artistAlbumsListNew = attachedArtistAlbumsListNew;
            albums.setArtistAlbumsList(artistAlbumsListNew);
            List<Tracks> attachedTracksListNew = new ArrayList<Tracks>();
            for (Tracks tracksListNewTracksToAttach : tracksListNew) {
                tracksListNewTracksToAttach = em.getReference(tracksListNewTracksToAttach.getClass(), tracksListNewTracksToAttach.getTrackId());
                attachedTracksListNew.add(tracksListNewTracksToAttach);
            }
            tracksListNew = attachedTracksListNew;
            albums.setTracksList(tracksListNew);
            List<GenreToAlbum> attachedGenreToAlbumListNew = new ArrayList<GenreToAlbum>();
            for (GenreToAlbum genreToAlbumListNewGenreToAlbumToAttach : genreToAlbumListNew) {
                genreToAlbumListNewGenreToAlbumToAttach = em.getReference(genreToAlbumListNewGenreToAlbumToAttach.getClass(), genreToAlbumListNewGenreToAlbumToAttach.getTablekey());
                attachedGenreToAlbumListNew.add(genreToAlbumListNewGenreToAlbumToAttach);
            }
            genreToAlbumListNew = attachedGenreToAlbumListNew;
            albums.setGenreToAlbumList(genreToAlbumListNew);
            Collection<OrderAlbum> attachedOrderAlbumCollectionNew = new ArrayList<OrderAlbum>();
            for (OrderAlbum orderAlbumCollectionNewOrderAlbumToAttach : orderAlbumCollectionNew) {
                orderAlbumCollectionNewOrderAlbumToAttach = em.getReference(orderAlbumCollectionNewOrderAlbumToAttach.getClass(), orderAlbumCollectionNewOrderAlbumToAttach.getOrderId());
                attachedOrderAlbumCollectionNew.add(orderAlbumCollectionNewOrderAlbumToAttach);
            }
            orderAlbumCollectionNew = attachedOrderAlbumCollectionNew;
            albums.setOrderAlbumCollection(orderAlbumCollectionNew);
            albums = em.merge(albums);
            for (ArtistAlbums artistAlbumsListNewArtistAlbums : artistAlbumsListNew) {
                if (!artistAlbumsListOld.contains(artistAlbumsListNewArtistAlbums)) {
                    Albums oldAlbumNumberOfArtistAlbumsListNewArtistAlbums = artistAlbumsListNewArtistAlbums.getAlbumNumber();
                    artistAlbumsListNewArtistAlbums.setAlbumNumber(albums);
                    artistAlbumsListNewArtistAlbums = em.merge(artistAlbumsListNewArtistAlbums);
                    if (oldAlbumNumberOfArtistAlbumsListNewArtistAlbums != null && !oldAlbumNumberOfArtistAlbumsListNewArtistAlbums.equals(albums)) {
                        oldAlbumNumberOfArtistAlbumsListNewArtistAlbums.getArtistAlbumsList().remove(artistAlbumsListNewArtistAlbums);
                        oldAlbumNumberOfArtistAlbumsListNewArtistAlbums = em.merge(oldAlbumNumberOfArtistAlbumsListNewArtistAlbums);
                    }
                }
            }
            for (Tracks tracksListNewTracks : tracksListNew) {
                if (!tracksListOld.contains(tracksListNewTracks)) {
                    Albums oldAlbumNumberOfTracksListNewTracks = tracksListNewTracks.getAlbumNumber();
                    tracksListNewTracks.setAlbumNumber(albums);
                    tracksListNewTracks = em.merge(tracksListNewTracks);
                    if (oldAlbumNumberOfTracksListNewTracks != null && !oldAlbumNumberOfTracksListNewTracks.equals(albums)) {
                        oldAlbumNumberOfTracksListNewTracks.getTracksList().remove(tracksListNewTracks);
                        oldAlbumNumberOfTracksListNewTracks = em.merge(oldAlbumNumberOfTracksListNewTracks);
                    }
                }
            }
            for (GenreToAlbum genreToAlbumListOldGenreToAlbum : genreToAlbumListOld) {
                if (!genreToAlbumListNew.contains(genreToAlbumListOldGenreToAlbum)) {
                    genreToAlbumListOldGenreToAlbum.setAlbumNumber(null);
                    genreToAlbumListOldGenreToAlbum = em.merge(genreToAlbumListOldGenreToAlbum);
                }
            }
            for (GenreToAlbum genreToAlbumListNewGenreToAlbum : genreToAlbumListNew) {
                if (!genreToAlbumListOld.contains(genreToAlbumListNewGenreToAlbum)) {
                    Albums oldAlbumNumberOfGenreToAlbumListNewGenreToAlbum = genreToAlbumListNewGenreToAlbum.getAlbumNumber();
                    genreToAlbumListNewGenreToAlbum.setAlbumNumber(albums);
                    genreToAlbumListNewGenreToAlbum = em.merge(genreToAlbumListNewGenreToAlbum);
                    if (oldAlbumNumberOfGenreToAlbumListNewGenreToAlbum != null && !oldAlbumNumberOfGenreToAlbumListNewGenreToAlbum.equals(albums)) {
                        oldAlbumNumberOfGenreToAlbumListNewGenreToAlbum.getGenreToAlbumList().remove(genreToAlbumListNewGenreToAlbum);
                        oldAlbumNumberOfGenreToAlbumListNewGenreToAlbum = em.merge(oldAlbumNumberOfGenreToAlbumListNewGenreToAlbum);
                    }
                }
            }
            for (OrderAlbum orderAlbumCollectionNewOrderAlbum : orderAlbumCollectionNew) {
                if (!orderAlbumCollectionOld.contains(orderAlbumCollectionNewOrderAlbum)) {
                    Albums oldAlbumIdOfOrderAlbumCollectionNewOrderAlbum = orderAlbumCollectionNewOrderAlbum.getAlbumId();
                    orderAlbumCollectionNewOrderAlbum.setAlbumId(albums);
                    orderAlbumCollectionNewOrderAlbum = em.merge(orderAlbumCollectionNewOrderAlbum);
                    if (oldAlbumIdOfOrderAlbumCollectionNewOrderAlbum != null && !oldAlbumIdOfOrderAlbumCollectionNewOrderAlbum.equals(albums)) {
                        oldAlbumIdOfOrderAlbumCollectionNewOrderAlbum.getOrderAlbumCollection().remove(orderAlbumCollectionNewOrderAlbum);
                        oldAlbumIdOfOrderAlbumCollectionNewOrderAlbum = em.merge(oldAlbumIdOfOrderAlbumCollectionNewOrderAlbum);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = albums.getAlbumNumber();
                if (findAlbums(id) == null) {
                    throw new NonexistentEntityException("The albums with id " + id + " no longer exists.");
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
            Albums albums;
            try {
                albums = em.getReference(Albums.class, id);
                albums.getAlbumNumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The albums with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ArtistAlbums> artistAlbumsListOrphanCheck = albums.getArtistAlbumsList();
            for (ArtistAlbums artistAlbumsListOrphanCheckArtistAlbums : artistAlbumsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Albums (" + albums + ") cannot be destroyed since the ArtistAlbums " + artistAlbumsListOrphanCheckArtistAlbums + " in its artistAlbumsList field has a non-nullable albumNumber field.");
            }
            List<Tracks> tracksListOrphanCheck = albums.getTracksList();
            for (Tracks tracksListOrphanCheckTracks : tracksListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Albums (" + albums + ") cannot be destroyed since the Tracks " + tracksListOrphanCheckTracks + " in its tracksList field has a non-nullable albumNumber field.");
            }
            Collection<OrderAlbum> orderAlbumCollectionOrphanCheck = albums.getOrderAlbumCollection();
            for (OrderAlbum orderAlbumCollectionOrphanCheckOrderAlbum : orderAlbumCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Albums (" + albums + ") cannot be destroyed since the OrderAlbum " + orderAlbumCollectionOrphanCheckOrderAlbum + " in its orderAlbumCollection field has a non-nullable albumId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GenreToAlbum> genreToAlbumList = albums.getGenreToAlbumList();
            for (GenreToAlbum genreToAlbumListGenreToAlbum : genreToAlbumList) {
                genreToAlbumListGenreToAlbum.setAlbumNumber(null);
                genreToAlbumListGenreToAlbum = em.merge(genreToAlbumListGenreToAlbum);
            }
            em.remove(albums);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Albums> findAlbumsEntities() {
        return findAlbumsEntities(true, -1, -1);
    }

    public List<Albums> findAlbumsEntities(int maxResults, int firstResult) {
        return findAlbumsEntities(false, maxResults, firstResult);
    }

    private List<Albums> findAlbumsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Albums.class));
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

    public Albums findAlbums(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Albums.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlbumsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Albums> rt = cq.from(Albums.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
