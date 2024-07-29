import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {SimulationModel} from "../models/simulation.model";

@Injectable({
    providedIn: 'root'
})
export class SimulationService {

    baseUrl = 'http://localhost:8080/api/simulation'

    constructor(private http: HttpClient) {}

    getAllSimulations(): Observable<SimulationModel[]> {
        return this.http.get<SimulationModel[]>(`${this.baseUrl}/get-all`).pipe(
            map(simulations =>  {
                return simulations.map(simulation => ({
                    id: simulation.id,
                    name: simulation.name,
                    population: simulation.population,
                    initialInfected: simulation.initialInfected,
                    infectionRate: simulation.infectionRate,
                    mortalityRate: simulation.mortalityRate,
                    infectionDaysDelay: simulation.infectionDaysDelay,
                    daysToRecovery: simulation.daysToRecovery,
                    daysToDeath: simulation.daysToDeath,
                    simulationDays: simulation.simulationDays
                }));
            })
        );
    }

}