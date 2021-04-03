package com.beatchamber.jpacontroller;

import com.beatchamber.entities.InvoiceDetails;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.beatchamber.entities.Invoices;
import com.beatchamber.entities.Tracks;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
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
public class InvoiceDetailsJpaController implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(InvoiceDetailsJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "music_store_persistence")
    private EntityManager em;

    public InvoiceDetailsJpaController() {
    }

    public void create(InvoiceDetails invoiceDetails) throws RollbackFailureException {

        try {
            utx.begin();
            Invoices saleNumber = invoiceDetails.getSaleNumber();
            if (saleNumber != null) {
                saleNumber = em.getReference(saleNumber.getClass(), saleNumber.getSaleNumber());
                invoiceDetails.setSaleNumber(saleNumber);
            }
            Tracks trackId = invoiceDetails.getTrackId();
            if (trackId != null) {
                trackId = em.getReference(trackId.getClass(), trackId.getTrackId());
                invoiceDetails.setTrackId(trackId);
            }
            em.persist(invoiceDetails);
            if (saleNumber != null) {
                saleNumber.getInvoiceDetailsList().add(invoiceDetails);
                saleNumber = em.merge(saleNumber);
            }
            if (trackId != null) {
                trackId.getInvoiceDetailsList().add(invoiceDetails);
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

    public void edit(InvoiceDetails invoiceDetails) throws NonexistentEntityException, Exception {

        try {

            utx.begin();
            InvoiceDetails persistentInvoiceDetails = em.find(InvoiceDetails.class, invoiceDetails.getTablekey());
            Invoices saleNumberOld = persistentInvoiceDetails.getSaleNumber();
            Invoices saleNumberNew = invoiceDetails.getSaleNumber();
            Tracks trackIdOld = persistentInvoiceDetails.getTrackId();
            Tracks trackIdNew = invoiceDetails.getTrackId();
            if (saleNumberNew != null) {
                saleNumberNew = em.getReference(saleNumberNew.getClass(), saleNumberNew.getSaleNumber());
                invoiceDetails.setSaleNumber(saleNumberNew);
            }
            if (trackIdNew != null) {
                trackIdNew = em.getReference(trackIdNew.getClass(), trackIdNew.getTrackId());
                invoiceDetails.setTrackId(trackIdNew);
            }
            invoiceDetails = em.merge(invoiceDetails);
            if (saleNumberOld != null && !saleNumberOld.equals(saleNumberNew)) {
                saleNumberOld.getInvoiceDetailsList().remove(invoiceDetails);
                saleNumberOld = em.merge(saleNumberOld);
            }
            if (saleNumberNew != null && !saleNumberNew.equals(saleNumberOld)) {
                saleNumberNew.getInvoiceDetailsList().add(invoiceDetails);
                saleNumberNew = em.merge(saleNumberNew);
            }
            if (trackIdOld != null && !trackIdOld.equals(trackIdNew)) {
                trackIdOld.getInvoiceDetailsList().remove(invoiceDetails);
                trackIdOld = em.merge(trackIdOld);
            }
            if (trackIdNew != null && !trackIdNew.equals(trackIdOld)) {
                trackIdNew.getInvoiceDetailsList().add(invoiceDetails);
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
                Integer id = invoiceDetails.getTablekey();
                if (findInvoiceDetails(id) == null) {
                    throw new NonexistentEntityException("The invoiceDetails with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, NotSupportedException, SystemException, RollbackFailureException, RollbackException, HeuristicMixedException, HeuristicRollbackException {

        try {
            utx.begin();
            InvoiceDetails invoiceDetails;
            try {
                invoiceDetails = em.getReference(InvoiceDetails.class, id);
                invoiceDetails.getTablekey();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invoiceDetails with id " + id + " no longer exists.", enfe);
            }
            Invoices saleNumber = invoiceDetails.getSaleNumber();
            if (saleNumber != null) {
                saleNumber.getInvoiceDetailsList().remove(invoiceDetails);
                saleNumber = em.merge(saleNumber);
            }
            Tracks trackId = invoiceDetails.getTrackId();
            if (trackId != null) {
                trackId.getInvoiceDetailsList().remove(invoiceDetails);
                trackId = em.merge(trackId);
            }
            em.remove(invoiceDetails);
            utx.commit();
        } catch (NotSupportedException | SystemException | NonexistentEntityException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public List<InvoiceDetails> findInvoiceDetailsEntities() {
        return findInvoiceDetailsEntities(true, -1, -1);
    }

    public List<InvoiceDetails> findInvoiceDetailsEntities(int maxResults, int firstResult) {
        return findInvoiceDetailsEntities(false, maxResults, firstResult);
    }

    private List<InvoiceDetails> findInvoiceDetailsEntities(boolean all, int maxResults, int firstResult) {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(InvoiceDetails.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public InvoiceDetails findInvoiceDetails(Integer id) {

        return em.find(InvoiceDetails.class, id);
    }

    public int getInvoiceDetailsCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<InvoiceDetails> rt = cq.from(InvoiceDetails.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
