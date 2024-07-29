package com.treative.epidemicsimulation;

import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.repositories.DailySimulationDataRepository;
import com.treative.epidemicsimulation.repositories.SimulationRepository;
import com.treative.epidemicsimulation.services.DailySimulationDataService;
import com.treative.epidemicsimulation.services.EpidemicSimulationService;
import com.treative.epidemicsimulation.services.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EpidemicSimulationApplication implements CommandLineRunner {

    private final SimulationService simulationService;
    private final DailySimulationDataService dailySimulationDataService;
    private final EpidemicSimulationService epidemicSimulationService;
    private final SimulationRepository simulationRepository;
    private  final DailySimulationDataRepository dailySimulationDataRepository;


    @Autowired
    public EpidemicSimulationApplication(SimulationService simulationService, DailySimulationDataService dailySimulationDataService, EpidemicSimulationService epidemicSimulationService, SimulationRepository simulationRepository, DailySimulationDataRepository dailySimulationDataRepository) {
        this.simulationService = simulationService;
        this.dailySimulationDataService = dailySimulationDataService;
        this.epidemicSimulationService = epidemicSimulationService;
        this.simulationRepository = simulationRepository;
        this.dailySimulationDataRepository = dailySimulationDataRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(EpidemicSimulationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.simulationService.deleteSimulationById((long) 94);

        Simulation covidSimulation = new Simulation();
        covidSimulation.setName("Symulacja Epidemii COVID-19");
        covidSimulation.setPopulation(10000000);
        covidSimulation.setInitialInfected(1000);
        covidSimulation.setInfectionRate(0.1);
        covidSimulation.setMortalityRate(0.01);
        covidSimulation.setInfectionDaysDelay(4);
        covidSimulation.setDaysToRecovery(14);
        covidSimulation.setDaysToDeath(21);
        covidSimulation.setSimulationDays(365);
        simulationService.saveSimulation(covidSimulation);

        Simulation fluSimulation = new Simulation();
        fluSimulation.setName("Symulacja Epidemii Grypy");
        fluSimulation.setPopulation(5000000);
        fluSimulation.setInitialInfected(500);
        fluSimulation.setInfectionRate(0.05);
        fluSimulation.setMortalityRate(0.002);
        fluSimulation.setInfectionDaysDelay(2);
        fluSimulation.setDaysToRecovery(7);
        fluSimulation.setDaysToDeath(14);
        fluSimulation.setSimulationDays(180);
        simulationService.saveSimulation(fluSimulation);

        Simulation ebolaSimulation = new Simulation();
        ebolaSimulation.setName("Symulacja Epidemii Eboli");
        ebolaSimulation.setPopulation(1000000);
        ebolaSimulation.setInitialInfected(50);
        ebolaSimulation.setInfectionRate(0.2);
        ebolaSimulation.setMortalityRate(0.5);
        ebolaSimulation.setInfectionDaysDelay(5);
        ebolaSimulation.setDaysToRecovery(21);
        ebolaSimulation.setDaysToDeath(30);
        ebolaSimulation.setSimulationDays(100);
        simulationService.saveSimulation(ebolaSimulation);

        Simulation commonColdSimulation = new Simulation();
        commonColdSimulation.setName("Symulacja Zwykłego Przeziębienia");
        commonColdSimulation.setPopulation(1000000);
        commonColdSimulation.setInitialInfected(1000);
        commonColdSimulation.setInfectionRate(0.01);
        commonColdSimulation.setMortalityRate(0.0001);
        commonColdSimulation.setInfectionDaysDelay(1);
        commonColdSimulation.setDaysToRecovery(5);
        commonColdSimulation.setDaysToDeath(7);
        commonColdSimulation.setSimulationDays(365);
        simulationService.saveSimulation(commonColdSimulation);
    }

}
