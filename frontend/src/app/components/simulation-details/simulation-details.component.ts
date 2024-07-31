import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SimulationService} from "../../services/simulation.service";
import {DailySimulationDataModel} from "../../models/daily-simulation-data.model";
import {DailySimulationDataService} from "../../services/daily-simulation-data.service";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
    selector: 'app-simulation-details',
    standalone: true,
    imports: [
        FormsModule,
        NgIf
    ],
    templateUrl: './simulation-details.component.html',
    styleUrl: './simulation-details.component.css'
})
export class SimulationDetailsComponent {

    simulationId: number = NaN;
    simulation: any;
    dailySimulationDataList: DailySimulationDataModel[] = [];

    constructor(private route: ActivatedRoute, private router: Router, private simulationService: SimulationService, private dailySimulationDataService: DailySimulationDataService) {}

    ngOnInit(){
        this.simulationId = Number(this.route.snapshot.paramMap.get('id')).valueOf();

        if (history.state.simulation) {
            this.simulation = history.state.simulation;
        } else {
            this.simulation = this.simulationService.getSimulationById(this.simulationId);
        }

        if (this.simulation.simulationId) {
            this.getDailySimulationDataList();
        }
        console.log(this.dailySimulationDataList);
    }

    getDailySimulationDataList() {
        this.dailySimulationDataService.getAllDailySimulationData(this.simulationId).subscribe({
            next: (data: DailySimulationDataModel[]) => {
                this.dailySimulationDataList = data;
            },
            error: (err)=> {
                console.log(`Error getting DailySimulationDataList: ${err}`);
            }
        });

        if (this.dailySimulationDataList.length === 0) {
            this.dailySimulationDataService.createAllDailySimulationData(this.simulationId).subscribe({
                next: (data: DailySimulationDataModel[]) => {
                    this.dailySimulationDataList = data;
                },
                error: (err) => {
                    console.log(`Error creating DailySimulationDataList: ${err}`);
                }
            });
        }
    }

}
