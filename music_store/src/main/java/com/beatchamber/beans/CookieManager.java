
package com.beatchamber.beans;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
     * @author Ibrahim
     */
    public String getCount(){
        Map result = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        return result.size()+"";
    }
    
    /**
     * This method will find the value of the cookies from the given key
     * @param key
     * @return String
     * @author Ibrahim
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
     * @author Ibrahim
     */
    public void createCookie(String key,String value){
        FacesContext context = 	FacesContext.getCurrentInstance();
        context.getExternalContext().addResponseCookie(key, value,null);
    }
    
    /**
     * Adds the id to the cart if the id is not already in the cart
     * @param id 
     * @author Ibrahim
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
     * (checks for the albums)
     * This method will be used in the cart page it will check if the id is in the cart and if it is it wil return none so the div containing the information will not be shown
     * (This method was made because it takes some time for the cookies to be updated and be reflected on he page so this is a shortcut so that the user will not need to wait for the page to be updated after the cookies has been changed)
     * @param idToCheck
     * @return String 
     * @author Ibrahim
     */
    public String checkIfAlbumIsInCart(String idToCheck){
        for(String item:getAllIdFromCart()){
            if(item.equals("a"+idToCheck)){
                return "";
            }
            
        }
        return "none";
    }
    
    /**
     * (checks for the tracks)
     * This method will be used in the cart page it will check if the id is in the cart and if it is it wil return none so the div containing the information will not be shown
     * (This method was made because it takes some time for the cookies to be updated and be reflected on he page so this is a shortcut so that the user will not need to wait for the page to be updated after the cookies has been changed)
     * @param idToCheck
     * @return String
     * @author Ibrahim
     */
    public String checkIfTrackIsInCart(String idToCheck){
        for(String item:getAllIdFromCart()){
            if(item.equals(idToCheck)){
                return "";
            }
            
        }
        return "none";
    }
    
    /**
     * This method will reload the page
     * @throws IOException 
     * @author Ibrahim
     */
    public void reload() throws IOException {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
}
    /**
     * This method will add the add the album to the cart cookie (it takes the string of the albums database id so not a15 but just 15)
     * @param albumId 
     * @author Ibrahim
     */
    public void addAlbumToCart(String albumId){
        addItemToCart("a"+albumId);
        try {
            reload();
        } catch (IOException ex) {
            Logger.getLogger(CookieManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method will add the add the tracks to the cart cookie 
     * @param albumId 
     */
    public void addTrackToCart(String trackId){
        addItemToCart(trackId);
        try {
            reload();
        } catch (IOException ex) {
            Logger.getLogger(CookieManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method returns true if the id given is in the cart
     * @param id
     * @return Boolean
     * @author Ibrahim
     */
    private Boolean isIdInCart(String id){
        for(String item:getAllIdFromCart()){
            if(item.equals(id)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * This method will return a string that represents if the button should be disabled because it alredy is in the cart
     * @param albumId
     * @return String
     * @author Ibrahim
     */
    public String shouldTheAddToCartBeDisabled(int albumId){
     if(isIdInCart("a"+albumId)){
         return "true";
     }
     else{
         return "false";
     }
    }
    
    /**
     * This method will display the default txt on the button if the album is not already in the cart or if the album is already in the cart
     * it will return a message on the button to say that the album is alredy in the cart
     * @param albumId
     * @param buttonText
     * @return String
     * @author Ibrahim
     */
    public String adjustDisplay(String albumId,String buttonText){
        if(isIdInCart("a"+albumId)){
            return com.beatchamber.util.Messages.getMessage("com.beatchamber.bundles.messages","Album_Alredy_In_Cart",null).getDetail();
        }
        else{
            return buttonText;
        }
    }
    
    /**
     * This method will clear the items the in the cart
     */
    public void clearTheCart(){
        clearCookie(com.beatchamber.util.Messages.getMessage("com.beatchamber.bundles.messages","cartKey",null).getDetail());
        try {
            reload();
        } catch (IOException ex) {
            Logger.getLogger(CookieManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method will remove the id from the cart
     * @param idToRemove
     * @throws InterruptedException 
     * @author Ibrahim
     */
    public void removeItemFromCart(String idToRemove) throws InterruptedException{
        String newCookieList = "";
        if(!isCartEmpty()){
            try {
                for (String item:getAllIdFromCart()) {
                    if(!item.equals(idToRemove)){
                        newCookieList = newCookieList + item + "," ;
                    }
                }
                createCookie(cartKey,newCookieList);
                reload();
            } catch (IOException ex) {
                Logger.getLogger(CookieManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    /**
     * Check if the id of the is already in the cart
     * @param id
     * @return Boolean
     * @author Ibrahim
     */
    public Boolean isItemInCart(String id){
     return findValue(cartKey).contains(id);
    }
    
    /**
     * Checks if the cart is empty
     * @return Boolean
     * @author Ibrahim
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
     * @author Ibrahim
     */
    public void clearCookie(String key){
        createCookie(key,"");
    }
    
    /**
     * This method will return the number of items in the cart
     * @return String 
     * @author Ibrahim
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
    
    /**
     * This method will return a string array of all of the ids of the items that are in the cart
     * @return String 
     * @author Ibrahim
     */
    private String[] getAllIdFromCart(){
        String dataResult = findValue(com.beatchamber.util.Messages.getMessage("com.beatchamber.bundles.messages","cartKey",null).getDetail());
        return dataResult.split(",");
    }
    
    
    
    
}
