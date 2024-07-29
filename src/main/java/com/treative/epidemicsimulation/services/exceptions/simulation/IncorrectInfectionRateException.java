package com.treative.epidemicsimulation.service.exceptions.simulation;

public class IncorrectInfectionRateException extends Exception {

    public IncorrectInfectionRateException() {
        super("Infection rate must be between 0 and 1");
    }

}
