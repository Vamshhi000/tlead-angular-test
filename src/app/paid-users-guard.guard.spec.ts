import { TestBed } from '@angular/core/testing';

import { PaidUsersGuardGuard } from './paid-users-guard.guard';

describe('PaidUsersGuardGuard', () => {
  let guard: PaidUsersGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(PaidUsersGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
