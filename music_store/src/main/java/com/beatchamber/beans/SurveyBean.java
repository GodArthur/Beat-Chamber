package com.beatchamber.beans;

import com.beatchamber.entities.Choices;
import com.beatchamber.entities.Surveys;
import com.beatchamber.jpacontroller.ChoicesJpaController;
import com.beatchamber.jpacontroller.SurveysJpaController;
import java.io.Serializable;
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

    //JPA controllers used for survey functionality
    @Inject
    SurveysJpaController surveysJpaController;
    
    @Inject
    ChoicesJpaController choicesJpaController;
    
    //Stored values
    private Surveys survey;
    private List<Choices> surveyChoices;
    private String selectedChoice;

    /**
     * Default constructor
     */
    public SurveyBean() {
        this.survey = null;
        this.surveyChoices = null;
        this.selectedChoice = "";
    }
    
    /**
     * @return The choices of the survey
     */
    public List<Choices> getSurveyChoices() {
        return this.surveyChoices;
    }
    
    /**
     * @return The selected choice of the user
     */
    public String getSelectedChoice() {
        return selectedChoice;
    }

    /**
     * Set the selected choice of the user
     * @param selectedChoice 
     */
    public void setSelectedChoice(String selectedChoice) {
        this.selectedChoice = selectedChoice;
    }
    
    /**
     * @return The first approved survey. Nothing will display if none are approved
     */
    public Surveys getSurvey(){
        List<Surveys> allSurveys = this.surveysJpaController.findSurveysEntities();
        for (Surveys surveys : allSurveys) {
            if (surveys.getEnabled()) {
                this.survey = surveys;
                this.surveyChoices = surveysJpaController.getSurveyChoices(this.survey.getSurveyId());
                return surveys;
            }
        }
        return null;
    }
    
    /**
     * Voting process of the survey
     * 
     * @return Redirect to the index page if successful, nothing if not
     */
    public String voteSurvey() {
        //Inform the user if they didn't select a radio button
        if(this.selectedChoice == null || this.selectedChoice.isEmpty()) {
            displayMessage(FacesMessage.SEVERITY_WARN, "Warning", "You didn't select a choice!");
            return null;
        }
        
        //Find the specific choice in the table using its id
        Choices foundChoice;
        int choiceId = 0;
        int previousVotes = 0;
        for(Choices choice : this.surveyChoices) {
            if(choice.getChoiceName().equals(this.selectedChoice)) {
                foundChoice = choice;
                choiceId = foundChoice.getChoiceId();
                previousVotes = foundChoice.getVotes();
                break;
            }
        }
        
        //Add 1 to the choice's votes
        choicesJpaController.increaseChoicesNumber(choiceId);
        Choices changedChoice = choicesJpaController.findChoices(choiceId);
        
        //Check if the voting was successful
        if(changedChoice.getVotes() == previousVotes + 1) {
            return "index.xhtml?faces-redirect=true";
        }
        else {
            displayMessage(FacesMessage.SEVERITY_FATAL, "Voting failed", "An error has occured trying to add your vote");
            return null;
        }
    }
    
    /**
     * Using p:growl to display a message
     * 
     * @param severity
     * @param title
     * @param message 
     */
    public void displayMessage(Severity severity, String title, String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, title, message));
    }
}
