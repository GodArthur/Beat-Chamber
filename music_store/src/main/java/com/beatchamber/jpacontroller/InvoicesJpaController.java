package com.beatchamber.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.beatchamber.entities.Clients;
import com.beatchamber.entities.InvoiceDetails;
import com.beatchamber.entities.Invoices;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
import com.beatchamber.exceptions.RollbackFailureException;
import java.util.ArrayList;
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
public class InvoicesJpaController implements Serializable {

   private final static Logger LOG = LoggerFactory.getLogger(InvoicesJpaController.class);

    @Resource
    private UserTransaction utx;

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public InvoicesJpaController() {
    }

    public void create(Invoices invoices) throws RollbackFailureException {
        if (invoices.getInvoiceDetailsList() == null) {
            invoices.setInvoiceDetailsList(new ArrayList<InvoiceDetails>());
        }

        try {

            utx.begin();
            Clients clientNumber = invoices.getClientNumber();
            if (clientNumber != null) {
                clientNumber = em.getReference(clientNumber.getClass(), clientNumber.getClientNumber());
                invoices.setClientNumber(clientNumber);
            }
            List<InvoiceDetails> attachedInvoiceDetailsList = new ArrayList<InvoiceDetails>();
            for (InvoiceDetails invoiceDetailsListInvoiceDetailsToAttach : invoices.getInvoiceDetailsList()) {
                invoiceDetailsListInvoiceDetailsToAttach = em.getReference(invoiceDetailsListInvoiceDetailsToAttach.getClass(), invoiceDetailsListInvoiceDetailsToAttach.getTablekey());
                attachedInvoiceDetailsList.add(invoiceDetailsListInvoiceDetailsToAttach);
            }
            invoices.setInvoiceDetailsList(attachedInvoiceDetailsList);
            em.persist(invoices);
            if (clientNumber != null) {
                clientNumber.getInvoicesList().add(invoices);
                clientNumber = em.merge(clientNumber);
            }
            for (InvoiceDetails invoiceDetailsListInvoiceDetails : invoices.getInvoiceDetailsList()) {
                Invoices oldSaleNumberOfInvoiceDetailsListInvoiceDetails = invoiceDetailsListInvoiceDetails.getSaleNumber();
                invoiceDetailsListInvoiceDetails.setSaleNumber(invoices);
                invoiceDetailsListInvoiceDetails = em.merge(invoiceDetailsListInvoiceDetails);
                if (oldSaleNumberOfInvoiceDetailsListInvoiceDetails != null) {
                    oldSaleNumberOfInvoiceDetailsListInvoiceDetails.getInvoiceDetailsList().remove(invoiceDetailsListInvoiceDetails);
                    oldSaleNumberOfInvoiceDetailsListInvoiceDetails = em.merge(oldSaleNumberOfInvoiceDetailsListInvoiceDetails);
                }
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

    public void edit(Invoices invoices) throws IllegalOrphanException, NonexistentEntityException, Exception {

        try {
            utx.begin();
            Invoices persistentInvoices = em.find(Invoices.class, invoices.getSaleNumber());
            Clients clientNumberOld = persistentInvoices.getClientNumber();
            Clients clientNumberNew = invoices.getClientNumber();
            List<InvoiceDetails> invoiceDetailsListOld = persistentInvoices.getInvoiceDetailsList();
            List<InvoiceDetails> invoiceDetailsListNew = invoices.getInvoiceDetailsList();
            List<String> illegalOrphanMessages = null;
            for (InvoiceDetails invoiceDetailsListOldInvoiceDetails : invoiceDetailsListOld) {
                if (!invoiceDetailsListNew.contains(invoiceDetailsListOldInvoiceDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InvoiceDetails " + invoiceDetailsListOldInvoiceDetails + " since its saleNumber field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientNumberNew != null) {
                clientNumberNew = em.getReference(clientNumberNew.getClass(), clientNumberNew.getClientNumber());
                invoices.setClientNumber(clientNumberNew);
            }
            List<InvoiceDetails> attachedInvoiceDetailsListNew = new ArrayList<InvoiceDetails>();
            for (InvoiceDetails invoiceDetailsListNewInvoiceDetailsToAttach : invoiceDetailsListNew) {
                invoiceDetailsListNewInvoiceDetailsToAttach = em.getReference(invoiceDetailsListNewInvoiceDetailsToAttach.getClass(), invoiceDetailsListNewInvoiceDetailsToAttach.getTablekey());
                attachedInvoiceDetailsListNew.add(invoiceDetailsListNewInvoiceDetailsToAttach);
            }
            invoiceDetailsListNew = attachedInvoiceDetailsListNew;
            invoices.setInvoiceDetailsList(invoiceDetailsListNew);
            invoices = em.merge(invoices);
            if (clientNumberOld != null && !clientNumberOld.equals(clientNumberNew)) {
                clientNumberOld.getInvoicesList().remove(invoices);
                clientNumberOld = em.merge(clientNumberOld);
            }
            if (clientNumberNew != null && !clientNumberNew.equals(clientNumberOld)) {
                clientNumberNew.getInvoicesList().add(invoices);
                clientNumberNew = em.merge(clientNumberNew);
            }
            for (InvoiceDetails invoiceDetailsListNewInvoiceDetails : invoiceDetailsListNew) {
                if (!invoiceDetailsListOld.contains(invoiceDetailsListNewInvoiceDetails)) {
                    Invoices oldSaleNumberOfInvoiceDetailsListNewInvoiceDetails = invoiceDetailsListNewInvoiceDetails.getSaleNumber();
                    invoiceDetailsListNewInvoiceDetails.setSaleNumber(invoices);
                    invoiceDetailsListNewInvoiceDetails = em.merge(invoiceDetailsListNewInvoiceDetails);
                    if (oldSaleNumberOfInvoiceDetailsListNewInvoiceDetails != null && !oldSaleNumberOfInvoiceDetailsListNewInvoiceDetails.equals(invoices)) {
                        oldSaleNumberOfInvoiceDetailsListNewInvoiceDetails.getInvoiceDetailsList().remove(invoiceDetailsListNewInvoiceDetails);
                        oldSaleNumberOfInvoiceDetailsListNewInvoiceDetails = em.merge(oldSaleNumberOfInvoiceDetailsListNewInvoiceDetails);
                    }
                }
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
                Integer id = invoices.getSaleNumber();
                if (findInvoices(id) == null) {
                    throw new NonexistentEntityException("The album with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {

        try {

            utx.begin();
            Invoices invoices;
            try {
                invoices = em.getReference(Invoices.class, id);
                invoices.getSaleNumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invoices with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<InvoiceDetails> invoiceDetailsListOrphanCheck = invoices.getInvoiceDetailsList();
            for (InvoiceDetails invoiceDetailsListOrphanCheckInvoiceDetails : invoiceDetailsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Invoices (" + invoices + ") cannot be destroyed since the InvoiceDetails " + invoiceDetailsListOrphanCheckInvoiceDetails + " in its invoiceDetailsList field has a non-nullable saleNumber field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clients clientNumber = invoices.getClientNumber();
            if (clientNumber != null) {
                clientNumber.getInvoicesList().remove(invoices);
                clientNumber = em.merge(clientNumber);
            }
            em.remove(invoices);
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

    public List<Invoices> findInvoicesEntities() {
        return findInvoicesEntities(true, -1, -1);
    }

    public List<Invoices> findInvoicesEntities(int maxResults, int firstResult) {
        return findInvoicesEntities(false, maxResults, firstResult);
    }

    private List<Invoices> findInvoicesEntities(boolean all, int maxResults, int firstResult) {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Invoices.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    public Invoices findInvoices(Integer id) {

        return em.find(Invoices.class, id);
    }

    public int getInvoicesCount() {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Invoices> rt = cq.from(Invoices.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }

}
