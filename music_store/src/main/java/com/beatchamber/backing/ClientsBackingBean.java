/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.backing;

import com.beatchamber.entities.Clients;
import com.beatchamber.jpacontroller.ClientsJpaController;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 1733570 Yan
 */
@Named("theClients")
@RequestScoped
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
        if (this.selectedClient.getTitle() == null) {
//            this.selectedClient.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.clients.add(this.selectedClient);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client Added"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageClientDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-clients");
    }

    public void deleteClient() {
        this.clients.remove(this.selectedClient);
        this.selectedClient = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-clients");
    }
}
