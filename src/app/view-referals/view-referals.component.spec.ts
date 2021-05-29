import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewReferalsComponent } from './view-referals.component';

describe('ViewReferalsComponent', () => {
  let component: ViewReferalsComponent;
  let fixture: ComponentFixture<ViewReferalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewReferalsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewReferalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
