package com.treative.epidemicsimulation.service.exceptions.simulation;

public class SimulationNameAlreadyExistsException extends Exception {

    public SimulationNameAlreadyExistsException() {
        super("Simulation with this name already exists");
    }

}
