import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SimulationService } from '../../services/simulation.service';
import { DailySimulationDataModel } from '../../models/daily-simulation-data.model';
import { DailySimulationDataService } from '../../services/daily-simulation-data.service';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { SimulationModel } from '../../models/simulation.model';
import { BaseChartDirective } from 'ng2-charts';
import {Chart, ChartData, ChartOptions, LineController, LineElement, PointElement} from 'chart.js';
import { PieController, ArcElement, Tooltip, Legend, BarController, CategoryScale, LinearScale, BarElement } from 'chart.js';
import { ChangeDetectorRef } from '@angular/core';

Chart.register(PieController, ArcElement, Tooltip, Legend, BarController, CategoryScale, LinearScale, BarElement, LineController, PointElement, LineElement);

@Component({
    selector: 'app-simulation-details',
    standalone: true,
    imports: [
        FormsModule,
        NgIf,
        BaseChartDirective
    ],
    templateUrl: './simulation-details.component.html',
    styleUrls: ['./simulation-details.component.css']
})
export class SimulationDetailsComponent implements OnInit, OnDestroy {

    simulationId: number = NaN;
    simulation: any;
    dailySimulationDataList: DailySimulationDataModel[] = [];
    successMess: any = {};
    errors: any = {};
    currentDay = 0;
    totalDays = 0;
    private updateInterval: any;

    pieChartData: ChartData<'pie', number[], string | string[]> = {
        labels: ['Healthy', 'Infected', 'Dead', 'Recovered'],
        datasets: [{
            data: [0, 0, 0, 0],
            backgroundColor: ['#4caf50', '#ffeb3b', '#f44336', '#2196f3'],
        }]
    };

    pieChartOptions: ChartOptions<'pie'> = {
        responsive: true,
        animation: {
            duration: 0
        },
        plugins: {
            legend: {
                position: 'top'
            },
            tooltip: {
                callbacks: {
                    label: (tooltipItem) => {
                        const dataset = tooltipItem.dataset.data as number[];
                        const index = tooltipItem.dataIndex;
                        const label = tooltipItem.label || '';
                        const value = dataset[index] || 0;
                        return `${label}: ${value}`;
                    }
                }
            }
        }
    };

    lineChartData: {
        datasets: ({ borderColor: string; data: any[]; label: string; fill: boolean })[];
        labels: any[]
    } = {
        labels: [],
        datasets: [
            {
                label: 'Infection Rate',
                data: [],
                borderColor: '#ffeb3b',
                fill: false
            },
            {
                label: 'Restriction Level',
                data: [],
                borderColor: '#f44336',
                fill: false
            },
            {
                label: 'People Mood',
                data: [],
                borderColor: '#2196f3',
                fill: false
            }
        ]
    };

