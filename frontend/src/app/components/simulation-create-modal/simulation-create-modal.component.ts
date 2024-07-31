import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgClass, NgIf, NgStyle} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {SimulationModel} from "../../models/simulation.model";

@Component({
    selector: 'app-simulation-create-modal',
    standalone: true,
    imports: [
        NgIf,
        FormsModule,
        NgClass,
        NgStyle
    ],
    templateUrl: './simulation-create-modal.component.html',
    styleUrl: './simulation-create-modal.component.css'
})
export class SimulationCreateModalComponent {
    @Input() isVisible = false;
    @Input() errors: any;
    @Output() closeModal = new EventEmitter<void>();
    @Output() createSimulation = new EventEmitter<SimulationModel>();

    newSimulation: SimulationModel = {
        id: 0,
        name: '',
        population: 1,
        initialInfected: 1,
        infectionRate: 0.1,
        mortalityRate: 0.01,
        infectionDaysDelay: 1,
        daysToRecovery: 1,
        daysToDeath: 1,
        simulationDays: 1
    };

    onClose() {
        this.closeModal.emit();
    }

    submitForm() {
        this.createSimulation.emit(this.newSimulation);
    }
}
