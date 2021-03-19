/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.backing;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
 * @author 1733570 Yan
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

    private List<SurveyToChoice> surveyToChoices;
    private List<Choices> choices;

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
    }

    public void openNew() {
        this.selectedSurvey = new Surveys();
        this.choices = new ArrayList<>();
    }

    public void saveSurvey() {
        try {
            if (this.selectedSurvey.getSurveyId() == null) {
                surveysJpaController.create(this.selectedSurvey);
                this.surveys.add(this.selectedSurvey);
                this.addChoices();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Survey Added"));
            } else {
                surveysJpaController.edit(this.selectedSurvey);
                this.updateChoices();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Survey Updated"));
            }

        } catch (RollbackFailureException | IllegalOrphanException | NonexistentEntityException ex) {
            java.util.logging.Logger.getLogger(ClientsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ClientsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrimeFaces.current().executeScript("PF('manageSurveyDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-surveys");
    }

    public void deleteSurvey() {
        try {
            surveysJpaController.destroy(this.selectedSurvey.getSurveyId());
        } catch (IllegalOrphanException | NonexistentEntityException | NotSupportedException | SystemException | RollbackFailureException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
            java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.surveys.remove(this.selectedSurvey);
        this.selectedSurvey = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Survey Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-surveys");
    }

    public List<Choices> getChoices() {
        this.choices = new ArrayList<>();
        this.surveyToChoices = this.selectedSurvey.getSurveyToChoiceList();
        this.surveyToChoices.forEach(item
                -> this.choices.add(item.getChoiceId())
        );
        return this.choices;
    }

    public void addChoices() {

        this.choices.forEach(item -> {
            try {
                this.choicesJpaController.create(item);
                this.choices.add(item);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void updateChoices() {

        this.choices.forEach(item -> {
            try {
                this.choicesJpaController.edit(item);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
