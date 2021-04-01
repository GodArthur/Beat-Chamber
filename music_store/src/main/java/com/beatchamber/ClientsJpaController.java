/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber;

import com.beatchamber.entities.Clients;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.beatchamber.entities.CustomerReviews;
import java.util.ArrayList;
import java.util.List;
import com.beatchamber.entities.Invoices;
import com.beatchamber.entities.Orders;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kibra
 */
public class ClientsJpaController implements Serializable {

    public ClientsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clients clients) {
        if (clients.getCustomerReviewsList() == null) {
            clients.setCustomerReviewsList(new ArrayList<CustomerReviews>());
        }
        if (clients.getInvoicesList() == null) {
            clients.setInvoicesList(new ArrayList<Invoices>());
        }
        if (clients.getOrdersCollection() == null) {
            clients.setOrdersCollection(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CustomerReviews> attachedCustomerReviewsList = new ArrayList<CustomerReviews>();
            for (CustomerReviews customerReviewsListCustomerReviewsToAttach : clients.getCustomerReviewsList()) {
                customerReviewsListCustomerReviewsToAttach = em.getReference(customerReviewsListCustomerReviewsToAttach.getClass(), customerReviewsListCustomerReviewsToAttach.getReviewNumber());
                attachedCustomerReviewsList.add(customerReviewsListCustomerReviewsToAttach);
            }
            clients.setCustomerReviewsList(attachedCustomerReviewsList);
            List<Invoices> attachedInvoicesList = new ArrayList<Invoices>();
            for (Invoices invoicesListInvoicesToAttach : clients.getInvoicesList()) {
                invoicesListInvoicesToAttach = em.getReference(invoicesListInvoicesToAttach.getClass(), invoicesListInvoicesToAttach.getSaleNumber());
                attachedInvoicesList.add(invoicesListInvoicesToAttach);
            }
            clients.setInvoicesList(attachedInvoicesList);
            Collection<Orders> attachedOrdersCollection = new ArrayList<Orders>();
            for (Orders ordersCollectionOrdersToAttach : clients.getOrdersCollection()) {
                ordersCollectionOrdersToAttach = em.getReference(ordersCollectionOrdersToAttach.getClass(), ordersCollectionOrdersToAttach.getTablekey());
                attachedOrdersCollection.add(ordersCollectionOrdersToAttach);
            }
            clients.setOrdersCollection(attachedOrdersCollection);
            em.persist(clients);
            for (CustomerReviews customerReviewsListCustomerReviews : clients.getCustomerReviewsList()) {
                Clients oldClientNumberOfCustomerReviewsListCustomerReviews = customerReviewsListCustomerReviews.getClientNumber();
                customerReviewsListCustomerReviews.setClientNumber(clients);
                customerReviewsListCustomerReviews = em.merge(customerReviewsListCustomerReviews);
                if (oldClientNumberOfCustomerReviewsListCustomerReviews != null) {
                    oldClientNumberOfCustomerReviewsListCustomerReviews.getCustomerReviewsList().remove(customerReviewsListCustomerReviews);
                    oldClientNumberOfCustomerReviewsListCustomerReviews = em.merge(oldClientNumberOfCustomerReviewsListCustomerReviews);
                }
            }
            for (Invoices invoicesListInvoices : clients.getInvoicesList()) {
                Clients oldClientNumberOfInvoicesListInvoices = invoicesListInvoices.getClientNumber();
                invoicesListInvoices.setClientNumber(clients);
                invoicesListInvoices = em.merge(invoicesListInvoices);
                if (oldClientNumberOfInvoicesListInvoices != null) {
                    oldClientNumberOfInvoicesListInvoices.getInvoicesList().remove(invoicesListInvoices);
                    oldClientNumberOfInvoicesListInvoices = em.merge(oldClientNumberOfInvoicesListInvoices);
                }
            }
            for (Orders ordersCollectionOrders : clients.getOrdersCollection()) {
                Clients oldClientNumberOfOrdersCollectionOrders = ordersCollectionOrders.getClientNumber();
                ordersCollectionOrders.setClientNumber(clients);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
                if (oldClientNumberOfOrdersCollectionOrders != null) {
                    oldClientNumberOfOrdersCollectionOrders.getOrdersCollection().remove(ordersCollectionOrders);
                    oldClientNumberOfOrdersCollectionOrders = em.merge(oldClientNumberOfOrdersCollectionOrders);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clients clients) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients persistentClients = em.find(Clients.class, clients.getClientNumber());
            List<CustomerReviews> customerReviewsListOld = persistentClients.getCustomerReviewsList();
            List<CustomerReviews> customerReviewsListNew = clients.getCustomerReviewsList();
            List<Invoices> invoicesListOld = persistentClients.getInvoicesList();
            List<Invoices> invoicesListNew = clients.getInvoicesList();
            Collection<Orders> ordersCollectionOld = persistentClients.getOrdersCollection();
            Collection<Orders> ordersCollectionNew = clients.getOrdersCollection();
            List<String> illegalOrphanMessages = null;
            for (CustomerReviews customerReviewsListOldCustomerReviews : customerReviewsListOld) {
                if (!customerReviewsListNew.contains(customerReviewsListOldCustomerReviews)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CustomerReviews " + customerReviewsListOldCustomerReviews + " since its clientNumber field is not nullable.");
                }
            }
            for (Invoices invoicesListOldInvoices : invoicesListOld) {
                if (!invoicesListNew.contains(invoicesListOldInvoices)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Invoices " + invoicesListOldInvoices + " since its clientNumber field is not nullable.");
                }
            }
            for (Orders ordersCollectionOldOrders : ordersCollectionOld) {
                if (!ordersCollectionNew.contains(ordersCollectionOldOrders)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orders " + ordersCollectionOldOrders + " since its clientNumber field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CustomerReviews> attachedCustomerReviewsListNew = new ArrayList<CustomerReviews>();
            for (CustomerReviews customerReviewsListNewCustomerReviewsToAttach : customerReviewsListNew) {
                customerReviewsListNewCustomerReviewsToAttach = em.getReference(customerReviewsListNewCustomerReviewsToAttach.getClass(), customerReviewsListNewCustomerReviewsToAttach.getReviewNumber());
                attachedCustomerReviewsListNew.add(customerReviewsListNewCustomerReviewsToAttach);
            }
            customerReviewsListNew = attachedCustomerReviewsListNew;
            clients.setCustomerReviewsList(customerReviewsListNew);
            List<Invoices> attachedInvoicesListNew = new ArrayList<Invoices>();
            for (Invoices invoicesListNewInvoicesToAttach : invoicesListNew) {
                invoicesListNewInvoicesToAttach = em.getReference(invoicesListNewInvoicesToAttach.getClass(), invoicesListNewInvoicesToAttach.getSaleNumber());
                attachedInvoicesListNew.add(invoicesListNewInvoicesToAttach);
            }
            invoicesListNew = attachedInvoicesListNew;
            clients.setInvoicesList(invoicesListNew);
            Collection<Orders> attachedOrdersCollectionNew = new ArrayList<Orders>();
            for (Orders ordersCollectionNewOrdersToAttach : ordersCollectionNew) {
                ordersCollectionNewOrdersToAttach = em.getReference(ordersCollectionNewOrdersToAttach.getClass(), ordersCollectionNewOrdersToAttach.getTablekey());
                attachedOrdersCollectionNew.add(ordersCollectionNewOrdersToAttach);
            }
            ordersCollectionNew = attachedOrdersCollectionNew;
            clients.setOrdersCollection(ordersCollectionNew);
            clients = em.merge(clients);
            for (CustomerReviews customerReviewsListNewCustomerReviews : customerReviewsListNew) {
                if (!customerReviewsListOld.contains(customerReviewsListNewCustomerReviews)) {
                    Clients oldClientNumberOfCustomerReviewsListNewCustomerReviews = customerReviewsListNewCustomerReviews.getClientNumber();
                    customerReviewsListNewCustomerReviews.setClientNumber(clients);
                    customerReviewsListNewCustomerReviews = em.merge(customerReviewsListNewCustomerReviews);
                    if (oldClientNumberOfCustomerReviewsListNewCustomerReviews != null && !oldClientNumberOfCustomerReviewsListNewCustomerReviews.equals(clients)) {
                        oldClientNumberOfCustomerReviewsListNewCustomerReviews.getCustomerReviewsList().remove(customerReviewsListNewCustomerReviews);
                        oldClientNumberOfCustomerReviewsListNewCustomerReviews = em.merge(oldClientNumberOfCustomerReviewsListNewCustomerReviews);
                    }
                }
            }
            for (Invoices invoicesListNewInvoices : invoicesListNew) {
                if (!invoicesListOld.contains(invoicesListNewInvoices)) {
                    Clients oldClientNumberOfInvoicesListNewInvoices = invoicesListNewInvoices.getClientNumber();
                    invoicesListNewInvoices.setClientNumber(clients);
                    invoicesListNewInvoices = em.merge(invoicesListNewInvoices);
                    if (oldClientNumberOfInvoicesListNewInvoices != null && !oldClientNumberOfInvoicesListNewInvoices.equals(clients)) {
                        oldClientNumberOfInvoicesListNewInvoices.getInvoicesList().remove(invoicesListNewInvoices);
                        oldClientNumberOfInvoicesListNewInvoices = em.merge(oldClientNumberOfInvoicesListNewInvoices);
                    }
                }
            }
            for (Orders ordersCollectionNewOrders : ordersCollectionNew) {
                if (!ordersCollectionOld.contains(ordersCollectionNewOrders)) {
                    Clients oldClientNumberOfOrdersCollectionNewOrders = ordersCollectionNewOrders.getClientNumber();
                    ordersCollectionNewOrders.setClientNumber(clients);
                    ordersCollectionNewOrders = em.merge(ordersCollectionNewOrders);
                    if (oldClientNumberOfOrdersCollectionNewOrders != null && !oldClientNumberOfOrdersCollectionNewOrders.equals(clients)) {
                        oldClientNumberOfOrdersCollectionNewOrders.getOrdersCollection().remove(ordersCollectionNewOrders);
                        oldClientNumberOfOrdersCollectionNewOrders = em.merge(oldClientNumberOfOrdersCollectionNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clients.getClientNumber();
                if (findClients(id) == null) {
                    throw new NonexistentEntityException("The clients with id " + id + " no longer exists.");
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
            Clients clients;
            try {
                clients = em.getReference(Clients.class, id);
                clients.getClientNumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clients with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CustomerReviews> customerReviewsListOrphanCheck = clients.getCustomerReviewsList();
            for (CustomerReviews customerReviewsListOrphanCheckCustomerReviews : customerReviewsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clients (" + clients + ") cannot be destroyed since the CustomerReviews " + customerReviewsListOrphanCheckCustomerReviews + " in its customerReviewsList field has a non-nullable clientNumber field.");
            }
            List<Invoices> invoicesListOrphanCheck = clients.getInvoicesList();
            for (Invoices invoicesListOrphanCheckInvoices : invoicesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clients (" + clients + ") cannot be destroyed since the Invoices " + invoicesListOrphanCheckInvoices + " in its invoicesList field has a non-nullable clientNumber field.");
            }
            Collection<Orders> ordersCollectionOrphanCheck = clients.getOrdersCollection();
            for (Orders ordersCollectionOrphanCheckOrders : ordersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clients (" + clients + ") cannot be destroyed since the Orders " + ordersCollectionOrphanCheckOrders + " in its ordersCollection field has a non-nullable clientNumber field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clients);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clients> findClientsEntities() {
        return findClientsEntities(true, -1, -1);
    }

    public List<Clients> findClientsEntities(int maxResults, int firstResult) {
        return findClientsEntities(false, maxResults, firstResult);
    }

    private List<Clients> findClientsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clients.class));
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

    public Clients findClients(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clients.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clients> rt = cq.from(Clients.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
