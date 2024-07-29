package com.treative.epidemicsimulation.service;

import com.treative.epidemicsimulation.entity.DailySimulationData;
import com.treative.epidemicsimulation.entity.Simulation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.treative.epidemicsimulation.utils.EpidemicSimulationUtil.*;

@Service
public class EpidemicSimulationService {

    public List<DailySimulationData> runEpidemicSimulation(Simulation simulation) {
        List<DailySimulationData> dailySimulationDataList = new ArrayList<>();

        int totalPopulation = simulation.getPopulation();
        int initialInfected = simulation.getInitialInfected();
        int healthy = totalPopulation - initialInfected;
        int recovered = 0;
        int totalHealthy = healthy + recovered;
        int dead = 0;
        double restrictionLevel = 0.0;
        double peopleMood;
        double infectionRate = simulation.getInfectionRate();
        double mortalityRate = simulation.getMortalityRate();
        int infectionsDaysDelay = simulation.getInfectionDaysDelay();
        int daysToDeath = simulation.getDaysToDeath();
        int daysToRecovery = simulation.getDaysToRecovery();
        List<Integer> infectedDaysList = new ArrayList<>(Collections.nCopies(initialInfected, 0));

        for (int day = 1; day <= simulation.getSimulationDays(); day++) {
            int[] deadRecoveryCountArray = {0, 0};

            infectedDaysList = updateInfectedDays(infectedDaysList, daysToDeath, daysToRecovery, deadRecoveryCountArray, mortalityRate);

            int infected = infectedDaysList.size();
            dead += deadRecoveryCountArray[0];
            recovered += deadRecoveryCountArray[1];

            if (day % 10 == 0) {
                restrictionLevel = calculateRestrictionLevel(restrictionLevel, totalPopulation, totalHealthy, infected, dead);
            }

            if (infected == 0) {
                restrictionLevel = 0.0;
            }

            peopleMood = calculatePeopleMood(totalHealthy, infected, dead, totalPopulation, restrictionLevel);
            infectionRate = calculateUpdatedInfectionRate(infectionRate, restrictionLevel, infected, totalPopulation);

            if (infected == 0) {
                infectionRate = 0.0;
            }

            System.out.println("restrictiveLevel: " + restrictionLevel + "; peopleMood: " + peopleMood + "; infectionRate: " + infectionRate);


            int newInfections = calculateNewInfections(infectedDaysList, healthy, infectionRate, infectionsDaysDelay);

            healthy -= newInfections;
            totalHealthy = healthy + recovered;

            for (int i = 0; i < newInfections; i++) {
                infectedDaysList.add(0);
            }

            dailySimulationDataList.add(new DailySimulationData(simulation, infectedDaysList.size(), healthy, dead, recovered, restrictionLevel, peopleMood, day));
        }

        return dailySimulationDataList;
    }

    private List<Integer> updateInfectedDays(List<Integer> infectedDaysList, int daysToDeath, int daysToRecovery, int[] deadRecoveredCountArray, double mortalityRate) {
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
