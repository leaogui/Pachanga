import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { GetFestaService } from './get-festa.service';
import { RouterModule } from '@angular/router';
import { CustomMaterialModule } from '../../views/material/material.module';

describe('GetFestaService', () => {
  let service: GetFestaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterModule.forRoot([]),
        CustomMaterialModule
      ]
    });

    service = TestBed.get(GetFestaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should set farol', () => {
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
    service.setFarol(false);
    expect(service.getFarol()).toBeFalsy();
  });

  it('should get farol', () => {
    expect(service.getFarol()).toBeFalsy();
    service.setFarol(true);
    expect(service.getFarol()).toBeTruthy();
  });

  it('should get Info at acessarFesta', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.acessarFesta('teste')).toBeTruthy();

    expect(service.getFarol()).toBeTruthy();

    expect(service.acessarFesta('teste')).toBeFalsy();
  });
});
