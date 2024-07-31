import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, map, Observable, throwError} from "rxjs";
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

    getSimulationById(id: number): Observable<SimulationModel> {
        return this.http.get<SimulationModel>(`${this.baseUrl}/get-by-id/${id}`).pipe(
            map(simulation => ({
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
            })),
            catchError(err => {
                console.error('Error fetching simulation:', err);
                return throwError(() => new Error(`Failed to fetch simulation details: ${err}`));
            })
        );
    }

    createSimulation(simulation: SimulationModel): Observable<SimulationModel> {
        return this.http.post<SimulationModel>(`${this.baseUrl}/create`, simulation).pipe(catchError(this.handleError))
    }

    deleteSimulation(simulation: SimulationModel) {
        return this.http.delete(`${this.baseUrl}/delete-by-id/${simulation.id}`);
    }

    private handleError(error: HttpErrorResponse): Observable<never> {
        let errorMessage = 'An unexpected error occurred';

        if (error.error) {
            errorMessage = error.error[Object.keys(error.error)[0]];
        } else if (error.message) {
            errorMessage = error.message;
        }

        return throwError(() => new Error(errorMessage));
    }

}