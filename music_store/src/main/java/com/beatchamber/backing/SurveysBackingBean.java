/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.backing;

import com.beatchamber.entities.Surveys;
import com.beatchamber.jpacontroller.SurveysJpaController;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 1733570 Yan
 */
@Named("theSurveys")
@RequestScoped
public class SurveysBackingBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(SurveysBackingBean.class);

    @Inject
    private SurveysJpaController surveysJpaController;

    private List<Surveys> surveys;

    private Surveys selectedSurvey;

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
    }

    public void saveSurvey() {
        if (this.selectedSurvey.getTitle() == null) {
//            this.selectedSurvey.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            this.surveys.add(this.selectedSurvey);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Survey Added"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Survey Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageSurveyDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-surveys");
    }

    public void deleteSurvey() {
        this.surveys.remove(this.selectedSurvey);
        this.selectedSurvey = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Survey Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-surveys");
    }
}
