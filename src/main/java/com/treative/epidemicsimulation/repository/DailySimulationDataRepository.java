package com.treative.epidemicsimulation.repository;

import com.treative.epidemicsimulation.entity.DailySimulationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailySimulationDataRepository extends JpaRepository<DailySimulationData, Long> {



}
