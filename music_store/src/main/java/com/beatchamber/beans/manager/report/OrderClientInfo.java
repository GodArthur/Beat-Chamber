/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans.manager.report;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Yan Tang
 */
@Named
@RequestScoped
public class OrderClientInfo {
    private int clientNumber;
    
    private double orderTotal;

    private String username;

    public OrderClientInfo(){
        
    }
    
    public OrderClientInfo(int clientID, String userName, double orderTotal) {
        this.clientNumber = clientID;
        this.orderTotal = orderTotal;
        this.username = userName;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public String getUsername() {
        return username;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
    
    
}
