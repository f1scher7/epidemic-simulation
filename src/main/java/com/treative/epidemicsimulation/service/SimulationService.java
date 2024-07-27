package com.treative.epidemicsimulation.service;

import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.repository.DailySimulationDataRepository;
import com.treative.epidemicsimulation.repository.SimulationRepository;
import com.treative.epidemicsimulation.service.exceptions.NullFieldException;
import com.treative.epidemicsimulation.service.exceptions.simulation.IncorrectInfectionRateException;
import com.treative.epidemicsimulation.service.exceptions.simulation.IncorrectInitialInfectedException;
import com.treative.epidemicsimulation.service.exceptions.simulation.IncorrectMortalityRateException;
import com.treative.epidemicsimulation.service.exceptions.simulation.SimulationNameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SimulationService {

    private final SimulationRepository simulationRepository;
    private final DailySimulationDataService dailySimulationDataService;


    @Autowired
    public SimulationService(SimulationRepository simulationRepository, DailySimulationDataService dailySimulationDataService) {
        this.simulationRepository = simulationRepository;
        this.dailySimulationDataService = dailySimulationDataService;
    }


    public void saveSimulation(Simulation simulation) throws SimulationNameAlreadyExistsException, IncorrectInfectionRateException, IncorrectMortalityRateException, IncorrectInitialInfectedException, NullFieldException {
        if (this.simulationRepository.findByName(simulation.getName()) != null) {
            throw new SimulationNameAlreadyExistsException();
        }

        validateSimulation(simulation);

        this.simulationRepository.save(simulation);
    }

    public Simulation findSimulationById(Long id) {
        return this.simulationRepository.findById(id).orElse(null);
    }

    public List<Simulation> findAllSimulations() {
        return this.simulationRepository.findAll();
    }

    @Transactional
    public Simulation updateSimulation(Simulation updatedSimulation) throws SimulationNameAlreadyExistsException, IncorrectMortalityRateException, IncorrectInfectionRateException, IncorrectInitialInfectedException, NullFieldException {
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

            return this.simulationRepository.save(existingSimulation);
        }

        return null;
    }

    public void deleteSimulationById(Long id) {
        this.dailySimulationDataService.deleteAllDailySimulationDataBySimulationId(id);
        this.simulationRepository.deleteById(id);
    }


    private void validateSimulation(Simulation simulation) throws IncorrectInfectionRateException, IncorrectMortalityRateException, IncorrectInitialInfectedException, NullFieldException {
        if (simulation.getName() == null) {
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

        if (simulation.getInfectionRate() < 0 || simulation.getInfectionRate() > 1) {
            throw new IncorrectInfectionRateException();
        }

        if (simulation.getMortalityRate() < 0 || simulation.getMortalityRate() > 1) {
            throw new IncorrectMortalityRateException();
        }

        if (simulation.getPopulation() < simulation.getInitialInfected()) {
            throw new IncorrectInitialInfectedException();
        }
    }

}
