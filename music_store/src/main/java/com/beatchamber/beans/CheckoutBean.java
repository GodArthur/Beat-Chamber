/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans;



import com.beatchamber.entities.Albums;
import com.beatchamber.entities.OrderAlbum;
import com.beatchamber.entities.OrderTrack;
import com.beatchamber.entities.Orders;
import com.beatchamber.entities.Tracks;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.jpacontroller.ClientsJpaController;
import com.beatchamber.jpacontroller.OrderAlbumJpaController;
import com.beatchamber.jpacontroller.OrderTrackJpaController;
import com.beatchamber.jpacontroller.OrdersJpaController;
import com.beatchamber.jpacontroller.TracksJpaController;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



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
    private String province = "-1";//This needs to be -1 so that we know that it is when the page has just been loaded and the first province will be shown (alberta)
    private double totalPrice = 0;
    private double totalPst = 0;
    private double totalGst = 0;
    private double totalHst = 0;
    @PersistenceContext(unitName = "music_store_persistence")
    private EntityManager em;
    
    @Inject
    private LoginRegisterBean userLoginBean;
    
    @Inject 
    OrdersJpaController orderController;
    
    @Inject
    ClientsJpaController clientController;
    
    @Inject
    private OrderAlbumJpaController orderAlbumController; 
    
    @Inject
    private OrderTrackJpaController orderTrackController;
    
    @Inject
    private TracksJpaController trackController;
    
    private final static Logger LOG = LoggerFactory.getLogger(OrdersJpaController.class);


    
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
        return "";
    }
    
    /**
     * This method will reload the page 
     */
    public void reload() throws IOException {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
}
    

    
    /**
     * This method will return the int value of the string
     * @param strToParse
     * @return in
     * @author Ibrahim
     */
    private int parseStringToInt(String strToParse){
        return Integer.parseInt(strToParse);
    }
    

    /**
     * This method will get the total price of the items that are in the cart and return the value 
     * @param trackTotal
     * @param albumTotal
     * @return String
     * @author Ibrahim
     */
    public String computePrices(String trackTotal,String albumTotal){
        totalPrice = (convertStringToDouble(trackTotal)+convertStringToDouble(albumTotal));
        return totalPrice + "";
    }
    
    /**
     * This method will convert a string into a double
     * @param data
     * @return double
     * @author Ibrahim
     */
    private double convertStringToDouble(String data){
        return Double.parseDouble(data);
    }


    /**
     * This method will compute the pst value from the total price before tax
     * @param total
     * @param percentage
     * @return String 
     * @author Ibrahim
     */    
    public String computePst(String total,String percentage){
        this.totalPst = getPercentagePrice(convertStringToDouble(total),convertStringToDouble(percentage));
        return this.totalPst+"";
    }
    /**
     * This method will compute the gst value from the total price before tax
     * @param total
     * @param percentage
     * @return String
     * @author Ibrahim
     */
    public String computeGst(String total,String percentage){
        this.totalGst = getPercentagePrice(convertStringToDouble(total),convertStringToDouble(percentage));
        return this.totalGst+"";
    }
    
    /**
     * This method will compute the pst value from the total price before tax
     * @param total
     * @param percentage
     * @return String
     * @author Ibrahim
     */
    public String computeHst(String total,String percentage){
        this.totalHst = getPercentagePrice(convertStringToDouble(total),convertStringToDouble(percentage));
        return this.totalHst+"";
    }
    

    /**
     * This method will return the string of the total price 
     * @return String
     * @author Ibrahim
     */
    public String getPriceBeforeTax(){
        return this.totalPrice+"";
    }
    
    /**
     * This method will return the total price with the tax added to it
     * @param total
     * @param pstValue
     * @param gstValue
     * @param hstValue
     * @return String
     * @author Ibrahim
     */
    public double getToatlPriceWithTax(String total,String pstValue,String gstValue,String hstValue){
        return roundDoubleValue(convertStringToDouble(total)+convertStringToDouble(computePst(total,pstValue))+convertStringToDouble(computePst(total,gstValue))+convertStringToDouble(computePst(total,hstValue)));
    }
    
    /**
     * This method will compute the percentage of the double value that has been passed (it will also round the value to 2 digits)
     * @param value
     * @param percentage
     * @return double
     * @author Ibrahim
     */
    private double getPercentagePrice(double value,double percentage){
        double result = 0;
        if(percentage == 0){
            result = 0;
        }
        else{
            result = roundDoubleValue((value*percentage)/100);
        }
        
        return result;
        
    }
    
    /**
     * This method will round the double to 2 digit 
     * found some help on this website : https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     * @param value
     * @return Double
     * @author Ibrahim
     * 
     */
    private Double roundDoubleValue(double value){
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(value));
    }
    
    /**
     * This method will check if the use is logged in if they are not they will go to the login page
     * @param fileToGoTo
     * @return String
     */
    public String checkoutRedirect(String fileToGoTo) {

        if (userLoginBean.getClient().getFirstName() == null) {
            return "login.xhtml";
        } else {
            return fileToGoTo;
        }
    }
    
    /**
     * This method will add the orders in the appropriate order tables
     *
     * @param ClientNumber
     * @param albumList
     * @param trackList
     * @return String
     * @author Ibrahim
     */
    public String addOrdersToTable(int ClientNumber, ArrayList<Albums> albumList, ArrayList<Tracks> trackList, double totalPrice) {
        //set variables
        Orders order = new Orders();
        Date date = new Date();
        CookieManager cookiesManager = new CookieManager();
        int newOrderId = orderController.getOrdersCount() + 1;
        //clientNumber = em.getReference(clientNumber.getClass(), clientNumber.getClientNumber());
        //creating the order
        order.setOrderDate(date);
        order.setClientNumber(clientController.findClients(ClientNumber));
        order.setVisible(true);
        order.setOrderId(newOrderId);
        order.setOrderTotal(totalPrice);
        try {
            orderController.create(order);
        } catch (RollbackFailureException ex) {
            LOG.error("orders order roll back error");
        }

        //creating the orderAlbums
        for (Albums item : albumList) {
            OrderAlbum orderAlbum = new OrderAlbum();
            orderAlbum.setAlbumId(item);
            orderAlbum.setOrderId(newOrderId);
            try {
                orderAlbumController.create(orderAlbum);
            } catch (RollbackFailureException ex) {
                LOG.error("order rollback error");
            }
        }

        //creating the orderTrack
        for (Tracks item : trackList) {
            OrderTrack orderTrack = new OrderTrack();
            orderTrack.setOrderId(newOrderId);
            orderTrack.setTrackId(trackController.findTracks(item.getTrackId()));
            try {
                orderTrackController.create(orderTrack);
            } catch (RollbackFailureException ex) {
                LOG.error("order track error");
            }
        }
        cookiesManager.clearTheCart();
        return "about.xhtml";
    }
    
    
}
