/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans.manager;

import com.beatchamber.entities.Choices;
import com.beatchamber.entities.SurveyToChoice;
import com.beatchamber.entities.Surveys;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.jpacontroller.ChoicesJpaController;
import com.beatchamber.jpacontroller.SurveyToChoiceJpaController;
import com.beatchamber.jpacontroller.SurveysJpaController;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.faces.context.FacesContext;

/**
 *
 * @author 1733570 Yan Tang
 */
@Named("theSurveys")
@SessionScoped
public class SurveysBackingBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(SurveysBackingBean.class);

    @Inject
    private SurveysJpaController surveysJpaController;

    @Inject
    private SurveyToChoiceJpaController surveyToChoiceJpaController;

    @Inject
    private ChoicesJpaController choicesJpaController;

    private List<Surveys> surveys;

    private Surveys selectedSurvey;

    //private List<SurveyToChoice> surveyToChoices;
    private Choices choice1;
    private Choices choice2;
    private Choices choice3;
    private Choices choice4;
    private Choices choice5;

    @PostConstruct
    public void init() {
        this.surveys = surveysJpaController.findSurveysEntities();
    }

    public List<Surveys> getSurveys() {
        return surveys;
    }

    public Surveys getSelectedSurvey() {
        return selectedSurvey;
    }

    public void setSelectedSurvey(Surveys selectedSurvey) {
        this.selectedSurvey = selectedSurvey;
        this.choice1 = this.selectedSurvey.getSurveyToChoiceList().get(0).getChoiceId();
        this.choice2 = null;
        this.choice3 = null;
        this.choice4 = null;
        this.choice5 = null;
        if (this.selectedSurvey.getSurveyToChoiceList().size() > 1) {
            this.choice2 = this.selectedSurvey.getSurveyToChoiceList().get(1).getChoiceId();
            if (this.selectedSurvey.getSurveyToChoiceList().size() > 2) {
                this.choice3 = this.selectedSurvey.getSurveyToChoiceList().get(2).getChoiceId();
                if (this.selectedSurvey.getSurveyToChoiceList().size() > 3) {
                    this.choice4 = this.selectedSurvey.getSurveyToChoiceList().get(3).getChoiceId();
                    if (this.selectedSurvey.getSurveyToChoiceList().size() > 4) {
                        this.choice5 = this.selectedSurvey.getSurveyToChoiceList().get(4).getChoiceId();
                    }
                }
            }
        }
    }

    public void openNew() {
        this.selectedSurvey = new Surveys();
        this.initializeChoices();

//        List<SurveyToChoice> surveyToChoices = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            SurveyToChoice tempSurveyToChoice = new SurveyToChoice();
//            tempSurveyToChoice.setSurveyId(selectedSurvey);
//            Choices tempChoice = new Choices();
//            tempChoice.setChoiceName("");
//            tempChoice.setVotes(0);
//            tempSurveyToChoice.setChoiceId(tempChoice);
//            surveyToChoices.add(tempSurveyToChoice);
//        }
//        this.selectedSurvey.setSurveyToChoiceList(surveyToChoices);//newly added
    }

    public void saveSurvey() {
        try {
            if (this.selectedSurvey.getSurveyId() == null) {
                this.surveys.add(this.selectedSurvey);
                this.addChoicesDataToDB();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Survey Added"));
            } else {
                this.surveysJpaController.edit(selectedSurvey);
                this.updateChoicesDataToDB();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Survey Updated"));
            }
            //we need update the this.surveys busing the database data
            this.surveys = surveysJpaController.findSurveysEntities();
        } catch (RollbackFailureException ex) {
            java.util.logging.Logger.getLogger(ClientsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ClientsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrimeFaces.current().executeScript("PF('manageSurveyDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-surveys");
    }

    public void deleteSurvey() {
        try {
            this.choicesJpaController.destroy(this.choice1.getChoiceId());
            this.choicesJpaController.destroy(this.choice2.getChoiceId());
            this.choicesJpaController.destroy(this.choice3.getChoiceId());
            this.choicesJpaController.destroy(this.choice4.getChoiceId());
            this.choicesJpaController.destroy(this.choice5.getChoiceId());
            List<SurveyToChoice> surveyToChoice = this.selectedSurvey.getSurveyToChoiceList();
            surveyToChoice.forEach(item -> {
                try {
                    this.surveyToChoiceJpaController.destroy(item.getTablekey());
                } catch (IllegalOrphanException | NonexistentEntityException | NotSupportedException | SystemException | RollbackFailureException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
                    java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            this.surveysJpaController.destroy(this.selectedSurvey.getSurveyId());
        } catch (IllegalOrphanException | NonexistentEntityException | NotSupportedException | SystemException | RollbackFailureException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
            java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.surveys.remove(this.selectedSurvey);
        this.selectedSurvey = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Survey Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-surveys");
    }

    public Choices getChoice1() {
        return this.choice1;
        //return this.selectedSurvey.getSurveyToChoiceList().get(0).getChoiceId();
    }

    public Choices getChoice2() {
        return this.choice2;
        //return this.selectedSurvey.getSurveyToChoiceList().get(1).getChoiceId();
    }

    public Choices getChoice3() {
        return this.choice3;
        //return this.selectedSurvey.getSurveyToChoiceList().get(2).getChoiceId();
    }

    public Choices getChoice4() {
        return this.choice4;
        //return this.selectedSurvey.getSurveyToChoiceList().get(3).getChoiceId();
    }

    public Choices getChoice5() {
        return this.choice5;
        //return this.selectedSurvey.getSurveyToChoiceList().get(4).getChoiceId();
    }

    public void addChoicesDataToDB() {
        try {
            //this statement must be executed before adding it to the surveyToChoice otherwise the survey id is null
            this.surveysJpaController.create(selectedSurvey);
            if (choice1 != null && !choice1.getChoiceName().equals("")) {
                this.addChoiceAndSurveyToChoice(choice1);
            }
            if (choice2 != null && !choice2.getChoiceName().equals("")) {
                this.addChoiceAndSurveyToChoice(choice2);
            }
            if (choice3 != null && !choice3.getChoiceName().equals("")) {
                this.addChoiceAndSurveyToChoice(choice3);
            }
            if (choice4 != null && !choice4.getChoiceName().equals("")) {
                this.addChoiceAndSurveyToChoice(choice4);
            }
            if (choice5 != null && !choice5.getChoiceName().equals("")) {
                this.addChoiceAndSurveyToChoice(choice5);
            }
        } catch (RollbackFailureException ex) {
            java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

//        this.selectedSurvey.getSurveyToChoiceList().forEach(item -> {
//            try {
//                this.choicesJpaController.create(item.getChoiceId());
//                this.choices.add(item.getChoiceId());
//                this.surveyToChoiceJpaController.create(item);
//            } catch (RollbackFailureException ex) {
//                java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
    }

    public void updateChoicesDataToDB() {
        try {
            if (choice1 != null && !choice1.getChoiceName().equals("")) {
                this.choicesJpaController.edit(choice1);
            }
            if (choice2 != null && !choice2.getChoiceName().equals("")) {
                this.choicesJpaController.edit(choice2);
            }
            if (choice3 != null && !choice3.getChoiceName().equals("")) {
                this.choicesJpaController.edit(choice3);
            }
            if (choice4 != null && !choice4.getChoiceName().equals("")) {
                this.choicesJpaController.edit(choice4);
            }
            if (choice5 != null && !choice5.getChoiceName().equals("")) {
                this.choicesJpaController.edit(choice5);
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.surveyToChoices.forEach(item -> {
//        this.selectedSurvey.getSurveyToChoiceList().forEach(item-> {
//            try {
//                this.choicesJpaController.edit(item.getChoiceId());
//            } catch (Exception ex) {
//                java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
    }

    private void addChoiceAndSurveyToChoice(Choices choice) {
        try {
            this.choicesJpaController.create(choice);
            SurveyToChoice surveyToChoice = new SurveyToChoice();
            surveyToChoice.setChoiceId(choice);
            surveyToChoice.setSurveyId(this.selectedSurvey);
            this.surveyToChoiceJpaController.create(surveyToChoice);
        } catch (RollbackFailureException ex) {
            java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initializeChoices() {
        this.choice1 = new Choices();
        this.choice1.setChoiceName("");
        this.choice1.setVotes(0);
        this.choice2 = new Choices();
        this.choice2.setChoiceName("");
        this.choice2.setVotes(0);
        this.choice3 = new Choices();
        this.choice3.setChoiceName("");
        this.choice3.setVotes(0);
        this.choice4 = new Choices();
        this.choice4.setChoiceName("");
        this.choice4.setVotes(0);
        this.choice5 = new Choices();
        this.choice5.setChoiceName("");
        this.choice5.setVotes(0);
    }
}
