package com.treative.epidemicsimulation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "simulations")
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private int population;     //P
    private int initialInfected;     //I
    private double infectionRate;    //R
    private double mortalityRate;     //M
    private int daysToRecovery;     //Ti
    private int daysToDeath;    //Tm
    private int simulationDays;     //Ts


    // wiem, że jest Lombok)
    public Simulation() {}

    public Simulation(Long id, String name, int population, int initialInfected, double infectionRate, double mortalityRate, int daysToRecovery, int daysToDeath, int simulationDays) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.initialInfected = initialInfected;
        this.infectionRate = infectionRate;
        this.mortalityRate = mortalityRate;
        this.daysToRecovery = daysToRecovery;
        this.daysToDeath = daysToDeath;
        this.simulationDays = simulationDays;
    }


    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPopulation() {
        return this.population;
    }

    public int getInitialInfected() {
        return this.initialInfected;
    }

    public double getInfectionRate() {
        return this.infectionRate;
    }

    public double getMortalityRate() {
        return this.mortalityRate;
    }

    public int getDaysToRecovery() {
        return this.daysToRecovery;
    }

    public int getDaysToDeath() {
        return this.daysToDeath;
    }

    public int getSimulationDays() {
        return this.simulationDays;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setInitialInfected(int initialInfected) {
        this.initialInfected = initialInfected;
    }

    public void setInfectionRate(double infectionRate) {
        this.infectionRate = infectionRate;
    }

    public void setMortalityRate(double mortalityRate) {
        this.mortalityRate = mortalityRate;
    }

    public void setDaysToRecovery(int daysToRecovery) {
        this.daysToRecovery = daysToRecovery;
    }

    public void setDaysToDeath(int daysToDeath) {
        this.daysToDeath = daysToDeath;
    }

    public void setSimulationDays(int simulationDays) {
        this.simulationDays = simulationDays;
    }


    @Override
    public String toString() {
        return "Simulation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", population=" + population +
                ", initialInfected=" + initialInfected +
                ", infectionRate=" + infectionRate +
                ", mortalityRate=" + mortalityRate +
                ", daysToRecovery=" + daysToRecovery +
                ", daysToDeath=" + daysToDeath +
                ", simulationDays=" + simulationDays +
                '}';
    }

}
