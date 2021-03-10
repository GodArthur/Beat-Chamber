/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author 1733570
 */
@Named("phoneNumber")
@SessionScoped
public class PhoneNumber implements Serializable {

    private String areaCode;
    private String prefix;
    private String lineNumber;

    public PhoneNumber(String areaCode, String prefix, String lineNumber) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNumber = lineNumber;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return areaCode + "-" + prefix + "-" + lineNumber;
    }
}
