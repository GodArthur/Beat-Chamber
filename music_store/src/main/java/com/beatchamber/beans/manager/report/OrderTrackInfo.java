/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans.manager.report;

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

    private String trackTitle;

    private double listPrice;

    private double salePrice;

    public OrderTrackInfo() {

    }

    public OrderTrackInfo(int trackid, String trackTitle, double listPrice, double salePrice) {
        this.trackid = trackid;
        this.trackTitle = trackTitle;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
    }

    public int getTrackId() {
        return trackid;
    }

    public double getListPrice() {
        return listPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackid(int trackid) {
        this.trackid = trackid;
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

}
