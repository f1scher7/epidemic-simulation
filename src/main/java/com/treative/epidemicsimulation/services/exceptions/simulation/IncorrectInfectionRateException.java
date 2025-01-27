package com.treative.epidemicsimulation.services.exceptions.simulation;

public class IncorrectInfectionRateException extends Exception {

    public IncorrectInfectionRateException() {
        super("Infection rate must be between 0.1 and 1.0");
    }

}
