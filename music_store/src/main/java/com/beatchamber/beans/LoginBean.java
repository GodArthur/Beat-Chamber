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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1733570 Yan Tang
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(LoginBean.class.getName());

    private String username;
    private String password;

    private boolean loggedIn;

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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
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
        for (Clients client : clientsList) {
            String dbUsername = client.getUsername();
            String dbEmail = client.getEmail();
            String dbPassword = client.getPassword();

            // Successful login
            if ((dbUsername.equals(username)||dbEmail.equals(username)) && dbPassword.equals(password)) {
                LOG.info("Successful login");
                loggedIn = true;
                //return navigationBean.toWelcome();
                if(client.getTitle().equals("Manager")){
                    return "manageClients.xhtml"; 
                }
                else{
                    return "index.xhtml";
                }
                
            }
        }
        LOG.info("Unsuccessful login");

        // Set login ERROR
        FacesMessage msg = new FacesMessage("The username or rhe password is incorrect", "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        // To to login page
        //return navigationBean.toLogin();
        return "login.xhtml";

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
        // return navigationBean.redirectToLogin();
        return "index.xhtml";
    }

}
