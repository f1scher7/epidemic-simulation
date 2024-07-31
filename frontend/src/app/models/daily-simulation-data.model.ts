export interface DailySimulationDataModel {
    id: number;
    infected: number;
    healthy: number;
    dead: number;
    recovered: number;
    dynamicalInfectionRate: number;
    restrictionLevel: number;
    peopleMood: number;
    day: number;
}