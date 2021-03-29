package com.beatchamber.beans;

import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.RequestScoped;
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
    
    public LocaleChanger(){
        
    }
    
    /**
     * Changes the language of the website depending on the current one
     * @return The same page the user was currently on
     */
    public String changeLocale() {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale currentLocale = context.getViewRoot().getLocale();
        
        if (currentLocale == Locale.CANADA || currentLocale == Locale.ENGLISH) {
            context.getViewRoot().setLocale(Locale.CANADA_FRENCH);
        }
        else if (currentLocale == Locale.CANADA_FRENCH || currentLocale == Locale.FRENCH) {
            context.getViewRoot().setLocale(Locale.CANADA);
        }
        else {
            context.getViewRoot().setLocale(Locale.getDefault());
        }
        
        return null;
    }
}
