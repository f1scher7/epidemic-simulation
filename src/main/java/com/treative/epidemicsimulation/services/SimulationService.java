package com.treative.epidemicsimulation.services;

import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.repositories.SimulationRepository;
import com.treative.epidemicsimulation.services.exceptions.NullFieldException;
import com.treative.epidemicsimulation.services.exceptions.simulation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class SimulationService {

    private final SimulationRepository simulationRepository;
    private final DailySimulationDataService dailySimulationDataService;


    @Autowired
    public SimulationService(SimulationRepository simulationRepository, @Lazy DailySimulationDataService dailySimulationDataService) {
        this.simulationRepository = simulationRepository;
        this.dailySimulationDataService = dailySimulationDataService;
    }


    public Simulation saveSimulation(Simulation simulation) throws SimulationNameAlreadyExistsException, IncorrectInfectionRateException, IncorrectMortalityRateException, IncorrectInitialInfectedException, NullFieldException, InitialInfectedExceedsPopulationException {
        if (this.simulationRepository.findByName(simulation.getName()) != null) {
            throw new SimulationNameAlreadyExistsException();
        }

        validateSimulation(simulation);

        return this.simulationRepository.save(simulation);
    }

    public Simulation findSimulationById(Long id) {
        return this.simulationRepository.findById(id).orElse(null);
    }

    public List<Simulation> findAllSimulations() {
        List<Simulation> simulations = this.simulationRepository.findAll();
        Collections.reverse(simulations);

        return  simulations;
    }

    @Transactional
    public Simulation updateSimulation(Simulation updatedSimulation) throws SimulationNameAlreadyExistsException, IncorrectMortalityRateException, IncorrectInfectionRateException, IncorrectInitialInfectedException, NullFieldException, InitialInfectedExceedsPopulationException {
        Simulation existingSimulation = this.simulationRepository.findById(updatedSimulation.getId()).orElse(null);
        if (existingSimulation != null) {
            if (!existingSimulation.getName().equals(updatedSimulation.getName()) && simulationRepository.findByName(updatedSimulation.getName()) != null) {
                throw new SimulationNameAlreadyExistsException();
            }

            existingSimulation.setName(updatedSimulation.getName());
            existingSimulation.setPopulation(updatedSimulation.getPopulation());
            existingSimulation.setInitialInfected(updatedSimulation.getInitialInfected());
            existingSimulation.setInfectionRate(updatedSimulation.getInfectionRate());
            existingSimulation.setMortalityRate(updatedSimulation.getMortalityRate());
            existingSimulation.setInfectionDaysDelay(updatedSimulation.getInfectionDaysDelay());
            existingSimulation.setDaysToRecovery(updatedSimulation.getDaysToRecovery());
            existingSimulation.setDaysToDeath(updatedSimulation.getDaysToDeath());
            existingSimulation.setSimulationDays(updatedSimulation.getSimulationDays());

            validateSimulation(existingSimulation);

            this.dailySimulationDataService.deleteAllDailySimulationDataBySimulationId(existingSimulation.getId());
            this.dailySimulationDataService.saveAllDailySimulationDataBySimulationId(existingSimulation.getId());

            return this.simulationRepository.save(existingSimulation);
        }

        return null;
    }

    public void deleteSimulationById(Long id) {
        this.dailySimulationDataService.deleteAllDailySimulationDataBySimulationId(id);
        this.simulationRepository.deleteById(id);
    }


    private void validateSimulation(Simulation simulation) throws IncorrectInfectionRateException, IncorrectMortalityRateException, IncorrectInitialInfectedException, NullFieldException, InitialInfectedExceedsPopulationException {
        if (simulation.getName() == null || simulation.getName().isEmpty()) {
            throw new NullFieldException("Name");
        }

        if (simulation.getPopulation() <= 0) {
            throw new NullFieldException("Population");
        }

        if (simulation.getInitialInfected() <= 0) {
            throw new NullFieldException("Initial infected");
        }

        if (simulation.getInfectionDaysDelay() < 0) {
            throw new NullFieldException("Infectious days delay");
        }

        if (simulation.getDaysToDeath() <= 0 ) {
            throw new NullFieldException("Days to death");
        }

        if (simulation.getDaysToRecovery() <= 0 ) {
            throw new NullFieldException("Days to recovery");
        }

        if (simulation.getSimulationDays() <= 0) {
            throw new NullFieldException("Simulation days");
        }

        if (simulation.getPopulation() <= simulation.getInitialInfected()) {
            throw new InitialInfectedExceedsPopulationException();
        }

        if (simulation.getInfectionRate() < 0.1 || simulation.getInfectionRate() > 1) {
            throw new IncorrectInfectionRateException();
        }

        if (simulation.getMortalityRate() < 0.01 || simulation.getMortalityRate() > 1) {
            throw new IncorrectMortalityRateException();
        }

        if (simulation.getPopulation() < simulation.getInitialInfected()) {
            throw new IncorrectInitialInfectedException();
        }
    }

}
