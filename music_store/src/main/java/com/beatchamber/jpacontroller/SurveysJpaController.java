/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.jpacontroller;

import com.beatchamber.entities.SurveyToChoice;
import com.beatchamber.entities.Surveys;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 1733570
 */
@Named
@SessionScoped
public class SurveysJpaController implements Serializable {

        private final static Logger LOG = LoggerFactory.getLogger(SurveysJpaController.class);
    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;
    
        //Default Constructor
    public SurveysJpaController() {
    }
    

    public void create(Surveys surveys) throws RollbackFailureException{
        try {
            utx.begin();
            em.persist(surveys);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            try {
                utx.rollback();
                LOG.error("Rollback");
            } catch (IllegalStateException | SecurityException | SystemException re) {
                LOG.error("Rollback2");

                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
        }
        
       
    }

    public void edit(Surveys surveys) throws NonexistentEntityException, Exception {
        try {
            utx.begin();
            surveys = em.merge(surveys);
            utx.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = surveys.getSurveyId();
                if (findSurveys(id) == null) {
                    throw new NonexistentEntityException("The survey with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
       
    }

    public void destroy(Integer id) throws Exception, NonexistentEntityException, RollbackFailureException {
        try {
            utx.begin();
            Surveys survey;
            try {
                survey = em.getReference(Surveys.class, id);
                survey.getSurveyId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The survey with id " + id + " no longer exists.", enfe);
            }
            em.remove(survey);
            utx.commit();
        } catch (NonexistentEntityException | IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
        
        
       
    }

    public List<Surveys> findSurveysEntities() {
        return findSurveysEntities(true, -1, -1);
    }

    public List<Surveys> findSurveysEntities(int maxResults, int firstResult) {
        return findSurveysEntities(false, maxResults, firstResult);
    }

    private List<Surveys> findSurveysEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Surveys.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
        
        
    }

    public Surveys findSurveys(Integer id) {
        return em.find(Surveys.class, id);
    }

    public int getSurveysCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Surveys> rt = cq.from(Surveys.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

        
        
    }
    
}
