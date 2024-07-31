import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {DailySimulationDataModel} from "../models/daily-simulation-data.model";

@Injectable({
    providedIn: 'root'
})
export class DailySimulationDataService {

    baseUrl = 'http://localhost:8080/api/daily-simulation-data'

    constructor(private http: HttpClient) {}

    createAllDailySimulationData(simulationId: number): Observable<DailySimulationDataModel[]> {
        return this.http.post<DailySimulationDataModel[]>(`${this.baseUrl}/create-all/${simulationId}`, {}).pipe(
            map(this.mapDailySimulationDataList)
        );
    }

    getAllDailySimulationData(simulationId: number): Observable<DailySimulationDataModel[]> {
        return this.http.get<DailySimulationDataModel[]>(`${this.baseUrl}/find-all/${simulationId}`).pipe(
            map(this.mapDailySimulationDataList)
        );
    }

    private mapDailySimulationDataList(dailySimulationDataList: DailySimulationDataModel[]): DailySimulationDataModel[] {
        return dailySimulationDataList.map(dailySimulationData => ({
            id: dailySimulationData.id,
            infected: dailySimulationData.infected,
            healthy: dailySimulationData.healthy,
            dead: dailySimulationData.dead,
            recovered: dailySimulationData.recovered,
            restrictionLevel: dailySimulationData.restrictionLevel,
            peopleMood: dailySimulationData.peopleMood,
            day: dailySimulationData.day
        }));
    }

}