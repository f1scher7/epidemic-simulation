package com.treative.epidemicsimulation.service;

import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.repository.SimulationRepository;
import com.treative.epidemicsimulation.service.exceptions.simulation.SimulationNameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SimulationService {

    private final SimulationRepository simulationRepository;


    @Autowired
    public SimulationService(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }


    public void saveSimulation(Simulation simulation) throws SimulationNameAlreadyExistsException {
        if (this.simulationRepository.findByName(simulation.getName()) != null) {
            throw new SimulationNameAlreadyExistsException();
        } else {
            this.simulationRepository.save(simulation);
        }
    }

    public Simulation findSimulationById(Long id) {
        return this.simulationRepository.findById(id).orElse(null);
    }

    public List<Simulation> findAllSimulations() {
        return this.simulationRepository.findAll();
    }

    @Transactional
    public Simulation updateSimulation(Simulation updatedSimulation) throws SimulationNameAlreadyExistsException {
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
            existingSimulation.setDaysToRecovery(updatedSimulation.getDaysToRecovery());
            existingSimulation.setDaysToDeath(updatedSimulation.getDaysToDeath());
            existingSimulation.setSimulationDays(updatedSimulation.getSimulationDays());

            return this.simulationRepository.save(existingSimulation);
        }

        return null;
    }

    public void deleteSimulationById(Long id) {
        this.simulationRepository.deleteById(id);
    }

}
