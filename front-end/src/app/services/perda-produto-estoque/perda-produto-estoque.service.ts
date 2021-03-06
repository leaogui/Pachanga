import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';
import { LogService } from '../logging/log.service';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { LoginService } from '../loginService/login.service';
import { MatDialog } from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class PerdaProdutoEstoqueService {

  public quebra: true;
  farol = false;
  private readonly urlQuebraProdutoEstoque = `${environment.URL_BACK}produto/quebraProdutoEstoque`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  perdaProdutoEstoque(codEstoque, dose, codProduto, quantidades) {
    const httpParams = new HttpParams()
    .append('idUsuarioPermissao', this.loginService.usuarioInfo.codUsuario)
    .append('codProduto', codProduto)
    .append('dose', dose)
    .append('codEstoque', codEstoque);

    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    headers = headers.append('Authorization', 'Bearer ' + JSON.parse(localStorage.getItem('token')).token);

    return this.http.put(this.urlQuebraProdutoEstoque, quantidades, {params: httpParams, headers}).pipe(
      take(1),
      catchError(error => {
        return this.handleError(error, this.logService);
      })
    );
  }

  handleError = (error: HttpErrorResponse, logService: LogService) => {
    this.dialog.closeAll();
    this.openErrorDialog(error.error);
    logService.initialize();
    logService.logHttpInfo(JSON.stringify(error), 0, error.url);
    this.setFarol(false);
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
