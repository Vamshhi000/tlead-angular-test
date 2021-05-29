import { TestBed } from '@angular/core/testing';
import { commonServiceService } from './common-service.service';



describe('CommonServiceService', () => {
  let service: commonServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(commonServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
