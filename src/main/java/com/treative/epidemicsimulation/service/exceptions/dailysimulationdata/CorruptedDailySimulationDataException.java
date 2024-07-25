package com.treative.epidemicsimulation.service.exceptions.dailysimulationdata;

public class CorruptedDailySimulationDataException extends Exception {

    public CorruptedDailySimulationDataException() {
        super("Daily simulation data is corrupted");
    }

}
