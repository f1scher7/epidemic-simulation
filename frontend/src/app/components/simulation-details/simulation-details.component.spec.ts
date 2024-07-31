import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulationDetailsComponent } from './simulation-details.component';

describe('SimulationDetailsComponent', () => {
  let component: SimulationDetailsComponent;
  let fixture: ComponentFixture<SimulationDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SimulationDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SimulationDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
