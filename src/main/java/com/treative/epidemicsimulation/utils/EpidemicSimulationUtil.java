package com.treative.epidemicsimulation.utils;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class EpidemicSimulationUtil {

    public static double calculateUpdatedInfectionRate(double initialInfectionRate, double restrictionLevel, int infected, int totalPopulation) {
        if (restrictionLevel < 0.4) {
            initialInfectionRate *= 1.037;
        }

        double infectionProportion = (double) infected / totalPopulation;

        double infectionRateAdjustment = 1.0 + infectionProportion;

        return Math.max(Math.min((initialInfectionRate * infectionRateAdjustment * (1 - restrictionLevel)), 1.0), 0);
    }

    public static int calculateNewInfections(List<Integer> infectedDaysList, int healthy, double infectionRate, int infectionDaysDelay) {
        int infectionCount = (int) infectedDaysList.stream().filter(infectedDays -> infectedDays >= infectionDaysDelay).count();
        int newInfections = (int) (infectionCount * infectionRate);

        return Math.min(newInfections, healthy);
    }

    public static double calculateRestrictionLevel(double baseRestrictionLevel, int totalPopulation, int totalHealthy, int infected, int dead) {
        if ((double) dead / totalPopulation <= 0.99) {
            double infectionProportion = (double) infected / totalPopulation;
            double deathProportion = (double) dead / totalPopulation;
            double adjustedRestrictionLevel = baseRestrictionLevel + 0.01;

            if (infectionProportion > 0.2) {
                adjustedRestrictionLevel *= (infectionProportion + 1);
            }

            if (deathProportion > 0.05) {
                adjustedRestrictionLevel *= (deathProportion + 1);
            }

            return Math.min(1.0, Math.max(adjustedRestrictionLevel, 0.0));
        }

        return Math.max(baseRestrictionLevel - 0.1, 0.0);
    }

    public static double calculatePeopleMood(int totalHealthy, int infected, int dead, int totalPopulation, double restrictionLevel) {
        if (totalHealthy == 0.0) {
            return 0.0;
        }

        double infectionProportion = (double) infected / totalPopulation;
        double deathProportion = (double) dead / totalPopulation;

        double moodFromInfections = Math.max(0, 1.0 - infectionProportion * 2.0); // Im więcej zakażeń, tym gorszy nastrój
        double moodFromDeaths = Math.max(0, 1.0 - deathProportion * 5.0); // Im więcej zgonów, tym gorszy nastrój
        double moodFromRestrictionLevel = Math.max((1.0 - restrictionLevel), 0); // Im więcej restrykcji, tym gorszy nastrój

        double peopleMood = (moodFromInfections + moodFromDeaths + moodFromRestrictionLevel) / 3.0;

        return Math.min(1.0, Math.max(0.0, peopleMood));
    }

}
