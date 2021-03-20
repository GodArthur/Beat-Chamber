package com.beatchamber.beans;

import com.beatchamber.jpacontroller.ProvincesJpaController;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author kibra
 */
@Named("provinceManager")
@RequestScoped
public class DisplayProvinces {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    
    //CHANGED THIS TO AVOID ERROR
    //ProvincesJpaController dbProvince = new ProvincesJpaController(emf);
    
    ProvincesJpaController dbProvince = new ProvincesJpaController();
    
    public String getPst(int id){
        String pstValue = "";
        try{
            pstValue = dbProvince.findProvinces(id).getPst() + "";
        }
        catch(NullPointerException ex){
            pstValue = dbProvince.findProvinces(1).getPst() + "";
        }
        return pstValue;
    }
    
    public String getGst(int id){
        String gstValue = "";
        try{
            gstValue = dbProvince.findProvinces(id).getGst() + "";
        }
        catch(NullPointerException ex){
            gstValue = dbProvince.findProvinces(1).getGst() + "";
        }
        return gstValue;
    }
    
    public String getHst(int id){
        String hstValue = "";
        try{
            hstValue = dbProvince.findProvinces(id).getHst() + "";
        }
        catch(NullPointerException ex){
            hstValue = dbProvince.findProvinces(1).getHst() + "";
        }
        return hstValue;
    }
}
