package com.beatchamber.beans;

import com.beatchamber.entities.Choices;
import com.beatchamber.entities.Surveys;
import com.beatchamber.jpacontroller.SurveysJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Bean for survey functionality
 * 
 * @author Susan Vuu - 1735488
 */
@Named
@SessionScoped
public class SurveyBean implements Serializable {

    @Inject
    SurveysJpaController surveysJpaController;
    
    private Surveys survey;
    private String selectedChoice;
    
    private boolean surveyExists;


    public SurveyBean() {
        this.survey = null;
        this.selectedChoice = "";
        this.surveyExists = false;
    }
    
    public String getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(String selectedChoice) {
        this.selectedChoice = selectedChoice;
    }
    
    public boolean getSurveyExists() {
        return surveyExists;
    }

    public void setSurveyExists(boolean surveyExists) {
        this.surveyExists = surveyExists;
    }
    
    public Surveys getSurvey(){
        List<Surveys> allSurveys = this.surveysJpaController.findSurveysEntities();
        for (Surveys surveys : allSurveys) {
            if (surveys.getEnabled()) {
                this.surveyExists = true;
                this.survey = surveys;
                return surveys;
            }
        }
        return null;
    }
    
    public String voteSurvey() {
        if(this.selectedChoice == null || this.selectedChoice.isEmpty()) {
            displayMessage(FacesMessage.SEVERITY_WARN, "Warning", "You didn't select a choice!");
            return null;
        }
        
        //displayMessage(FacesMessage.SEVERITY_INFO, "Choice", selectedChoice);
        List<Choices> choicesList = this.surveysJpaController.getSurveyChoices(1);
        Choices foundChoice;
        for(Choices choice : choicesList) {
            if(choice.getChoiceName().equals(this.selectedChoice)) {
                foundChoice = choice;
            }
        }
        
        return null;
    }
    
    public void displayMessage(Severity severity, String title, String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, title, message));
    }
}
