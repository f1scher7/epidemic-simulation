package com.treative.epidemicsimulation.service;

import com.treative.epidemicsimulation.entity.DailySimulationData;
import com.treative.epidemicsimulation.entity.Simulation;
import com.treative.epidemicsimulation.repository.DailySimulationDataRepository;
import com.treative.epidemicsimulation.service.exceptions.dailysimulationdata.CorruptedDailySimulationDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DailySimulationDataService {

    private final DailySimulationDataRepository dailySimulationDataRepository;


    @Autowired
    public DailySimulationDataService(DailySimulationDataRepository dailySimulationDataRepository) {
        this.dailySimulationDataRepository = dailySimulationDataRepository;
    }


    public void saveDailySimulationData(DailySimulationData dailySimulationData) {
        this.dailySimulationDataRepository.save(dailySimulationData);
    }

    @Transactional
    public void saveAllDailySimulationData(List<DailySimulationData> dailySimulationDataList) throws CorruptedDailySimulationDataException {
        if (areAllDailySimulationDataCorrect(dailySimulationDataList)) {
            this.dailySimulationDataRepository.deleteAllBySimulationId(dailySimulationDataList.get(0).getSimulation().getId());
            this.dailySimulationDataRepository.saveAll(dailySimulationDataList);
        } else {
            throw new CorruptedDailySimulationDataException();
        }
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
