package com.treative.epidemicsimulation.services.exceptions.dailysimulationdata;

public class CorruptedDailySimulationDataException extends Exception {

    public CorruptedDailySimulationDataException() {
        super("Daily simulation data is corrupted");
    }

}
