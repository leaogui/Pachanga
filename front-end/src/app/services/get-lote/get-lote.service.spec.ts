import { TestBed } from '@angular/core/testing';

import { GetLoteService } from './get-lote.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomMaterialModule } from '../../views/material/material.module';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RouterModule } from '@angular/router';

describe('GetLoteService', () => {
  let dialogSpy: MatDialog;
  let service: GetLoteService;

  beforeEach(() => {
    dialogSpy = jasmine.createSpyObj('MatDialog', ['open', 'closeAll']);
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CustomMaterialModule,
        RouterModule.forRoot([])
      ],
      providers: [
        { provide: MatDialog, useValue: dialogSpy },
        { provide: MatDialogRef, useValue: {} }
      ]
    });
    const token = {
      timeToken: '2020-09-21T01:14:04.028+0000',
      token: 'teste'
    };
    localStorage.setItem('token', JSON.stringify(token));
    service = TestBed.get(GetLoteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get Info at getLoteUnico', () => {
    service.loginService.usuarioInfo = {codusuario: 'teste'};
    expect(service.getLote('teste')).toBeTruthy();
  });

  it('should open a dialog through a method', () => {
    service.openErrorDialog('teste');
    expect(dialogSpy.open).toHaveBeenCalled();
  });
});
