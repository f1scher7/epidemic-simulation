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
    private double restrictionLevel;
    private double peopleMood;
    private int day;


    public DailySimulationData() {}

    public DailySimulationData(Simulation simulation, int infected, int healthy, int dead, int recovered, double restrictionLevel, double peopleMood, int day) {
        this.simulation = simulation;
        this.infected = infected;
        this.healthy = healthy;
        this.dead = dead;
        this.recovered = recovered;
        this.restrictionLevel = restrictionLevel;
        this.peopleMood = peopleMood;
        this.day = day;
    }


    public Long getId() {
        return this.id;
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public int getInfected() {
        return this.infected;
    }

    public int getHealthy() {
        return this.healthy;
    }

    public int getDead() {
        return this.dead;
    }

    public int getRecovered() {
        return this.recovered;
    }

    public double getRestrictionLevel() {
        return this.restrictionLevel;
    }

    public double getPeopleMood() {
        return this.peopleMood;
    }

    public int getDay() {
        return this.day;
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

    public void setRestrictionLevel(double restrictionLevel) {
        this.restrictionLevel = restrictionLevel;
    }

    public void setPeopleMood(double peopleMood) {
        this.peopleMood = peopleMood;
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
                ", restrictionLevel=" + restrictionLevel +
                ", peopleMood=" + peopleMood +
                ", day=" + day +
                '}';
    }

}
