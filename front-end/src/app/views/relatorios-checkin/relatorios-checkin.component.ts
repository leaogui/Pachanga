import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RelatorioCheckinService } from 'src/app/services/relatorios/relatorio-checkin.service';
import { interval, Observable, Subscription } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-relatorios-checkin',
  templateUrl: './relatorios-checkin.component.html',
  styleUrls: ['./relatorios-checkin.component.scss']
})
export class RelatoriosCheckinComponent implements OnInit, OnDestroy {

  codFesta: string;
  showLegend = true;
  showLabels = true;
  isDoughnut = false;
  legendPosition = 'right';
  ingressosCompradosEntradasValores = [];
  faixaEtariaValores = [];
  generosValores = [];
  quantidadeEntradasHoraValores = [];
  ingressosFestaCheckedUncheckedValores = [];

  source: any;
  subscription: Subscription;
  mySlideOptions = {items: 1, dots: true, nav: true};
  myCarouselOptions = {items: 80, dots: true, nav: true};

  colorScheme = {
    domain: ['#d63333', '#a833d6', '#d68f33', '#d63395', '#d6d333', '#4633d6', '#87d633', '#338dd6', '#33d659', '#33d6bb']
  };

  constructor(public relatorioCheckin: RelatorioCheckinService, public translateService: TranslateService, public router: Router) { }

  ngOnInit() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.source = null;
    const idFesta = this.router.url;
    this.codFesta = idFesta.substring(idFesta.indexOf('&') + 1, idFesta.indexOf('/', idFesta.indexOf('&')));
    this.faixaEtaria();
    this.generos();
    this.ingressosFestaCheckedUnchecked();
    this.quantidadeEntradasHora();
    this.updateRelatorios();
  }

  faixaEtaria() {
    this.relatorioCheckin.faixaEtaria(this.codFesta).subscribe((resp: any) => {
      const dataSetTemp = [];
      for (const faixaEtaria of Object.keys(resp.quantitadeFaixaEtaria)) {
        dataSetTemp.push({
          name: faixaEtaria === '0' ? this.translateService.instant('PERFIL.NOTINFORMED') : faixaEtaria,
          value: resp.quantitadeFaixaEtaria[faixaEtaria]
        });
      }
      this.faixaEtariaValores = dataSetTemp;
    });
  }

  generos() {
    this.relatorioCheckin.genero(this.codFesta).subscribe((resp: any) => {
      const dataSetTemp = [];
      for (const genero of Object.keys(resp.quantidadeGenero)) {
        dataSetTemp.push({
          name: genero === 'NF' ? this.translateService.instant('PERFIL.NOTINFORMED') : genero,
          value: resp.quantidadeGenero[genero]
        });
      }
      this.generosValores = dataSetTemp;
    });
  }

  ingressosFestaCheckedUnchecked() {
    this.relatorioCheckin.checkedUnchecked(this.codFesta).subscribe((resp: any) => {
      const dataSetTemp = [];
      for (const ingressosFestaCheckedUnchecked of Object.keys(resp.ingressoFestaCheckedUnchecked)) {
        const seriesTemp = [];
        for (const data of Object.keys(resp.ingressoFestaCheckedUnchecked[ingressosFestaCheckedUnchecked])) {
          seriesTemp.push(
            {
              name: this.translateService.instant('RELATORIOCHECKIN.NAOENTROU'),
              value: parseInt(data, 10)
            },
            {
              name: this.translateService.instant('RELATORIOCHECKIN.ENTROU'),
              value: parseInt(resp.ingressoFestaCheckedUnchecked[ingressosFestaCheckedUnchecked][data], 10)
            }
          );
        }
        dataSetTemp.push({
          name: ingressosFestaCheckedUnchecked,
          series: seriesTemp
        });
      }
      this.ingressosFestaCheckedUncheckedValores = dataSetTemp;
    });
  }

  quantidadeEntradasHora() {
    this.relatorioCheckin.qtdEntradasHora(this.codFesta).subscribe((resp: any) => {
      const seriesTemp = [];
      for (const quantidadeEntradasHora of Object.keys(resp.quantidadePessoasHora)) {
        seriesTemp.push({
          name: new Date(quantidadeEntradasHora),
          value: resp.quantidadePessoasHora[quantidadeEntradasHora]
        });
      }
      const dataSetTemp = [{
        name: this.translateService.instant('RELATORIOCHECKIN.QTDCHECKIN'),
        series: seriesTemp
      }];
      this.quantidadeEntradasHoraValores = dataSetTemp;
    });
  }

  formatDate(value): any {
    let formatOptions;
    if (value.getSeconds() !== 0) {
      formatOptions = { second: '2-digit' };
    } else if (value.getMinutes() !== 0) {
      formatOptions = { hour: '2-digit', minute: '2-digit' };
    } else if (value.getHours() !== 0) {
      formatOptions = { hour: '2-digit', minute: '2-digit' };
    } else if (value.getDate() !== 1) {
      formatOptions = value.getDay() === 0 ? { month: 'short', day: '2-digit' } : { weekday: 'short', day: '2-digit', month: '2-digit' };
    } else if (value.getMonth() !== 0) {
      formatOptions = { month: 'long' };
    } else {
      formatOptions = { year: 'numeric' };
    }
    return new Intl.DateTimeFormat('pt-br', formatOptions).format(value);
  }

  toolTipDate(value): any {
    let formatOptions;
    if (value.getSeconds() !== 0) {
      formatOptions = { day: '2-digit', month: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' };
    }
    return new Intl.DateTimeFormat('pt-br', formatOptions).format(value);
  }

  updateRelatorios() {
    this.source = interval(5000);
    this.subscription = this.source.subscribe(() => {
        this.faixaEtaria();
        this.generos();
        this.ingressosFestaCheckedUnchecked();
        this.quantidadeEntradasHora();
      }
    );
  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.source = null;
  }

}
