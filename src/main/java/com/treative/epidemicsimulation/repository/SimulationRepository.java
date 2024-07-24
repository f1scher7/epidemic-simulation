package com.treative.epidemicsimulation.repository;

import com.treative.epidemicsimulation.entity.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {



}
