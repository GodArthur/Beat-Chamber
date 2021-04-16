package com.beatchamber.beans.manager;

import com.beatchamber.entities.OrderTrack;
import com.beatchamber.entities.Orders;
import com.beatchamber.jpacontroller.OrderAlbumJpaController;
import com.beatchamber.jpacontroller.OrderTrackJpaController;
import com.beatchamber.jpacontroller.OrdersJpaController;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *This class will be used as the bean for the order management page
 * @author Massimo Di Girolamo
 */
@Named
@RequestScoped
public class OrderBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(OrderBean.class);

    @Inject
    private OrdersJpaController ordersJpaController;
    
    @Inject
    private OrderAlbumJpaController orderAlbumJpaController;
    
    @Inject
    private OrderTrackJpaController orderTrackJpaController;
    
    private List<OrderTrack> orderTrackList;
    
    private List<Orders> ordersList;

    private Orders selectedOrder;
    
    private List<Orders> filteredOrdersList;
    
    //All the db values
    private int tableKey;
    private int order_id;
    private double order_total;
    private int client_number;
    private Date order_date;
    private boolean visible;
    
    //Order Track
    private int orderTrackId;
    
    //Order Album
    private int orderAlbumId;
    
    /**
     * Initialization.
     */
    @PostConstruct
    public void init() {
        this.ordersList = ordersJpaController.findOrdersEntities();
        this.orderTrackList = orderTrackJpaController.findOrderTrackEntities();
    }
    
    /**
     * Constructor
     */
    public OrderBean(){
        this.selectedOrder = new Orders();
    }
    
    //Getters and setters for all the dv values
    public int getTableKey(){
        return this.tableKey;
    }
    
    public void setTableKey(int tblKey){
        this.tableKey = tblKey;
    }
    
    public int getOrder_id(){
        return this.order_id;
    }
    
    public void setOrder_id(int ordId){
        this.order_id = ordId;
    }
    
    public double getOrder_total(){
        return this.order_total;
    }
    
    public void setOrder_total(double ordTotal){
        this.order_total = ordTotal;
    }
    
    public int getClient_number(){
        return this.client_number;
    }
    
    public void setClient_number(int clntNum){
        this.client_number = clntNum;
    }
    
    public Date getOrder_date(){
        return this.order_date;
    }
    
    public void setOrder_date(Date ordDate){
        this.order_date = ordDate;
    }
    
    public boolean getVisible(){
        return this.visible;
    }
    
    public void setVisible(boolean isVisible){
        this.visible = isVisible;
    }
    
    public int getOrderTrackId(){
        return this.orderTrackId;
    }
    
    public void setOrderTrackId(int trackId){
        this.orderTrackId = trackId;
    }
    
    public int getOrderAlbumId(){
        return this.orderAlbumId;
    }
    
    public void setOrderAlbumId(int albumId){
        this.orderAlbumId = albumId;
    }
    
    
    /**
     * Get all the orders.
     *
     * @return a list of orders in the database.
     */
    public List<Orders> getOrdersList() {

        return ordersList;
    }

    /**
     * Get the selected the order.
     *
     * @return the selected order.
     */
    public Orders getSelectedOrder() {
        return selectedOrder;
    }
    
    /**
     * Set the selected order.
     *
     * @param selectedOrder the selected order.
     */
    public void setSelectedOrder(Orders selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    /**
     * Get the filteredOrdersList.
     *
     * @return a list of filteredOrdersList.
     */
    public List<Orders> getFilteredOrdersList() {
        return filteredOrdersList;
    }

    /**
     * Set the filteredOrdersList.
     *
     * @param filteredOrdersList a list of filteredOrdersList.
     */
    public void setFilteredOrdersList(List<Orders> filteredOrdersList) {
        this.filteredOrdersList = filteredOrdersList;
    }
    
    /**
     * The message that shows when visibility changes
     */
    public void showVisibleMessage(){
        if(this.visible){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The order is visible"));
        }
        
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The order is no longer visible"));
        }
        
    }
    
    
}
