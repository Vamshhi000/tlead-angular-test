import { TestBed } from '@angular/core/testing';

import { ServiceLayerService } from './service-layer.service';

describe('ServiceLayerService', () => {
  let service: ServiceLayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceLayerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
