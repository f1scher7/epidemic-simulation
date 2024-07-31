import { Routes } from '@angular/router';
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import {SimulationDetailsComponent} from "./components/simulation-details/simulation-details.component";

export const routes: Routes = [
    { path: '', component: DashboardComponent },
    { path: 'simulation/:id', component: SimulationDetailsComponent },
    { path: '**', redirectTo: '' }
];
