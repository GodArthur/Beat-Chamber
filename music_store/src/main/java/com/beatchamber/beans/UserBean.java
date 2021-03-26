/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans;

import com.beatchamber.entities.Clients;
import com.beatchamber.jpacontroller.ClientsJpaController;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 1733570 Yan Tang
 */
@Named
@SessionScoped
public class UserBean implements Serializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(UserBean.class);

    private String username;
    private String password;
    private String passwordConfirm;
    private PhoneNumber homePhoneNumber;
    private PhoneNumber cellPhoneNumber;

    private Clients client;

    private boolean loggedIn;
    private boolean isManager;

    @Inject
    private ClientsJpaController clientsJpaController;

    // ------------------------------
    // Getters & Setters
    // ------------------------------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public PhoneNumber getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(PhoneNumber phoneNumber) {
        this.homePhoneNumber = phoneNumber;
    }

    public PhoneNumber getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(PhoneNumber phoneNumber) {
        this.cellPhoneNumber = phoneNumber;
    }

    public Clients getClient() {
        if (client == null) {
            client = new Clients();
        }
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

    public List<Clients> getClients() {
        return clientsJpaController.findClientsEntities();
    }

    /**
     * Login operation.
     *
     * @return
     */
    public String doLogin() {
        List<Clients> clientsList = getClients();
        for (Clients clientItem : clientsList) {
            String dbUsername = clientItem.getUsername();
            String dbEmail = clientItem.getEmail();
            String dbPassword = clientItem.getPassword();

            // Successful login
            if ((dbUsername.equals(username) || dbEmail.equals(username)) && dbPassword.equals(password)) {
                LOG.info("Successful login");
                loggedIn = true;
                this.client = clientItem;

                if (client.getTitle().equals("Manager")) {
                    this.isManager = true;
                    return "redirectToManagement";
                } else {
                    this.isManager = false;
                    return "redirectToIndex";
                }

            }
        }
        LOG.info("Unsuccessful login");

        // Set login ERROR
        FacesMessage msg = new FacesMessage("The username or the password is incorrect", "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        // To to login page
        return "toLogin";

    }

    /**
     * Logout operation.
     *
     * @return
     */
    public String doLogout() {
        // Set the parameter indicating that user is logged in to false
        loggedIn = false;

        // Set logout message
        FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage("errors", msg);

        LOG.info("Successful logout");
        return "redirectToIndex";
    }

    public void validatePasswordCorrect(FacesContext context, UIComponent component,
            Object value) {

        LOG.debug("validatePasswordCorrect");

        // Retrieve the value passed to this method
        String confirmPassword = (String) value;

        // Retrieve the temporary value from the password field
        UIInput passwordInput = (UIInput) component.findComponent("password");
        String password = (String) passwordInput.getLocalValue();

        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            LOG.debug("validatePasswordCorrect: " + client.getPassword() + " and " + confirmPassword);
            String message = context.getApplication().evaluateExpressionGet(context, "#{msgs['nomatch']}", String.class);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
            throw new ValidatorException(msg);
        }
    }

    /**
     * The method verifies that the Login name is not already in the database
     *
     * @param context
     * @param component
     * @param value
     */
    public void validateUniqueUser(FacesContext context, UIComponent component,
            Object value) {

        LOG.debug("validateUniquePassword");

        // Retrieve the value passed to this method
        String username = (String) value;

        LOG.debug("validateUniquePassword: " + username);

        if (clientsJpaController.findClients(username) != null) {
            String message = context.getApplication().evaluateExpressionGet(context, "#{msgs['duplicate']}", String.class);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
            throw new ValidatorException(msg);
        }
    }

    public String doCreateUser() throws Exception {
        this.client.setTitle("Consumer");
        this.client.setHomePhone(this.homePhoneNumber.toString());
        this.client.setCellPhone(this.cellPhoneNumber.toString());
        clientsJpaController.create(this.client);
        loggedIn = true;
        this.username = this.client.getUsername();
        this.client = new Clients();
        return "redirectToIndex";
    }

}
