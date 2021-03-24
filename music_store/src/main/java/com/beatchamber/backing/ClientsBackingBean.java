/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.backing;

import com.beatchamber.entities.Clients;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.jpacontroller.ClientsJpaController;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 1733570 Yan Tang
 */
@Named("theClients")
@SessionScoped
public class ClientsBackingBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ClientsBackingBean.class);

    @Inject
    private ClientsJpaController clientsJpaController;

    private List<Clients> clients;

    private Clients selectedClient;

    @PostConstruct
    public void init() {
        this.clients = clientsJpaController.findClientsEntities();
    }

    public List<Clients> getClients() {
        return clients;
    }

    public Clients getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Clients selectedClient) {
        this.selectedClient = selectedClient;
    }

    public void openNew() {
        this.selectedClient = new Clients();
    }

    public void saveClient() {
        try {
            if (this.selectedClient.getClientNumber() == null) {
                this.selectedClient.setPassword("123456");
                this.convertPhoneFormat();
                clientsJpaController.create(this.selectedClient);
                this.clients.add(this.selectedClient);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client Added"));
            } else {
                clientsJpaController.edit(this.selectedClient);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client Updated"));
            }
        } catch (RollbackFailureException | IllegalOrphanException | NonexistentEntityException ex) {
            java.util.logging.Logger.getLogger(ClientsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ClientsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrimeFaces.current().executeScript("PF('manageClientDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-clients");
    }

    public void deleteClient() {
        try {
            clientsJpaController.destroy(this.selectedClient.getClientNumber());
        } catch (IllegalOrphanException | NonexistentEntityException | NotSupportedException | SystemException | RollbackFailureException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
            java.util.logging.Logger.getLogger(ClientsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.clients.remove(this.selectedClient);
        this.selectedClient = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-clients");
    }

    private void convertPhoneFormat() {
        String stringCellPhone = "(" + this.selectedClient.getCellPhone().substring(0, 3) + ") "
                + this.selectedClient.getCellPhone().substring(3, 6) + "-"
                + this.selectedClient.getCellPhone().substring(6, 10);
        this.selectedClient.setCellPhone(stringCellPhone);
        String stringHomePhone = "(" + this.selectedClient.getHomePhone().substring(0, 3) + ") "
                + this.selectedClient.getHomePhone().substring(3, 6) + "-"
                + this.selectedClient.getHomePhone().substring(6, 10);
        this.selectedClient.setHomePhone(stringHomePhone);
    }
}
