import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { LoginService } from '../loginService/login.service';
import { MatDialog } from '@angular/material';
import { ErroDialogComponent } from 'src/app/views/erro-dialog/erro-dialog.component';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class GetGruposService {

  private readonly urlGrupos = `${environment.URL_BACK}grupo/getAllGruposFesta`;
  private readonly urlGrupoUnico = `${environment.URL_BACK}grupo/getGrupoFesta`;

  public farol = false;

  constructor(private http: HttpClient, public logService: LogService,
              public loginService: LoginService, public dialog: MatDialog, public router: Router) { }

  getGrupos(idFesta) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
      .append('codFesta', idFesta)
      .append('idUsuario', this.loginService.usuarioInfo.codUsuario);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.get(this.urlGrupos, {params: httpParams, headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }

  getGrupoUnico(idGrupo) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
      .append('codGrupo', idGrupo)
      .append('idUsuario', this.loginService.usuarioInfo.codUsuario);

      let headers = new HttpHeaders();
      headers = headers.append('Content-Type', 'application/json');
      headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

      return this.http.get(this.urlGrupoUnico, {params: httpParams, headers}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    this.openErrorDialog(error.error);
    let painel = this.router.url;
    painel = painel.slice(0, -7) + 'painel';
    this.router.navigate([painel]);
    this.setFarol(false);
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    return throwError(error);
  }

  openErrorDialog(error) {
    const dialogRef = this.dialog.open(ErroDialogComponent, {
      width: '250px',
      data: {erro: error}
    });
  }

  setFarol(flag: boolean) {
    this.farol = flag;
  }

  getFarol() {
    return this.farol;
  }
}
