package com.treative.epidemicsimulation.services.exceptions;

public class NullFieldException extends Exception {

    public NullFieldException(String field) {
        super(field + " cannot be null");
    }

}
