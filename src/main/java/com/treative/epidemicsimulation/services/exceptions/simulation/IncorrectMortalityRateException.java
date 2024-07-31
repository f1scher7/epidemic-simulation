package com.treative.epidemicsimulation.services.exceptions.simulation;

public class IncorrectMortalityRateException extends Exception {

    public IncorrectMortalityRateException() {
        super("Mortality rate must be between 0.01 and 1.0");
    }

}
