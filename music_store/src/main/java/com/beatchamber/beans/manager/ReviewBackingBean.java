package com.beatchamber.beans.manager;

import com.beatchamber.beans.CustomerReviewBean;
import com.beatchamber.entities.CustomerReviews;
import com.beatchamber.jpacontroller.ClientsJpaController;
import com.beatchamber.jpacontroller.CustomerReviewsJpaController;
import com.beatchamber.jpacontroller.TracksJpaController;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Massimo Di Girolamo
 */
@Named()
@RequestScoped
public class ReviewBackingBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ReviewBackingBean.class);

    @Inject
    private CustomerReviewsJpaController customerReviewsJpaController;
    
    @Inject
    private TracksJpaController tracksJpaController;

    private List<CustomerReviews> customerReviewsList;

    private CustomerReviews selectedCustomerReview;
    
    private ClientsJpaController clientsJpaController;

    private int track_id;
    private int client_number;
    private Date review_date;
    private int rating;
    private String review_text;
    
    private boolean approval_status;

    private List<CustomerReviews> filteredCustomerReviewList;
    

    /**
     * Initialization.
     */
    @PostConstruct
    public void init() {
        this.customerReviewsList = customerReviewsJpaController.findCustomerReviewsEntities();
    }
    
    /**
     * Constructor, creates the selected Customer Review
     */
    public ReviewBackingBean(){
        this.selectedCustomerReview = new CustomerReviews();
    }

    /**
     * Get all the customer reviews.
     *
     * @return a list of customer reviews in the database.
     */
    public List<CustomerReviews> getCustomerReviews() {

        return customerReviewsList;
    }

    /**
     * Get the selected the customer review.
     *
     * @return the selected customer review.
     */
    public CustomerReviews getSelectedCustomerReviews() {
        return selectedCustomerReview;
    }

    /**
     * Set the selected customer review.
     *
     * @param selectedCustomerReview the selected client.
     */
    public void setSelectedCustomerReviews(CustomerReviews selectedCustomerReview) {
        this.selectedCustomerReview = selectedCustomerReview;
    }

    /**
     * Get the filteredCustomerReviewList.
     *
     * @return a list of filteredCustomerReviewList.
     */
    public List<CustomerReviews> getFilteredCustomerReviewList() {
        return filteredCustomerReviewList;
    }

    /**
     * Set the filteredCustomerReviewList.
     *
     * @param filteredCustomerReviewList a list of filteredCustomerReviewList.
     */
    public void setFilteredCustomerReviewList(List<CustomerReviews> filteredCustomerReviewList) {
        this.filteredCustomerReviewList = filteredCustomerReviewList;
    }

    public boolean getApproval_Status(){
        return this.approval_status;
    }
    
    public void setApproval_Status(boolean approval_status) {
        this.approval_status = approval_status;
    }

//    /**
//     * Initialize the selectedCustomerReview field when opening the add new
//     * dialog.
//     */
//    public void openNew() {
//        this.selectedCustomerReview = new CustomerReviews();
//    }

    
    public void changeApprovalStatus(CustomerReviewBean custReviewbean, boolean isApproved) throws Exception{
        
        //Create the customer review with values
        this.selectedCustomerReview.setTrackId(tracksJpaController.findTracks(custReviewbean.getTrack_id()));
        this.selectedCustomerReview.setReviewDate(custReviewbean.getReview_date());
        //Might need to change
        this.selectedCustomerReview.setApprovalStatus(isApproved);
        this.selectedCustomerReview.setClientNumber(this.clientsJpaController.findClients(custReviewbean.getClient_number()));
        this.selectedCustomerReview.setRating(custReviewbean.getRating());
        this.selectedCustomerReview.setReviewText(custReviewbean.getReview_Text());
        customerReviewsJpaController.edit(selectedCustomerReview);
    }
}
