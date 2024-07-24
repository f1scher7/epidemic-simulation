package com.treative.epidemicsimulation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "daily_simulation_data")
public class DailySimulationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "simulation_id")
    private Simulation simulation;
    private int infected;    //Pi
    private int healthy;    //Pv
    private int dead;    //Pm
    private int recovered;    //Pr
    private int day;


    public DailySimulationData() {}

    public DailySimulationData(Long id, Simulation simulation, int infected, int healthy, int dead, int recovered, int day) {
        this.id = id;
        this.simulation = simulation;
        this.infected = infected;
        this.healthy = healthy;
        this.dead = dead;
        this.recovered = recovered;
        this.day = day;
    }


    public Long getId() {
        return id;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public int getInfected() {
        return infected;
    }

    public int getHealthy() {
        return healthy;
    }

    public int getDead() {
        return dead;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getDay() {
        return day;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void setInfected(int infected) {
        this.infected = infected;
    }

    public void setHealthy(int healthy) {
        this.healthy = healthy;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public void setDay(int day) {
        this.day = day;
    }


    @Override
    public String toString() {
        return "DailySimulationData{" +
                "id=" + id +
                ", simulation=" + simulation +
                ", infected=" + infected +
                ", healthy=" + healthy +
                ", dead=" + dead +
                ", recovered=" + recovered +
                ", day=" + day +
                '}';
    }

}
