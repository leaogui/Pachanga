import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { take, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { LogService } from '../logging/log.service';
import { MatDialog } from '@angular/material/dialog';
import { ErroDialogComponent } from '../../views/erro-dialog/erro-dialog.component';
import { LoginService } from '../loginService/login.service';

@Injectable({
  providedIn: 'root'
})
export class CriarProdutoService {

  farol = false;
  private readonly urlAddProduto = `${environment.URL_BACK}produto/addProduto`;

  constructor(private http: HttpClient, public logService: LogService, public dialog: MatDialog,
              public loginService: LoginService) { }

  adicionarProduto(produto, codFesta) {
    if (!this.farol) {
      this.setFarol(true);
      const httpParams = new HttpParams()
        .append('codFesta', codFesta)
        .append('idUsuarioPermissao', this.loginService.usuarioInfo.codUsuario);
      return this.http.post(this.urlAddProduto, produto, {params: httpParams, responseType: 'text'}).pipe(
        take(1),
        catchError(error => {
          return this.handleError(error, this.logService);
        })
      );
    }
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