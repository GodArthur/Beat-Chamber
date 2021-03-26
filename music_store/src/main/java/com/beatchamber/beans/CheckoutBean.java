/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans;

import com.beatchamber.entities.Albums;
import com.beatchamber.entities.Tracks;
import com.beatchamber.jpacontroller.AlbumsJpaController;
import com.beatchamber.jpacontroller.TracksJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private ArrayList<Albums> listOfAlbumsInTheCart = new ArrayList<Albums>();
    private ArrayList<Tracks> listOfTracksInTheCart = new ArrayList<Tracks>();

    
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
    
    public String getCartSize(){
        return getAllIdFromCart().length + "";
    }
    
    private String[] getAllIdFromCart(){
        CookieManager cookies = new CookieManager();
        String dataResult = cookies.findValue(com.beatchamber.util.Messages.getMessage("com.beatchamber.bundles.messages","cartKey",null).getDetail());
        return dataResult.split(",");
    }
    
    private void SetListOfItems(){
        AlbumsJpaController albumController = new AlbumsJpaController();
        TracksJpaController trackController = new TracksJpaController();
        
        String[] data = getAllIdFromCart();
        for(String item:data){
            if(item.length()>0){
                if(item.toLowerCase().contains("a")){
                    listOfAlbumsInTheCart.add(albumController.findAlbums(parseStringToInt(item.replace("a", ""))));
                }
                else{
                    listOfTracksInTheCart.add(trackController.findTracks(parseStringToInt(item)));
                }
            }
        }
    }
    
    /**
     * This method will return the int value of the string
     * @param strToParse
     * @return in
     */
    private int parseStringToInt(String strToParse){
        return Integer.parseInt(strToParse);
    }
    
    /**
     * This method will allow us to get the list of albums that are in the cart
     * @return ArrayList of albums
     */
    public ArrayList<Albums> retrieveAllAlbumsInTheCart(){
        SetListOfItems();
        return this.listOfAlbumsInTheCart;
    }
    
    /**
     * This method will allow us to get the list of tracks that are in the cart
     * @return ArrayList of tracks
     */
    public ArrayList<Tracks> retrieveAllTracksInTheCart(){
        SetListOfItems();
        System.out.println( listOfTracksInTheCart.size()+ "  :size**************************************");
        return this.listOfTracksInTheCart;
    }
    
    public List<String> getx(){
        List<String> test = new ArrayList<String>();
        test.add("1");
        test.add("2");
        test.add("3");
        test.add("4");
        return test;
    }
}
