package com.treative.epidemicsimulation;

import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.repository.DailySimulationDataRepository;
import com.treative.epidemicsimulation.repository.SimulationRepository;
import com.treative.epidemicsimulation.service.DailySimulationDataService;
import com.treative.epidemicsimulation.service.EpidemicSimulationService;
import com.treative.epidemicsimulation.service.SimulationService;
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
        this.simulationService.deleteSimulationById((long) 89);

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

        this.simulationService.saveSimulation(covidSimulation);
        this.epidemicSimulationService.runEpidemicSimulation(covidSimulation);
    }

}
