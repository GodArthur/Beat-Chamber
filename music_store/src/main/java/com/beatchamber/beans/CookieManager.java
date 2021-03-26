/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;

/**
 *
 * @author kibra
 */
@Named("cookies")
@RequestScoped
public class CookieManager {
    private String cartKey = "CartItemsKey";
    FacesContext context = FacesContext.getCurrentInstance();
    Map<String,Object> cookies = new HashMap();
    

    
    /**
     * This method will return the number of Cookies that are made
     * @return String
     */
    public String getCount(){
        Map result = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        return result.size()+"";
    }
    
    /**
     * This method will find the value of the cookies from the given key
     * @param key
     * @return String 
     */
    public String findValue(String key){
        Object my_cookie = context.getExternalContext().getRequestCookieMap().get(key);
        String val="";
        if(my_cookie != null){
            val = ((Cookie)my_cookie).getValue();
        }
        return val;
    }
    
    /**
     * This method will create a cookie with the given key and value
     * @param key
     * @param value 
     */
    public void createCookie(String key,String value){
        FacesContext context = 	FacesContext.getCurrentInstance();
        context.getExternalContext().addResponseCookie(key, value,null);
    }
    
    /**
     * Adds the id to the cart if the id is not already in the cart
     * @param id 
     */
    public void addItemToCart(String idOfItem){
        if(!isItemInCart(idOfItem)){
            if(isCartEmpty()){
                createCookie(cartKey,idOfItem);
            }
            else{
                createCookie(cartKey,findValue(cartKey)+","+idOfItem);
            }
        }
    }
    
    /**
     * Check if the id of the is already in the cart
     * @param id
     * @return Boolean
     */
    public Boolean isItemInCart(String id){
     return findValue(cartKey).contains(id);
    }
    
    /**
     * Checks if the cart is empty
     * @return Boolean
     */
    private Boolean isCartEmpty(){
        if(findValue(cartKey).length() < 1){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * This method will clear the value of the given cookie value
     * @param key 
     */
    public void clearCookie(String key){
        createCookie(key,"");
    }
    
    /**
     * This method will return the number of items in the cart
     * @return String 
     */
    public String findNumberOfItemsInCart(){
        if(isCartEmpty()){
            return "0";
        }
        int count=0;
        for(String item:getAllIdFromCart()){
            if(item.length() > 0){
                count++;
                
            }
        }
        
        return count+"";
    }
    
    private String[] getAllIdFromCart(){
        String dataResult = findValue(com.beatchamber.util.Messages.getMessage("com.beatchamber.bundles.messages","cartKey",null).getDetail());
        return dataResult.split(",");
    }
    
    
    
    
}
