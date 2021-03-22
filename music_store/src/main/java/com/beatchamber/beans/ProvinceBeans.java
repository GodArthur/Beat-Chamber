/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author kibra
 */
@Named("provincechoice")
@RequestScoped
public class ProvinceBeans implements Serializable {

    private int name = 1;

    
    public void setName(int Name) {
        this.name = Name;
    }

    public int getName() {
        return this.name;
    }
    
    

    @Override
    public String toString() {
        return this.name+"";
    }
}