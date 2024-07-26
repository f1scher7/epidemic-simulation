package com.treative.epidemicsimulation.service.exceptions.simulation;

public class IncorrectInitialInfectedException extends Exception {

    public IncorrectInitialInfectedException() {
        super("Initial infected count cannot be greater than the total population");
    }

}
