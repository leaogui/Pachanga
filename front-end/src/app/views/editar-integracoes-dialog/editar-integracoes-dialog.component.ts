import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material';
import { EditarIntegracaoService } from 'src/app/services/editar-integracao/editar-integracao.service';
import { EventbriteApiService } from 'src/app/services/eventbrite-api/eventbrite-api.service';
import { SymplaApiService } from 'src/app/services/sympla-api/sympla-api.service';

@Component({
  selector: 'app-editar-integracoes-dialog',
  templateUrl: './editar-integracoes-dialog.component.html',
  styleUrls: ['./editar-integracoes-dialog.component.scss']
})
export class EditarIntegracoesDialogComponent implements OnInit {

  component: any;
  codFesta: any;
  integracao: any;
  form: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) data, public editarService: EditarIntegracaoService,
              public dialog: MatDialog, public formBuilder: FormBuilder,
              public symplaService: SymplaApiService, public eventbriteService: EventbriteApiService) {
    this.integracao = data.integracao;
    this.codFesta = data.codFesta;
    this.component = data.component;
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      terceiro: new FormControl({value: this.integracao.terceiroInt, disabled: true}, Validators.required),
      codEvento: new FormControl(this.integracao.codEvent, Validators.required),
      token: new FormControl(this.integracao.token, Validators.required)
    });
  }

  get f() { return this.form.controls; }

  submitForm(codEvent, token) {
    const integracao = {
      codFesta: this.codFesta,
      codInfo: this.integracao.codInfo,
      terceiroInt: this.integracao.terceiroInt,
      codEvent,
      token
    };
    this.checarIntegracao(integracao);
  }

  checarIntegracao(integracao) {
    if (integracao.terceiroInt === 'S') {
      this.symplaService.testSymplaConnection(integracao.codEvent, integracao.token).subscribe((resp: any) => {
        this.editarIntegracao(integracao);
      });
    } else if (integracao.terceiroInt === 'E') {
      this.eventbriteService.testEventbriteConnection(integracao.codEvent, integracao.token).subscribe((resp: any) => {
        this.editarIntegracao(integracao);
      });
    }
  }

  editarIntegracao(integracao) {
    this.editarService.editarIntegracao(integracao).subscribe((resp: any) => {
      this.editarService.setFarol(false);
      this.component.ngOnInit();
      this.dialog.closeAll();
    });
  }

}
