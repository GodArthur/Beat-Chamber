/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author kibra
 */
@Named("cookies")
@RequestScoped
public class CookieManager {
    Map<String,Object> cookies = new HashMap();
    
    public Map getAllCookies(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
    }
    
    public String getValues(){
        Map result = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        Collection collection = result.values();
        
        String lst = "";
        
        for(Object item : collection){
            if(item instanceof String){
                lst=lst + (String)item + ":";
            }
        }
        
        return lst;
        
    }
    
    public String getCount(){
        Map result = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        return (String)result.get("Bobby");
    }
    
    public Object findvalue(String key){
        
        this.cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        
        return cookies.get(key);
    }
    
    public void createCookie(String key,String value){
        FacesContext context = 	FacesContext.getCurrentInstance();
        context.getExternalContext().addResponseCookie(key, value,null);
    }
    
    public void addToCart(String id){
        String listKey = "";
        String listOfItems=(String)findvalue(listKey);
        listOfItems = listOfItems + "," + id;
        createCookie(listKey,listOfItems);
    }
    
}
