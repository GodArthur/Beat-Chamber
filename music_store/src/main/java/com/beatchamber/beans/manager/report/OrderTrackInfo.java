/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans.manager.report;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Yan Tang
 */
@Named
@RequestScoped
public class OrderTrackInfo {

    private int trackid;

    private int albumNumber;

    private String albumTitle;

    private String trackTitle;

    private double listPrice;

    private double salePrice;

    private Date orderDate;

    public OrderTrackInfo() {

    }

    public OrderTrackInfo(int trackid, int albumNumber, String albumTitle, String trackTitle, double listPrice, double salePrice, Date orderDate) {
        this.trackid = trackid;
        this.albumNumber = albumNumber;
        this.albumTitle = albumTitle;
        this.trackTitle = trackTitle;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
        this.orderDate = orderDate;
    }

    public int getTrackId() {
        return trackid;
    }

    public int getAlbumNumber() {
        return albumNumber;
    }

    public double getListPrice() {
        return listPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setTrackid(int trackid) {
        this.trackid = trackid;
    }

    public void setAlbumNumber(int albumNumber) {
        this.albumNumber = albumNumber;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

}
