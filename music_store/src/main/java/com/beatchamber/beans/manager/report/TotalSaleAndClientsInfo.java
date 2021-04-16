/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans.manager.report;

/**
 *
 * @author Yan Tang
 */
public class TotalSaleAndClientsInfo {
    
    private double total;
    private String username;
    private int clientID;

    public TotalSaleAndClientsInfo(double total, String username, int clientID) {
        this.total = total;
        this.username = username;
        this.clientID = clientID;
    }

    public double getTotal() {
        return total;
    }

    public String getUsername() {
        return username;
    }

    public int getClientID() {
        return clientID;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
    
    
    
}
