/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author kibra
 */
@Entity
@Table(name = "invoice_details", catalog = "beat_chamber", schema = "")
@NamedQueries({
    @NamedQuery(name = "InvoiceDetails.findAll", query = "SELECT i FROM InvoiceDetails i"),
    @NamedQuery(name = "InvoiceDetails.findByTablekey", query = "SELECT i FROM InvoiceDetails i WHERE i.tablekey = :tablekey"),
    @NamedQuery(name = "InvoiceDetails.findByPst", query = "SELECT i FROM InvoiceDetails i WHERE i.pst = :pst"),
    @NamedQuery(name = "InvoiceDetails.findByGst", query = "SELECT i FROM InvoiceDetails i WHERE i.gst = :gst"),
    @NamedQuery(name = "InvoiceDetails.findByHst", query = "SELECT i FROM InvoiceDetails i WHERE i.hst = :hst")})
public class InvoiceDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tablekey")
    private Integer tablekey;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PST")
    private double pst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GST")
    private double gst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HST")
    private double hst;
    @JoinColumn(name = "sale_number", referencedColumnName = "sale_number")
    @ManyToOne(optional = false)
    private Invoices saleNumber;
    @JoinColumn(name = "track_id", referencedColumnName = "track_id")
    @ManyToOne(optional = false)
    private Tracks trackId;

    public InvoiceDetails() {
    }

    public InvoiceDetails(Integer tablekey) {
        this.tablekey = tablekey;
    }

    public InvoiceDetails(Integer tablekey, double pst, double gst, double hst) {
        this.tablekey = tablekey;
        this.pst = pst;
        this.gst = gst;
        this.hst = hst;
    }

    public Integer getTablekey() {
        return tablekey;
    }

    public void setTablekey(Integer tablekey) {
        this.tablekey = tablekey;
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

    public Invoices getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Invoices saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Tracks getTrackId() {
        return trackId;
    }

    public void setTrackId(Tracks trackId) {
        this.trackId = trackId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tablekey != null ? tablekey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvoiceDetails)) {
            return false;
        }
        InvoiceDetails other = (InvoiceDetails) object;
        if ((this.tablekey == null && other.tablekey != null) || (this.tablekey != null && !this.tablekey.equals(other.tablekey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.beatchamber.testing.InvoiceDetails[ tablekey=" + tablekey + " ]";
    }
    
}
