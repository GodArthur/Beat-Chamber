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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 1733570
 */
@Named("theSurveys")
@RequestScoped
public class SurveysBackingBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(SurveysBackingBean.class);

    @Inject
    private  SurveysJpaController surveysJpaController;

    /**
     * Get list of songs in inventory
     *
     * @return
     */

    public List<Surveys> getSurveys() {

        return surveysJpaController.findSurveysEntities();
    }
}