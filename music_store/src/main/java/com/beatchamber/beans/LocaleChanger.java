package com.beatchamber.beans;

import java.util.Locale;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Class to change the language of the website
 * 
 * @author Susan Vuu - 1735488
 * Original code provided by Ken Fogel
 */
@Named
@RequestScoped
public class LocaleChanger {
    
    private boolean english;
    private boolean french;
    
    public LocaleChanger(){
        english = true;
        french = false;
    }

    public boolean isEnglish() {
        return english;
    }

    public void setEnglish(boolean english) {
        this.english = english;
    }

    public boolean isFrench() {
        return french;
    }

    public void setFrench(boolean french) {
        this.french = french;
    }
    
    public String changeLocale() {
        FacesContext context = FacesContext.getCurrentInstance();   

        Locale aLocale;
        if (english) {
            aLocale = Locale.CANADA_FRENCH;
            english = false;
            french = true;
        }
        else if (french) {
            aLocale = Locale.CANADA;
            french = false;
            english = true;
        }
        else {
            aLocale = Locale.getDefault();
        }
        
        context.getViewRoot().setLocale(aLocale);
        return null;
    }
}
