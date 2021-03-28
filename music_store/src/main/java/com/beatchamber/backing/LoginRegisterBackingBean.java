/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.backing;

import com.beatchamber.beans.PhoneNumber;
import com.beatchamber.entities.Clients;
import com.beatchamber.jpacontroller.ClientsJpaController;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
@Named("theLoginRegister")
@SessionScoped
public class LoginRegisterBackingBean implements Serializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(LoginRegisterBackingBean.class);

    private Integer ClientId;
    private String username;
    private String password;
    private String passwordConfirm;
    private PhoneNumber homePhoneNumber;
    private PhoneNumber cellPhoneNumber;

    private Clients client;
    private List<Clients> clients;

    private boolean loggedIn;
    private boolean isManager;

    private static SecureRandom random = new SecureRandom();

    @Inject
    private ClientsJpaController clientsJpaController;

    @PostConstruct
    public void init() {
        this.clients = clientsJpaController.findClientsEntities();
    }

    // ------------------------------
    // Getters & Setters
    // ------------------------------
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getClientId() {
        return this.ClientId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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

    public Clients getClient() {
        if (this.client == null) {
            this.client = new Clients();
        }
        return this.client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isManager() {
        return this.isManager;
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
            String dbSalt = clientItem.getSalt();
            String dbhashPassword = clientItem.getHash();

            boolean isPasswordMatch = false;
            if (dbSalt != null && dbhashPassword != null) {
                byte[] hashpsword = hash(password, dbSalt);
                String hashpswordStr = Arrays.toString(hashpsword);
                if (dbhashPassword.equals(hashpswordStr)) {
                    isPasswordMatch = true;
                }
            } else if (dbPassword.equals(password)) {
                isPasswordMatch = true;
            }

            // Successful login
            if ((dbUsername.equals(username) || dbEmail.equals(username)) && isPasswordMatch) {
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

        //clear the form
        this.client = new Clients();
        this.homePhoneNumber = new PhoneNumber();
        this.cellPhoneNumber = new PhoneNumber();
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
        this.clients = clientsJpaController.findClientsEntities();

        for (Clients client : this.clients) {
            if (client.getUsername().equals(username)) {
                String message = context.getApplication().evaluateExpressionGet(context, "#{msgs['duplicate']}", String.class);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
                throw new ValidatorException(msg);
            }
        }
    }

    public String doCreateUser() throws Exception {
        // set all the necessary fields which cannot get from input to DB
        setClientFields();
        // create a new client
        clientsJpaController.create(this.client);

        //set all the fields for passing value to other pages
        loggedIn = true;
        this.ClientId = this.client.getClientNumber();
        this.username = this.client.getUsername();

        //clear the form
        this.client = new Clients();
        this.homePhoneNumber = new PhoneNumber();
        this.cellPhoneNumber = new PhoneNumber();
        return "redirectToIndex";
    }

    /*
    * Set all the necessary fields which cannot get from input to DB
    */
    private void setClientFields() {
        this.client.setTitle("Consumer");
        
        if (this.homePhoneNumber != null) {
            this.client.setHomePhone(this.homePhoneNumber.toString());
        }
        if (this.cellPhoneNumber != null) {
            this.client.setCellPhone(this.cellPhoneNumber.toString());
        }
        
//        String salt = getSalt();
//        this.client.setSalt(salt);
//
//        byte[] hashPsword = hash(this.client.getPassword(), salt);
//        char[] hashPswordChar = new char[hashPsword.length];
//
//        for (int i = 0; i < hashPsword.length; i++) {
//            hashPswordChar[i] = (char) hashPsword[i];
//        }
//
//        String hashPswordStr = String.valueOf(hashPswordChar);
//        this.client.setHash(hashPswordStr);
    }

    //Creates a randomly generated String
    public String getSalt() {
        return new BigInteger(140, random).toString(32);
    }

    //Takes a password and a salt a performs a one way hashing on them, returning an array of bytes.
    public byte[] hash(String password, String salt) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1024, 256);

            SecretKey key = skf.generateSecret(spec);
            byte[] hash = key.getEncoded();
            return hash;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
