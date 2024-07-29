package com.treative.epidemicsimulation.controllers;

import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.services.SimulationService;
import com.treative.epidemicsimulation.services.exceptions.NullFieldException;
import com.treative.epidemicsimulation.services.exceptions.simulation.IncorrectInfectionRateException;
import com.treative.epidemicsimulation.services.exceptions.simulation.IncorrectInitialInfectedException;
import com.treative.epidemicsimulation.services.exceptions.simulation.IncorrectMortalityRateException;
import com.treative.epidemicsimulation.services.exceptions.simulation.SimulationNameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

    private final SimulationService simulationService;


    @Autowired
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createSimulation(@RequestBody Simulation newSimulation) throws SimulationNameAlreadyExistsException, IncorrectInitialInfectedException, IncorrectMortalityRateException, IncorrectInfectionRateException, NullFieldException {
        Simulation simulation = this.simulationService.saveSimulation(newSimulation);
        return ResponseEntity.ok().body(simulation);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Simulation>> getAllSimulations() {
        List<Simulation> simulations = this.simulationService.findAllSimulations();
        return ResponseEntity.ok().body(simulations);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Simulation> getSimulationById(@PathVariable Long id) {
        Simulation simulation = this.simulationService.findSimulationById(id);
        return ResponseEntity.ok().body(simulation);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSimulation(@RequestBody Simulation updatedSimulation) throws SimulationNameAlreadyExistsException, IncorrectInitialInfectedException, IncorrectMortalityRateException, IncorrectInfectionRateException, NullFieldException {
        Simulation simulation = this.simulationService.updateSimulation(updatedSimulation);
        return ResponseEntity.ok().body(simulation);
    }

    @DeleteMapping("/delete-by-id")
    public ResponseEntity<?> deleteSimulationById(Long id) {
        this.simulationService.deleteSimulationById(id);
        return ResponseEntity.ok().build();
    }

}
