package com.beatchamber.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.beatchamber.entities.Artists;
import com.beatchamber.entities.ArtistsToTracks;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.exceptions.NonexistentEntityException;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
public class ArtistsToTracksJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ArtistsToTracksJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "music_store_persistence")
    private EntityManager em;

    public ArtistsToTracksJpaController() {
    }

    public void create(ArtistsToTracks artistsToTracks) throws RollbackFailureException {

        try {

            utx.begin();
            Artists artistId = artistsToTracks.getArtistId();
            if (artistId != null) {
                artistId = em.getReference(artistId.getClass(), artistId.getArtistId());
                artistsToTracks.setArtistId(artistId);
            }
            em.persist(artistsToTracks);
            if (artistId != null) {
                artistId.getArtistsToTracksList().add(artistsToTracks);
                artistId = em.merge(artistId);
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

    public void edit(ArtistsToTracks artistsToTracks) throws NonexistentEntityException, Exception {

        try {  
            utx.begin();
            ArtistsToTracks persistentArtistsToTracks = em.find(ArtistsToTracks.class, artistsToTracks.getTablekey());
            Artists artistIdOld = persistentArtistsToTracks.getArtistId();
            Artists artistIdNew = artistsToTracks.getArtistId();
            if (artistIdNew != null) {
                artistIdNew = em.getReference(artistIdNew.getClass(), artistIdNew.getArtistId());
                artistsToTracks.setArtistId(artistIdNew);
            }
            artistsToTracks = em.merge(artistsToTracks);
            if (artistIdOld != null && !artistIdOld.equals(artistIdNew)) {
                artistIdOld.getArtistsToTracksList().remove(artistsToTracks);
                artistIdOld = em.merge(artistIdOld);
            }
            if (artistIdNew != null && !artistIdNew.equals(artistIdOld)) {
                artistIdNew.getArtistsToTracksList().add(artistsToTracks);
                artistIdNew = em.merge(artistIdNew);
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
                Integer id = artistsToTracks.getTablekey();
                if (findArtistsToTracks(id) == null) {
                    throw new com.beatchamber.exceptions.NonexistentEntityException("The album with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, com.beatchamber.exceptions.NonexistentEntityException, NotSupportedException, SystemException, RollbackFailureException, RollbackException, HeuristicMixedException, HeuristicRollbackException {

        try {
            utx.begin();
            ArtistsToTracks artistsToTracks;
            try {
                artistsToTracks = em.getReference(ArtistsToTracks.class, id);
                artistsToTracks.getTablekey();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The artistsToTracks with id " + id + " no longer exists.", enfe);
            }
            Artists artistId = artistsToTracks.getArtistId();
            if (artistId != null) {
                artistId.getArtistsToTracksList().remove(artistsToTracks);
                artistId = em.merge(artistId);
            }
            em.remove(artistsToTracks);
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

    public List<ArtistsToTracks> findArtistsToTracksEntities() {
        return findArtistsToTracksEntities(true, -1, -1);
    }

    public List<ArtistsToTracks> findArtistsToTracksEntities(int maxResults, int firstResult) {
        return findArtistsToTracksEntities(false, maxResults, firstResult);
    }

    private List<ArtistsToTracks> findArtistsToTracksEntities(boolean all, int maxResults, int firstResult) {

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ArtistsToTracks.class));
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

    public ArtistsToTracks findArtistsToTracks(Integer id) {

        try {
            return em.find(ArtistsToTracks.class, id);
        } finally {
            em.close();
        }
    }

    public int getArtistsToTracksCount() {

        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ArtistsToTracks> rt = cq.from(ArtistsToTracks.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
