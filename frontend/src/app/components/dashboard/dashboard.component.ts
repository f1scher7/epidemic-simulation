import {Component, OnInit} from '@angular/core';
import {SimulationModel} from "../../models/simulation.model";
import {SimulationService} from "../../services/simulation.service";
import {DecimalPipe, NgClass, NgForOf, PercentPipe} from "@angular/common";
import {SimulationCreateModalComponent} from "../simulation-create-modal/simulation-create-modal.component";
import {Router} from "@angular/router";

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [
        PercentPipe,
        DecimalPipe,
        NgForOf,
        SimulationCreateModalComponent,
        NgClass
    ],
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

    simulations: SimulationModel[] =  [];
    isSimulationCreateModalVisible = false;
    errors: any = {};

    constructor(private router: Router, private simulationService: SimulationService) {}

    ngOnInit() {
        this.loadAllSimulations();
    }

    loadAllSimulations() {
        this.simulationService.getAllSimulations().subscribe({
            next: (data: SimulationModel[]) => {
                this.simulations = data;
            },
            error: (err) => {
                console.log(err);
            }
        })
    }

    createSimulation(simulation: SimulationModel) {
        this.simulationService.createSimulation(simulation).subscribe({
            next: (simulation) => {
                window.location.reload();
                this.errors = {};
            },
            error: (err) => {
                console.log(err);
                this.errors = { createSimulation: err };
            }
        })
    }

    viewSimulationDetails(simulation: SimulationModel) {
        this.router.navigate(['/simulation', simulation.id], { state: { simulation } });
    }

    deleteSimulation(simulation: SimulationModel) {
        if (confirm(`Are you sure you want to delete simulation ${simulation.name}?`)) {
            this.simulationService.deleteSimulation(simulation).subscribe({
                next: () => {
                    window.location.reload();
                },
                error: err => {
                    console.log('Error deleting simulation', err);
                }
            })
        }
    }

    showSimulationCreateModal() {
        this.isSimulationCreateModalVisible = true;
    }

    closeSimulationCreateModal() {
        this.errors = {};
        this.isSimulationCreateModalVisible = false;
    }

}
