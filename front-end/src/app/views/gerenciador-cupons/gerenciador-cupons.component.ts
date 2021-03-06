import { Component, OnInit } from '@angular/core';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { Router } from '@angular/router';
import { GetCuponsService } from 'src/app/services/get-cupons/get-cupons.service';
import { CriarCupomDialogComponent } from '../criar-cupom-dialog/criar-cupom-dialog.component';
import { DeletarCupomDialogComponent } from '../deletar-cupom-dialog/deletar-cupom-dialog.component';
import { EditarCupomDialogComponent } from '../editar-cupom-dialog/editar-cupom-dialog.component';

export interface TabelaCupons {
  codCupom: any;
  nomeCupom: string;
  tipoDesconto: any;
  precoDesconto: any;
  porcentagemDesc: any;
  dataIniDesconto: any;
  dataFimDesconto: any;
}

@Component({
  selector: 'app-gerenciador-cupons',
  templateUrl: './gerenciador-cupons.component.html',
  styleUrls: ['./gerenciador-cupons.component.scss']
})
export class GerenciadorCuponsComponent implements OnInit {

  public codFesta: any;
  cupons: TabelaCupons[] = [];
  displayedColumns: string[] = ['nomeCupom', 'precoDesconto', 'dataIniDesconto', 'dataFimDesconto', 'actions'];
  dataSource = new MatTableDataSource<TabelaCupons>(this.cupons);

  constructor(public dialog: MatDialog, public router: Router, public getCupom: GetCuponsService) { }

  ngOnInit() {
    const idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.resgatarCupons();
  }

  resgatarCupons() {
    this.cupons = [];
    const idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.getCupom.getCupons(this.codFesta).subscribe((resp: any) => {
      this.getCupom.setFarol(false);
      for (const cupom of resp) {
        this.cupons.push({nomeCupom: cupom.nomeCupom,
                          precoDesconto: cupom.precoDesconto,
                          codCupom: cupom.codCupom,
                          porcentagemDesc: cupom.porcentagemDesc,
                          tipoDesconto: cupom.tipoDesconto,
                          dataIniDesconto: cupom.dataIniDesconto,
                          dataFimDesconto: cupom.dataFimDesconto
                        });
      }
      this.dataSource.data = this.cupons.sort(this.nomeCupomSort);
    });
  }

  nomeCupomSort(a, b) {
    if (a.nomeCupom > b.nomeCupom) {
      return 1;
    } else {
      return -1;
    }
  }

  openDialogDelete(cupom) {
    this.dialog.open(DeletarCupomDialogComponent, {
      width: '20rem',
      data: {
        cupom,
        codFesta: this.codFesta,
        component: this
      }
    });
  }

  openDialogEdit(cupom) {
    this.dialog.open(EditarCupomDialogComponent, {
      width: '20rem',
      data: {
        cupom,
        codFesta: this.codFesta,
        component: this
      }
    });
  }

  openDialogAdd() {
    this.dialog.open(CriarCupomDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.codFesta,
        component: this
      }
    });
  }

  getDateFromDTF(date) {
    let conversion = date.split(' ', 1);
    conversion = conversion[0].split('-');
    const day = conversion[2].split('T');
    return day[0] + '/' + conversion[1] + '/' + conversion[0];
  }

}
