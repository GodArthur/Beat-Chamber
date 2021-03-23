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
    
    private String CardNumber = "";
    private String ExpDate = "";
    private String Cvv = "";
    private String Name = "";
    private String Address = "";
    private String City = "";
    
    public void setCardNumber(String value){
        this.CardNumber=value;
    }
    
    public void setDate(String value){
        this.ExpDate=value;
    }
    
    public void setCvv(String value){
        this.Cvv=value;
    }
    
    public void setName(String value){
        this.Name=value;
    }
    
    public void setAddress(String value){
        this.Address=value;
    }
    
    public void setCity(String value){
        this.City=value;
    }
    
    public String getCardNumber(){
        return this.CardNumber;
    }
    
    public String getCvv(){
        return this.Cvv;
    }
    
    public String getName(){
        return this.Name;
    }
    
    public String getAddress(){
        return this.Address;
    }
    
    public String getCity(){
        return this.City;
    }
    
    public String getDate(){
        return this.ExpDate;
    }
    
    @Override
    public String toString() {
        return "bean";
    }
}
