package com.beatchamber.beans;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Class to change the language of the website
 * 
 * @author Susan Vuu - 1735488
 */
@Named
@SessionScoped
public class LocaleChanger implements Serializable {
    
    private Locale locale;
    private boolean currentEnglish;
    
    //Using i18n example
    public LocaleChanger() {
        this.currentEnglish = true;
    }
    
    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }
    
    public Locale getLocale() {
        return locale;
    }

    public boolean isCurrentEnglish() {
        return currentEnglish;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String a, String b) {
        locale = new Locale(a, b);
        
        this.currentEnglish = a.equals("en");
        
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
    
    //Using JSFSample25ParameterEvents example
    /**
     * Default constructor
     */
//    public LocaleChanger(){
//        FacesContext context = FacesContext.getCurrentInstance();
//        this.locale = context.getViewRoot().getLocale();
//    }
//
//    /**
//     * @return The current locale
//     */
//    public Locale getLocale() {
//        return locale;
//    }
//
//    /**
//     * Set the current locale
//     * @param locale 
//     */
//    public void setLocale(Locale locale) {
//        this.locale = locale;
//    }
//    
//    /**
//     * Changes the language of the website depending on the current one
//     * @return The same page the user was currently on
//     */
//    public String changeLocale() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        Locale currentLocale = context.getViewRoot().getLocale();
//        
//        if (currentLocale == Locale.CANADA || currentLocale == Locale.ENGLISH) {
//            //Locale needs to be stored in a private attribute
//            this.locale = Locale.CANADA_FRENCH;
//            context.getViewRoot().setLocale(Locale.CANADA_FRENCH);
//        }
//        else if (currentLocale == Locale.CANADA_FRENCH || currentLocale == Locale.FRENCH) {
//            this.locale = Locale.CANADA;
//            context.getViewRoot().setLocale(Locale.CANADA);
//        }
//        else {
//            this.locale = Locale.getDefault();
//            context.getViewRoot().setLocale(Locale.getDefault());
//        }
//        
//        return null;
//    }
}
