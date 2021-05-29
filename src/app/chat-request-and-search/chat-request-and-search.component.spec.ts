import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatRequestAndSearchComponent } from './chat-request-and-search.component';

describe('ChatRequestAndSearchComponent', () => {
  let component: ChatRequestAndSearchComponent;
  let fixture: ComponentFixture<ChatRequestAndSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChatRequestAndSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChatRequestAndSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
