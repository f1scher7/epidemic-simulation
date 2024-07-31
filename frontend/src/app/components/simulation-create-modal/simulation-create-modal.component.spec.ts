import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulationCreateModalComponent } from './simulation-create-modal.component';

describe('SimulationCreateModalComponent', () => {
  let component: SimulationCreateModalComponent;
  let fixture: ComponentFixture<SimulationCreateModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SimulationCreateModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SimulationCreateModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
