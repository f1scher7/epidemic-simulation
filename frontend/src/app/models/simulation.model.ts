export interface SimulationModel {
    id: number;
    name: string;
    population: number;
    initialInfected: number;
    infectionRate: number;
    mortalityRate: number;
    infectionDaysDelay: number;
    daysToRecovery: number;
    daysToDeath: number
    simulationDays: number;
}