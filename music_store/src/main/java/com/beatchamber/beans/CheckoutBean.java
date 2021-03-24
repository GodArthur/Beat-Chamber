/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author kibra
 */
@Named("checkoutData")
@RequestScoped
public class CheckoutBean implements Serializable {
    
    private String cardNumber = "";
    private String expDate = "";
    private String cvv = "";
    private String name = "";
    private String address = "";
    private String city = "";
    private String province = "-1";
    
    /*all of the setters */
    public void setCardNumber(String value){
        this.cardNumber=value;
    }
    
    public void setDate(String value){
        this.expDate=value;
    }
    
    public void setCvv(String value){
        this.cvv=value;
    }
    
    public void setName(String value){
        this.name=value;
    }
    
    public void setAddress(String value){
        this.address=value;
    }
    
    public void setCity(String value){
        this.city=value;
    }
    
    public void setProvince(String value){
        this.province = value;
    }
    
    /*all of the getters*/
    public String getProvince(){
        return this.province;
    }
    
    public String getCardNumber(){
        return this.cardNumber;
    }
    
    public String getCvv(){
        return this.cvv;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public String getCity(){
        return this.city;
    }
    
    public String getDate(){
        return this.expDate;
    }
    
    /*overiding the toString method*/
    @Override
    public String toString() {
        return "pop";
    }
}
