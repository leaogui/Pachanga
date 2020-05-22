import { TestBed } from '@angular/core/testing';

import { AuthGuard } from './auth.guard';
import { RouterModule } from '@angular/router';

import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../views/material/material.module';

describe('AuthGuard', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientModule,
      RouterModule.forRoot([]),
      CustomMaterialModule
    ],
  }));

  it('should be created', () => {
    const service: AuthGuard = TestBed.get(AuthGuard);
    expect(service).toBeTruthy();
  });
});
