/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans.manager;

import com.beatchamber.beans.PhoneNumber;
import com.beatchamber.entities.Clients;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.jpacontroller.ClientsJpaController;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
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

    private PhoneNumber homePhoneNumber;

    private PhoneNumber cellPhoneNumber;
    
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

    public PhoneNumber getHomePhoneNumber() {
        return this.homePhoneNumber;
    }

    public void setHomePhoneNumber(PhoneNumber phoneNumber) {
        this.homePhoneNumber = phoneNumber;
    }

    public PhoneNumber getCellPhoneNumber() {
        return this.cellPhoneNumber;
    }

    public void setCellPhoneNumber(PhoneNumber phoneNumber) {
        this.cellPhoneNumber = phoneNumber;
    }

    public List<Boolean> isManager() {
        List<Boolean> isManagerList = new ArrayList<>();
        this.clients.forEach(item -> isManagerList.add(item.getTitle().equals("Manager")));
        return isManagerList;
    }
    
    public void openNew() {
        this.selectedClient = new Clients();
    }

    public void saveClient() {
        try {
            if (this.selectedClient.getClientNumber() == null) {
                this.setPassword();
                this.setPhoneNumber();
                clientsJpaController.create(this.selectedClient);
                this.clients.add(this.selectedClient);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Client Added"));
            } else {
                this.setPhoneNumber();
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

    private void setPhoneNumber() {
        //Set phone number
        if (this.homePhoneNumber != null) {
            this.selectedClient.setHomePhone(this.homePhoneNumber.toString());
        } else {
            this.selectedClient.setHomePhone("");
        }
        if (this.cellPhoneNumber != null) {
            this.selectedClient.setCellPhone(this.cellPhoneNumber.toString());
        } else {
            this.selectedClient.setCellPhone("");
        }
        this.homePhoneNumber = new PhoneNumber();
        this.cellPhoneNumber = new PhoneNumber();
    }

    private void setPassword() {
        String initPassword = "123456";

        //Set salt and hashed password
        byte[] salt = getSalt();
        String saltStr = Base64.getEncoder().encodeToString(salt);
        this.selectedClient.setSalt(saltStr);

        String securePassword = getSecurePassword(initPassword, salt);
        this.selectedClient.setHash(securePassword);
    }

    /*
    * Get a random salt value
     */
    private byte[] getSalt() {
        //Create array for salt
        byte[] salt = new byte[16];
        try {
            //Always use a SecureRandom generator
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            //Get a random salt
            sr.nextBytes(salt);

        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            java.util.logging.Logger.getLogger(ClientsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return salt
        return salt;
    }

    /*
    * Generate a Secure Password using salt
     */
    private String getSecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt);
            //Get the hash's bytes 
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(ClientsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generatedPassword;
    }
}
