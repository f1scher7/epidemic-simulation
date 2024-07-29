package com.treative.epidemicsimulation.service;

import com.treative.epidemicsimulation.entity.DailySimulationData;
import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.repository.DailySimulationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailySimulationDataService {

    private final SimulationService simulationService;
    private final EpidemicSimulationService epidemicSimulationService;
    private final DailySimulationDataRepository dailySimulationDataRepository;


    @Autowired
    public DailySimulationDataService(SimulationService simulationService, EpidemicSimulationService epidemicSimulationService, DailySimulationDataRepository dailySimulationDataRepository) {
        this.simulationService = simulationService;
        this.epidemicSimulationService = epidemicSimulationService;
        this.dailySimulationDataRepository = dailySimulationDataRepository;

    }


    public List<DailySimulationData> saveAllDailySimulationDataBySimulationId(Long simulationId) {
        Simulation simulation = this.simulationService.findSimulationById(simulationId);

        if (simulation != null) {
            if (findAllDailySimulationDataBySimulationId(simulationId) != null) {
                deleteAllDailySimulationDataBySimulationId(simulationId);
            }
            return this.epidemicSimulationService.runEpidemicSimulation(simulation);
        }

        return null;
    }

    public DailySimulationData findDailySimulationDataById(Long id) {
        return this.dailySimulationDataRepository.findById(id).orElse(null);
    }

    public List<DailySimulationData> findAllDailySimulationDataBySimulationId(Long simulationId) {
        return this.dailySimulationDataRepository.findAllBySimulationId(simulationId);
    }

    public void deleteAllDailySimulationDataBySimulationId(Long simulationId) {
        this.dailySimulationDataRepository.deleteAllBySimulationId(simulationId);
    }


    private boolean areAllDailySimulationDataCorrect(List<DailySimulationData> dailySimulationDataList) {
        if (dailySimulationDataList.isEmpty()) {
            return false;
        }

        Simulation firstSimulation = dailySimulationDataList.get(0).getSimulation();

        if (dailySimulationDataList.size() != firstSimulation.getSimulationDays()) {
            return false;
        }

        return dailySimulationDataList.stream().allMatch(data -> data.getSimulation().getId().equals(firstSimulation.getId()));
    }

}
