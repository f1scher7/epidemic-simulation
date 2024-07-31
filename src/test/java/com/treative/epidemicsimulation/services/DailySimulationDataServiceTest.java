package com.treative.epidemicsimulation.services;

import com.treative.epidemicsimulation.entity.DailySimulationData;
import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.repositories.DailySimulationDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class DailySimulationDataServiceTest {

    @Mock
    private SimulationService simulationService;

    @Mock
    private EpidemicSimulationService epidemicSimulationService;

    @Mock
    private DailySimulationDataRepository dailySimulationDataRepository;

    @InjectMocks
    private DailySimulationDataService dailySimulationDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAllDailySimulationDataBySimulationId_simulationExists() {
        Simulation simulation = new Simulation();
        simulation.setId(1L);
        simulation.setSimulationDays(10);
        List<DailySimulationData> dailySimulationDataList = new ArrayList<>();
        when(this.simulationService.findSimulationById(1L)).thenReturn(simulation);
        when(this.dailySimulationDataRepository.findAllBySimulationId(1L)).thenReturn(dailySimulationDataList);
        when(this.epidemicSimulationService.runEpidemicSimulation(simulation)).thenReturn(dailySimulationDataList);
        when(this.dailySimulationDataRepository.saveAll(dailySimulationDataList)).thenReturn(dailySimulationDataList);

        List<DailySimulationData> result = this.dailySimulationDataService.saveAllDailySimulationDataBySimulationId(1L);

        assertNotNull(result);
        verify(this.dailySimulationDataRepository).deleteAllBySimulationId(1L);
        verify(this.dailySimulationDataRepository).saveAll(dailySimulationDataList);
    }

    @Test
    void saveAllDailySimulationDataBySimulationId_simulationDoesNotExist() {
        when(this.simulationService.findSimulationById(anyLong())).thenReturn(null);

        List<DailySimulationData> result = this.dailySimulationDataService.saveAllDailySimulationDataBySimulationId(1L);

        assertNull(result);
        verify(this.dailySimulationDataRepository, never()).saveAll(any());
    }

    @Test
    void findDailySimulationDataById() {
        DailySimulationData data = new DailySimulationData();
        when(this.dailySimulationDataRepository.findById(1L)).thenReturn(Optional.of(data));

        DailySimulationData result = this.dailySimulationDataService.findDailySimulationDataById(1L);

        assertNotNull(result);
        assertEquals(data, result);
    }

    @Test
    void findAllDailySimulationDataBySimulationId() {
        List<DailySimulationData> dailySimulationDataList = new ArrayList<>();
        when(this.dailySimulationDataRepository.findAllBySimulationId(1L)).thenReturn(dailySimulationDataList);

        List<DailySimulationData> result = this.dailySimulationDataService.findAllDailySimulationDataBySimulationId(1L);

        assertNotNull(result);
        assertEquals(dailySimulationDataList, result);
    }

    @Test
    void deleteAllDailySimulationDataBySimulationId() {
        this.dailySimulationDataService.deleteAllDailySimulationDataBySimulationId(1L);

        verify(this.dailySimulationDataRepository).deleteAllBySimulationId(1L);
    }

    @Test
    void areAllDailySimulationDataCorrect() {
        Simulation simulation = new Simulation();
        simulation.setSimulationDays(2);
        simulation.setId(1L);

        DailySimulationData data1 = new DailySimulationData();
        data1.setSimulation(simulation);
        DailySimulationData data2 = new DailySimulationData();
        data2.setSimulation(simulation);

        List<DailySimulationData> dailySimulationDataList = List.of(data1, data2);

        boolean result = this.dailySimulationDataService.areAllDailySimulationDataCorrect(dailySimulationDataList);

        assertTrue(result);
    }

    @Test
    void areAllDailySimulationDataCorrect_incorrectData() {
        Simulation simulation1 = new Simulation();
        simulation1.setSimulationDays(2);
        simulation1.setId(1L);

        Simulation simulation2 = new Simulation();
        simulation2.setSimulationDays(2);
        simulation2.setId(2L);

        DailySimulationData data1 = new DailySimulationData();
        data1.setSimulation(simulation1);
        DailySimulationData data2 = new DailySimulationData();
        data2.setSimulation(simulation2);

        List<DailySimulationData> dailySimulationDataList = List.of(data1, data2);

        boolean result = this.dailySimulationDataService.areAllDailySimulationDataCorrect(dailySimulationDataList);

        assertFalse(result);
    }

    @Test
    void areAllDailySimulationDataCorrect_emptyList() {
        List<DailySimulationData> dailySimulationDataList = new ArrayList<>();

        boolean result = this.dailySimulationDataService.areAllDailySimulationDataCorrect(dailySimulationDataList);

        assertFalse(result);
    }
}
