package com.treative.epidemicsimulation.repositories;

import com.treative.epidemicsimulation.entity.DailySimulationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DailySimulationDataRepository extends JpaRepository<DailySimulationData, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM DailySimulationData simData WHERE simData.simulation.id = :simulationId")
    void deleteAllBySimulationId(@Param("simulationId") Long simulationId);

    @Transactional
    @Query("SELECT simData FROM DailySimulationData simData WHERE simData.simulation.id = :simulationId")
    List<DailySimulationData> findAllBySimulationId(@Param("simulationId") Long simulationId);

}
