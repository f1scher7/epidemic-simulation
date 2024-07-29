package com.treative.epidemicsimulation.services;

import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.repositories.SimulationRepository;
import com.treative.epidemicsimulation.services.exceptions.NullFieldException;
import com.treative.epidemicsimulation.services.exceptions.simulation.IncorrectInfectionRateException;
import com.treative.epidemicsimulation.services.exceptions.simulation.SimulationNameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SimulationServiceTest {

    @Mock
    private SimulationRepository simulationRepository;

    @Mock
    private DailySimulationDataService dailySimulationDataService;

    @InjectMocks
    private SimulationService simulationService;

    private Simulation validSimulation;

    @BeforeEach
    public void setUp() {
        validSimulation = new Simulation();
        validSimulation.setId(1L);
        validSimulation.setName("Test Simulation");
        validSimulation.setPopulation(1000);
        validSimulation.setInitialInfected(10);
        validSimulation.setInfectionRate(0.05);
        validSimulation.setMortalityRate(0.01);
        validSimulation.setInfectionDaysDelay(1);
        validSimulation.setDaysToRecovery(14);
        validSimulation.setDaysToDeath(21);
        validSimulation.setSimulationDays(100);
    }

    private Simulation createUpdatedSimulation() {
        Simulation updatedSimulation = new Simulation();
        updatedSimulation.setId(validSimulation.getId());
        updatedSimulation.setName("Updated Simulation");
        updatedSimulation.setPopulation(1000);
        updatedSimulation.setInitialInfected(20);
        updatedSimulation.setInfectionRate(0.04);
        updatedSimulation.setMortalityRate(0.02);
        updatedSimulation.setInfectionDaysDelay(1);
        updatedSimulation.setDaysToRecovery(14);
        updatedSimulation.setDaysToDeath(21);
        updatedSimulation.setSimulationDays(100);
        return updatedSimulation;
    }

    @Test
    public void testSaveSimulation_Success() throws Exception {
        when(simulationRepository.findByName(validSimulation.getName())).thenReturn(null);
        when(simulationRepository.save(validSimulation)).thenReturn(validSimulation);

        simulationService.saveSimulation(validSimulation);

        verify(simulationRepository, times(1)).save(validSimulation);
    }

    @Test
    public void testSaveSimulation_NameAlreadyExists() {
        when(simulationRepository.findByName(validSimulation.getName())).thenReturn(validSimulation);

        assertThrows(SimulationNameAlreadyExistsException.class, () -> simulationService.saveSimulation(validSimulation));
    }

    @Test
    public void testSaveSimulation_IncorrectInfectionRate() {
        validSimulation.setInfectionRate(1.5);
        assertThrows(IncorrectInfectionRateException.class, () -> simulationService.saveSimulation(validSimulation));
    }

    @Test
    public void testSaveSimulation_NullField() {
        validSimulation.setName(null);
        assertThrows(NullFieldException.class, () -> simulationService.saveSimulation(validSimulation));
    }

    @Test
    public void testFindSimulationById_Success() {
        when(simulationRepository.findById(1L)).thenReturn(Optional.of(validSimulation));

        Simulation foundSimulation = simulationService.findSimulationById(1L);

        assertNotNull(foundSimulation);
        assertEquals("Test Simulation", foundSimulation.getName());
    }

    @Test
    public void testFindSimulationById_NotFound() {
        when(simulationRepository.findById(1L)).thenReturn(Optional.empty());

        Simulation foundSimulation = simulationService.findSimulationById(1L);

        assertNull(foundSimulation);
    }

    @Test
    public void testUpdateSimulation_Success() throws Exception {
        Simulation updatedSimulation = createUpdatedSimulation();

        when(simulationRepository.findById(validSimulation.getId())).thenReturn(Optional.of(validSimulation));
        when(simulationRepository.findByName(updatedSimulation.getName())).thenReturn(null);
        when(simulationRepository.save(any(Simulation.class))).thenReturn(updatedSimulation);
        doNothing().when(dailySimulationDataService).deleteAllDailySimulationDataBySimulationId(validSimulation.getId());

        Simulation result = simulationService.updateSimulation(updatedSimulation);

        assertNotNull(result);
        assertEquals("Updated Simulation", result.getName());
        verify(simulationRepository, times(1)).save(any(Simulation.class));
    }

    @Test
    public void testDeleteSimulationById() {
        doNothing().when(dailySimulationDataService).deleteAllDailySimulationDataBySimulationId(validSimulation.getId());
        doNothing().when(simulationRepository).deleteById(validSimulation.getId());

        simulationService.deleteSimulationById(validSimulation.getId());

        verify(dailySimulationDataService, times(1)).deleteAllDailySimulationDataBySimulationId(validSimulation.getId());
        verify(simulationRepository, times(1)).deleteById(validSimulation.getId());
    }
}
