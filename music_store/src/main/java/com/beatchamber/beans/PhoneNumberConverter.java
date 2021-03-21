/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 1733570
 */
@FacesConverter("com.beatchamber.PhoneNumberConverter")
public class PhoneNumberConverter implements Converter, Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(PhoneNumberConverter.class);

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String stringValue) throws ConverterException {

        if (stringValue == null || stringValue.trim().length() == 0) {
            return null;
        }

        String rawNumber = stringValue.replaceAll("[^0-9]", "");
        PhoneNumber phoneNumber = null;
        if (rawNumber.length() != 10) {
            throw new ConverterException(new FacesMessage(
                    "Phone number must be 10 numeric characters"));
        } else {
            phoneNumber = new PhoneNumber(rawNumber.substring(0, 3),
                    rawNumber.substring(3, 6), rawNumber.substring(6));
        }

        return phoneNumber;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        if (!(value instanceof PhoneNumber)) {
            throw new ConverterException();
        }

        PhoneNumber phoneNumber = (PhoneNumber) value;

        String stringValue = "(" + phoneNumber.getAreaCode() + ") "
                + phoneNumber.getPrefix() + "-" + phoneNumber.getLineNumber();

        return stringValue;
    }
}
