import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NumberVerificatioNComponent } from './number-verificatio-n.component';

describe('NumberVerificatioNComponent', () => {
  let component: NumberVerificatioNComponent;
  let fixture: ComponentFixture<NumberVerificatioNComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NumberVerificatioNComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NumberVerificatioNComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
