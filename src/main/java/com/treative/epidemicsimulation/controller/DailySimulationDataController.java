package com.treative.epidemicsimulation.controller;

import com.treative.epidemicsimulation.entity.DailySimulationData;
import com.treative.epidemicsimulation.service.DailySimulationDataService;
import com.treative.epidemicsimulation.service.exceptions.dailysimulationdata.CorruptedDailySimulationDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daily-simulation-data")
public class DailySimulationDataController {

    private final DailySimulationDataService dailySimulationDataService;


    @Autowired
    public DailySimulationDataController(DailySimulationDataService dailySimulationDataService) {
        this.dailySimulationDataService = dailySimulationDataService;
    }


    @PostMapping("/create-all/{simulationId}")
    public ResponseEntity<?> createAllDailySimulationDataBySimulationId(@PathVariable Long simulationId) throws CorruptedDailySimulationDataException {
        List<DailySimulationData> dailySimulationDataList = this.dailySimulationDataService.saveAllDailySimulationDataBySimulationId(simulationId);
        return ResponseEntity.ok().body(dailySimulationDataList);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findDailySimulationDataById(@PathVariable Long id) {
        DailySimulationData dailySimulationData = this.dailySimulationDataService.findDailySimulationDataById(id);
        return ResponseEntity.ok().body(dailySimulationData);
    }

    @GetMapping("find-all/{simulationId}")
    public ResponseEntity<?> findAllDailySimulationDateBySimulationId(@PathVariable Long simulationId) {
        List<DailySimulationData> dailySimulationDataList = this.dailySimulationDataService.findAllDailySimulationDataBySimulationId(simulationId);
        return ResponseEntity.ok().body(dailySimulationDataList);
    }

}
