package com.treative.epidemicsimulation.service.exceptions.simulation;

public class IncorrectMortalityRateException extends Exception {

    public IncorrectMortalityRateException() {
        super("Mortality rate must be between 0 and 1");
    }

}
