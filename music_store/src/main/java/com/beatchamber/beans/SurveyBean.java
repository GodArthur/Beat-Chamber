package com.beatchamber.beans;

import com.beatchamber.jpacontroller.SurveysJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Susan Vuu - 1735488
 */
@Named
@SessionScoped
public class SurveyBean implements Serializable {

    @Inject
    SurveysJpaController surveysJpaController;
    
    private List<String> surveyChoices = new ArrayList<>();
    private String selectedChoice;

    public String getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(String selectedChoice) {
        this.selectedChoice = selectedChoice;
    }
}
