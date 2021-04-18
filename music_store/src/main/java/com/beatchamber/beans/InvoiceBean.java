
package com.beatchamber.beans;

import com.beatchamber.entities.Albums;
import com.beatchamber.entities.Tracks;
import java.io.Serializable;
import java.util.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *Class is a bean containing information for invoices
 * 
 * @author Korjon Chang-Jones
 */
@Named("invoice")
@SessionScoped
public class InvoiceBean implements Serializable{
    
    
    private List<Tracks> tracks;
    
    private double pst;
    
    private double gst;
    
    private double hst;
    
    private double total;
    
    private double grossTotal;

    
    public InvoiceBean(){
        
    }

    public List<Tracks> getTracks() {
        return tracks;
    }

    public void setTracks(List<Tracks> tracks) {
        this.tracks = tracks;
    }

    public double getPst() {
        return pst;
    }

    public void setPst(double pst) {
        this.pst = pst;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getHst() {
        return hst;
    }

    public void setHst(double hst) {
        this.hst = hst;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(double grossTotal) {
        this.grossTotal = grossTotal;
    }
    
    
    
}
