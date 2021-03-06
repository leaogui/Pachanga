import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { RelatorioAreaSegService } from 'src/app/services/relatorios/relatorio-area-seg.service';
import { RelatorioDetalhesDialogComponent } from '../relatorio-detalhes-dialog/relatorio-detalhes-dialog.component';

@Component({
  selector: 'app-relatorios-seguranca',
  templateUrl: './relatorios-seguranca.component.html',
  styleUrls: ['./relatorios-seguranca.component.scss']
})
export class RelatoriosSegurancaComponent implements OnInit {



  codFesta = this.router.url.substring(this.router.url.indexOf('&') + 1, this.router.url.indexOf('/', this.router.url.indexOf('&')));
  public problemasPorArea = [];
  public resolucoesPorUsuario = [];
  public emissoesChamadas = [];
  view: any;
  chamadasUsuarioResp: any;
  showLegend = true;
  showLabels = true;
  isDoughnut = false;
  legendPosition = 'right';
  colorScheme = {
    domain: ['#d63333', '#a833d6', '#d68f33', '#d63395', '#d6d333', '#4633d6', '#87d633', '#338dd6', '#33d659', '#33d6bb']
  };

  constructor(
    public relAreaSegService: RelatorioAreaSegService,
    private router: Router,
    private translateService: TranslateService,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
    this.problemasArea(this.codFesta);
    this.chamadasUsuario(this.codFesta);
    this.usuariosPorEmissao(this.codFesta);
  }

  problemasArea(codFesta) {
    this.relAreaSegService.problemasArea(codFesta).subscribe((resp: any) => {
        const entries = Object.entries(resp.problemasArea);
        const dataset = [];
        for (const [prop, val] of entries) {
          dataset.push(
            {
              name: prop,
              value: val
            }
          );
        }
        this.problemasPorArea = dataset;
      });
  }


  chamadasUsuario(codFesta) {
    this.relAreaSegService.chamadasUsuario(codFesta).subscribe((resp: any) => {
      const dataset = [];
      this.chamadasUsuarioResp = resp.chamadasEmitidasFuncionario;
      for (const usuario of this.chamadasUsuarioResp) {
        const data = {
          name: usuario.nomeUsuario,
          series: [
            {
              name: this.translateService.instant('RELATARPROB.F'),
              value: parseInt(usuario.chamadasFinalizadas, 10)
            },
            {
              name: this.translateService.instant('RELATARPROB.E'),
              value: parseInt(usuario.chamadasEngano, 10)
            }
          ]
        };
        dataset.push(data);
      }
      this.resolucoesPorUsuario = dataset;
    });
  }

  usuariosPorEmissao(codFesta) {
    this.relAreaSegService.usuarioSolucionador(codFesta).subscribe((resp: any) => {
      const dataset = [];
      const solucionadorAlertasSeguranca = resp.solucionadorAlertasSeguranca;
      for (const solucionador in solucionadorAlertasSeguranca) {
        if (solucionadorAlertasSeguranca.hasOwnProperty(solucionador)) {
          dataset.push(
            {
              name: solucionador,
              value: solucionadorAlertasSeguranca[solucionador]
            }
          );
        }
      }
      this.emissoesChamadas = dataset;
    });
  }

  openDetalhes(event, relatorio) {
    this.dialog.open(RelatorioDetalhesDialogComponent, {
      width: '20rem',
      data: {
        codFesta: this.codFesta,
        data: event,
        relatorio,
        chamadasUsuario: relatorio === 'CONCSOL' ? this.chamadasUsuarioResp : null
      }
    });
  }

  onResize(event) {
    this.view = [event.target.innerWidth / 1.35, 400];
  }
}
