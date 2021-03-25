package com.beatchamber.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author kibra
 */
@Entity
@Table(name = "invoices", catalog = "CSgb1w21", schema = "")
@NamedQueries({
    @NamedQuery(name = "Invoices.findAll", query = "SELECT i FROM Invoices i"),
    @NamedQuery(name = "Invoices.findBySaleNumber", query = "SELECT i FROM Invoices i WHERE i.saleNumber = :saleNumber"),
    @NamedQuery(name = "Invoices.findBySaleDate", query = "SELECT i FROM Invoices i WHERE i.saleDate = :saleDate"),
    @NamedQuery(name = "Invoices.findByTotalNetValue", query = "SELECT i FROM Invoices i WHERE i.totalNetValue = :totalNetValue"),
    @NamedQuery(name = "Invoices.findByPst", query = "SELECT i FROM Invoices i WHERE i.pst = :pst"),
    @NamedQuery(name = "Invoices.findByGst", query = "SELECT i FROM Invoices i WHERE i.gst = :gst"),
    @NamedQuery(name = "Invoices.findByHst", query = "SELECT i FROM Invoices i WHERE i.hst = :hst"),
    @NamedQuery(name = "Invoices.findByTotalGrossValue", query = "SELECT i FROM Invoices i WHERE i.totalGrossValue = :totalGrossValue")})
public class Invoices implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sale_number")
    private Integer saleNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sale_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_net_value")
    private double totalNetValue;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_gross_value")
    private double totalGrossValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "saleNumber")
    private List<InvoiceDetails> invoiceDetailsList;
    @JoinColumn(name = "client_number", referencedColumnName = "client_number")
    @ManyToOne(optional = false)
    private Clients clientNumber;

    public Invoices() {
    }

    public Invoices(Integer saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Invoices(Integer saleNumber, Date saleDate, double totalNetValue, double pst, double gst, double hst, double totalGrossValue) {
        this.saleNumber = saleNumber;
        this.saleDate = saleDate;
        this.totalNetValue = totalNetValue;
        this.pst = pst;
        this.gst = gst;
        this.hst = hst;
        this.totalGrossValue = totalGrossValue;
    }

    public Integer getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Integer saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public double getTotalNetValue() {
        return totalNetValue;
    }

    public void setTotalNetValue(double totalNetValue) {
        this.totalNetValue = totalNetValue;
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

    public double getTotalGrossValue() {
        return totalGrossValue;
    }

    public void setTotalGrossValue(double totalGrossValue) {
        this.totalGrossValue = totalGrossValue;
    }

    public List<InvoiceDetails> getInvoiceDetailsList() {
        return invoiceDetailsList;
    }

    public void setInvoiceDetailsList(List<InvoiceDetails> invoiceDetailsList) {
        this.invoiceDetailsList = invoiceDetailsList;
    }

    public Clients getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(Clients clientNumber) {
        this.clientNumber = clientNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saleNumber != null ? saleNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoices)) {
            return false;
        }
        Invoices other = (Invoices) object;
        if ((this.saleNumber == null && other.saleNumber != null) || (this.saleNumber != null && !this.saleNumber.equals(other.saleNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.beatchamber.entities.Invoices[ saleNumber=" + saleNumber + " ]";
    }
    
}
