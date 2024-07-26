package com.treative.epidemicsimulation.service;

import com.treative.epidemicsimulation.entity.DailySimulationData;
import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.service.exceptions.dailysimulationdata.CorruptedDailySimulationDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EpidemicSimulationService {

    private final DailySimulationDataService dailySimulationDataService;


    @Autowired
    public EpidemicSimulationService(DailySimulationDataService dailySimulationDataService) {
        this.dailySimulationDataService = dailySimulationDataService;
    }


    public List<DailySimulationData> runEpidemicSimulation(Simulation simulation) throws CorruptedDailySimulationDataException {
        List<DailySimulationData> dailySimulationDataList = new ArrayList<>();

        int totalPopulation = simulation.getPopulation();
        int infected = simulation.getInitialInfected();
        int healthy = totalPopulation - infected;
        int dead = 0;
        int recovered = 0;
        int daysToDeath = simulation.getDaysToDeath();
        int daysToRecovery = simulation.getDaysToRecovery();
        double infectionRate = simulation.getInfectionRate();
        double mortalityRate = simulation.getMortalityRate();

        List<Integer> infectedDaysList = new ArrayList<>(Collections.nCopies(infected, 0));

        for (int day = 1; day <= simulation.getSimulationDays(); day++) {
            int[] deadRecoveryCountArray = {0, 0};

            infectedDaysList = updateInfectedDays(infectedDaysList, daysToDeath, daysToRecovery, deadRecoveryCountArray, mortalityRate);

            dead += deadRecoveryCountArray[0];
            recovered += deadRecoveryCountArray[1];

            int newInfections = (int) (infectedDaysList.size() * infectionRate);
            newInfections = Math.min(newInfections, healthy);

            healthy -= newInfections;

            for (int i = 0; i < newInfections; i++) {
                infectedDaysList.add(0);
            }

            dailySimulationDataList.add(new DailySimulationData(simulation, infectedDaysList.size(), healthy, dead, recovered, day));
        }
        return this.dailySimulationDataService.saveAllDailySimulationData(dailySimulationDataList);
    }

    public List<Integer> updateInfectedDays(List<Integer> infectedDaysList, int daysToDeath, int daysToRecovery, int[] deadRecoveredCountArray, double mortalityRate) {
        return infectedDaysList.stream()
                .map(daysInfected -> {
                    if (daysInfected + 1 == daysToDeath || Math.random() < mortalityRate) {
                        deadRecoveredCountArray[0]++;
                        return null;
                    } else if (daysInfected + 1 == daysToRecovery) {
                        deadRecoveredCountArray[1]++;
                        return null;
                    } else {
                        return daysInfected + 1;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
