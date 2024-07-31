package com.treative.epidemicsimulation.services.exceptions.simulation;

public class InitialInfectedExceedsPopulationException extends Exception {

    public InitialInfectedExceedsPopulationException() {
        super("Initial infected count cannot be greater than or equal to the population.");
    }

}
