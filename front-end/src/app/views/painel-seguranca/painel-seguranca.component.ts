import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { GetFestaService } from 'src/app/services/get-festa/get-festa.service';
import { GetSegurancaService } from 'src/app/services/get-seguranca/get-seguranca.service';
import { SegurancaProblemasService } from 'src/app/services/seguranca-problemas/seguranca-problemas.service';
import { CriarAreaSegurancaDialogComponent } from '../criar-area-seguranca-dialog/criar-area-seguranca-dialog.component';
import { DeleteAreaSegurancaDialogComponent } from '../delete-area-seguranca-dialog/delete-area-seguranca-dialog.component';
import { DetalhesProblemaDialogComponent } from '../detalhes-problema-dialog/detalhes-problema-dialog.component';
import { EditarAreaSegurancaDialogComponent } from '../editar-area-seguranca-dialog/editar-area-seguranca-dialog.component';
import { RelatarProblemaDialogComponent } from '../relatar-problema-dialog/relatar-problema-dialog.component';

export interface TabelaSeguranca {
  nome: string;
  status: string;
}

@Component({
  selector: 'app-painel-seguranca',
  templateUrl: './painel-seguranca.component.html',
  styleUrls: ['./painel-seguranca.component.scss']
})

export class PainelSegurancaComponent implements OnInit {

  constructor(
    public fb: FormBuilder,
    public dialog: MatDialog,
    public getFestaService: GetFestaService,
    private segProbService: SegurancaProblemasService,
    public router: Router,
    public getSeguranca: GetSegurancaService
    ) {
    this.options = fb.group({
        bottom: 55,
        top: 0
      });
  }

  festaNome: string;
  codFesta: any;
  areas: any = [];
  options: FormGroup;
  festa: any;
  statusFesta: any;
  panelOpenState = false;
  public forms = {};
  estoques: any;
  dataSources = [];
  subscription: Subscription;
  source: any;
  public idFesta: string;

  displayedColumns: string[] = ['nome', 'status'];


  ngOnInit() {
    this.source = null;
    const url = this.router.url;
    this.dataSources = [];
    this.idFesta = url.substring(url.indexOf('&') + 1, url.indexOf('/', url.indexOf('&')));
    this.resgatarDadosFesta();

    this.segProbService.updateProblemasEmitter.subscribe(() => {
      this.resgatarDadosFesta();
    });
  }

  resgatarAreaSeguranca() {
    this.getSeguranca.getAreaSeguranca(this.festa.codFesta).subscribe((resp: any) => {
      this.areas = resp;
      console.log(resp);
      this.getSeguranca.setFarol(false);
    });
  }

  resgatarDadosFesta() {
    this.getFestaService.acessarFesta(this.idFesta).subscribe((resp: any) => {
      this.festa = resp;
      this.festaNome = resp.nomeFesta;
      this.statusFesta = resp.statusFesta;
      this.resgatarAreaSeguranca();
      this.getFestaService.setFarol(false);
    });
  }

  getProblemasArea(area) {
    let problemas: any = [];
    this.segProbService.getAllProblemasArea(area.codArea, this.idFesta).subscribe(
      (resp: any) => {
        problemas = resp;
        this.segProbService.setFarol(false);
      }
    );

    return problemas;
  }

  atribuirProblemasAreas(areas) {
    for (const area of areas) {
      // Object.assign(
      //   area,
      //   {problemas: this.getProblemasArea(area)}
      // );

      console.log(this.getProblemasArea(area));
    }
  }

  verDetalhesProblema(problema) {
    this.dialog.open(DetalhesProblemaDialogComponent, {
      width: '30rem',
      data: {
        problema
      }
    });
  }

  openDialogRelatarProblema(area) {
    this.dialog.open(RelatarProblemaDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.festa.codFesta,
        component: this,
        area
      }
    });
  }

  openDialogCriarArea() {
    this.dialog.open(CriarAreaSegurancaDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.festa.codFesta,
        component: this
      }
    });
  }

  openDialogDelete(codArea) {
    this.dialog.open(DeleteAreaSegurancaDialogComponent, {
      width: '20rem',
      data: {
        codArea,
        component: this
      }
    });
  }

  openDialogEdit(area) {
    this.dialog.open(EditarAreaSegurancaDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.festa.codFesta,
        area,
        component: this
      }
    });
  }

}
