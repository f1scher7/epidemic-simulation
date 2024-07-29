import {Component, OnInit} from '@angular/core';
import {SimulationModel} from "../../models/simulation.model";
import {SimulationService} from "../../services/simulation.service";
import {DecimalPipe, NgForOf, PercentPipe} from "@angular/common";

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [
        PercentPipe,
        DecimalPipe,
        NgForOf
    ],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

    simulations: SimulationModel[] =  [];

    constructor(private simulationService: SimulationService) {}

    ngOnInit(): void {
        this.loadAllSimulations();
    }

    loadAllSimulations() {
        this.simulationService.getAllSimulations().subscribe(
            (data: SimulationModel[]) => {
                this.simulations = data;
                console.log(data);
            },
            error => {
                console.log(error);
            }
        )
    }

}