    lineChartOptions: ChartOptions<'line'> = {
        responsive: true,
        animation: {
            duration: 0
        },
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Days'
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Values'
                }
            }
        },
        plugins: {
            legend: {
                position: 'top'
            },
            tooltip: {
                callbacks: {
                    label: (tooltipItem) => {
                        const dataset = tooltipItem.dataset.data as number[];
                        const index = tooltipItem.dataIndex;
                        const label = tooltipItem.dataset.label || '';
                        const value = dataset[index] || 0;
                        return `${label}: ${value}`;
                    }
                }
            }
        }
    };

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private simulationService: SimulationService,
        private dailySimulationDataService: DailySimulationDataService,
        private cdr: ChangeDetectorRef
    ) {}

    ngOnInit() {
        this.simulationId = Number(this.route.snapshot.paramMap.get('id')).valueOf();
        this.simulationService.getSimulationById(this.simulationId).subscribe({
            next: (sim) => {
                this.simulation = sim;
                this.getDailySimulationDataList();
            },
            error: (err) => {
                console.log(`Error getting simulation: ${err}`);
            }
        });
    }

    ngOnDestroy() {
        if (this.updateInterval) {
            clearInterval(this.updateInterval);
        }
    }

    getDailySimulationDataList() {
        this.dailySimulationDataService.getAllDailySimulationData(this.simulationId).subscribe({
            next: (data: DailySimulationDataModel[]) => {
                this.dailySimulationDataList = data;
                this.totalDays = Math.max(...data.map(d => d.day));
                this.updateLineChart();

                if (this.dailySimulationDataList.length > 0) {
                    this.startAutoUpdate();
                } else {
                    this.dailySimulationDataService.createAllDailySimulationData(this.simulationId).subscribe({
                        next: (data: DailySimulationDataModel[]) => {
                            this.dailySimulationDataList = data;
                            this.totalDays = Math.max(...data.map(d => d.day));
                            this.startAutoUpdate();
                        }
                    })
                }
            },
            error: (err) => {
                console.log(`Error getting DailySimulationDataList: ${err}`);
            }
        });
    }

    startAutoUpdate() {
        let index = 0;
        this.updatePieChart(this.dailySimulationDataList[index]);
        this.updateInterval = setInterval(() => {
            index = (index + 1) % this.dailySimulationDataList.length;
            this.currentDay = this.dailySimulationDataList[index].day;
            if (this.currentDay >= this.totalDays) {
                clearInterval(this.updateInterval);
                return;
            }
            this.updatePieChart(this.dailySimulationDataList[index]);
        }, 500);
    }

    updateSimulationAndDailySimulationData(simulation: SimulationModel) {
        this.simulationService.updateSimulation(simulation).subscribe({
            next: (data: SimulationModel) => {
                this.simulation = data;
                this.errors = {};
                this.successMess = { updateSimulation: 'Simulation was updated successfully' };
                this.getDailySimulationDataList();

                setTimeout(() => { window.location.reload() }, 1000);
            },
            error: (err) => {
                this.successMess = {};
                this.errors = { updateSimulation: err };
            }
        });
    }

    updatePieChart(data: DailySimulationDataModel) {
        const totalPopulation = this.simulation.population;

        const healthyPercentage = (data.healthy / totalPopulation) * 100;
        const infectedPercentage = (data.infected / totalPopulation) * 100;
        const deadPercentage = (data.dead / totalPopulation) * 100;
        const recoveredPercentage = (data.recovered / totalPopulation) * 100;

        this.pieChartData = {
            labels: [
                `Healthy: ${healthyPercentage.toFixed(2)}%`,
                `Infected: ${infectedPercentage.toFixed(2)}%`,
                `Dead: ${deadPercentage.toFixed(2)}%`,
                `Recovered: ${recoveredPercentage.toFixed(2)}%`
            ],
            datasets: [{
                data: [
                    data.healthy,
                    data.infected,
                    data.dead,
                    data.recovered
                ],
                backgroundColor: this.pieChartData.datasets[0].backgroundColor,
            }]
        };

        this.currentDay = data.day;
        this.cdr.detectChanges();
    }

    updateLineChart() {
        if (this.dailySimulationDataList.length === 0) {
            return;
        }

        const labels = this.dailySimulationDataList.map(d => `Day ${d.day}`);
        const infectionRateData = this.dailySimulationDataList.map(d => d.dynamicalInfectionRate);
        const restrictionLevelData = this.dailySimulationDataList.map(d => d.restrictionLevel);
        const peopleMoodData = this.dailySimulationDataList.map(d => d.peopleMood);

        this.lineChartData = {
            labels: labels,
            datasets: [
                {
                    label: 'Infection Rate',
                    data: infectionRateData,
                    borderColor: '#ffeb3b',
                    fill: false
                },
                {
                    label: 'Restriction Level',
                    data: restrictionLevelData,
                    borderColor: '#f44336',
                    fill: false
                },
                {
                    label: 'People Mood',
                    data: peopleMoodData,
                    borderColor: '#2196f3',
                    fill: false
                }
            ]
        };

        this.cdr.detectChanges();
    }

    goToDashboard() {
        this.router.navigate(['/dashboard']);
    }
}
