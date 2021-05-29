import { TestBed } from '@angular/core/testing';

import { ResetGuardGuard } from './reset-guard.guard';

describe('ResetGuardGuard', () => {
  let guard: ResetGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ResetGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
