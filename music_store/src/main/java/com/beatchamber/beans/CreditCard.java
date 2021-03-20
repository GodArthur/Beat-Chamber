package com.beatchamber.beans;

import java.io.Serializable;

/**
 * This class is used by the JSF credit card converter
 *
 * @author Ken Fogel
 */
public class CreditCard implements Serializable {

    private final String number;

    public CreditCard(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }
}
