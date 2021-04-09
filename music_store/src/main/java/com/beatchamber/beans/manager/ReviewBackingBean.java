package com.beatchamber.beans.manager;

import com.beatchamber.entities.CustomerReviews;
import com.beatchamber.jpacontroller.CustomerReviewsJpaController;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
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
@Named("reviews")
@ViewScoped
public class ReviewBackingBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(ReviewBackingBean.class);

    @Inject
    private CustomerReviewsJpaController customerReviewsJpaController;

    private List<CustomerReviews> customerReviewsList;

    private CustomerReviews selectedCustomerReview;

    private List<CustomerReviews> filteredCustomerReviewList;
    
    private boolean approval_status = false;

    /**
     * Initialization.
     */
    @PostConstruct
    public void init() {
        this.customerReviewsList = customerReviewsJpaController.findCustomerReviewsEntities();
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
    
    public boolean isApproved() {
        return approval_status;
    }

    public void setApprovalStatus(boolean approval_status) {
        this.approval_status = approval_status;
    }

    /**
     * Initialize the selectedCustomerReview field when opening the add new
     * dialog.
     */
    public void openNew() {
        this.selectedCustomerReview = new CustomerReviews();
    }

    /**
     * It is used for a global search.
     *
     * @param value
     * @param filter
     * @param locale
     * @return
     */
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isValueBlank(filterText)) {
            return true;
        }

        /*CustomerReviews customerReviews = (CustomerReviews) value;
        return customerReviews.getClientNumber().toLowerCase().contains(filterText)
                || customerReviews.getRating() .toLowerCase().contains(filterText)
                || customerReviews.getReviewDate() .toLowerCase().contains(filterText);*/
        return false;
    }

    public void addMessage() {
        String summary = this.approval_status ? "True" : "False";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
        PrimeFaces.current().ajax().update("messages");
    }

}
