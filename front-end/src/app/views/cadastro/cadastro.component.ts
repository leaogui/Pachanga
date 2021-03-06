import { Component, OnInit } from '@angular/core';

import { FormControl, Validators } from '@angular/forms';

import { SocialLoginBaseComponent } from '../social-login-base/social-login-base.component';

import { FacebookLoginProvider, GoogleLoginProvider } from 'angular4-social-login';

import { MustMatch } from '../utils/matchPassword';
import { TermosUsoDialogComponent } from '../termos-uso-dialog/termos-uso-dialog.component';
import { Observable } from 'rxjs';
import {map, startWith} from 'rxjs/operators';


@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: [
    './cadastro.component.scss',
    '../../../../node_modules/font-awesome/css/font-awesome.css'
  ]
})
export class CadastroComponent extends SocialLoginBaseComponent implements OnInit {

  nomeCadastro;
  pronomeCadastro;
  dtnascCadastro;
  sexoCadastro;
  emailCadastro;
  senhaCadastro;
  options: string[] = [
                        this.translate.instant('GENEROS.HOMEMCIS'),
                        this.translate.instant('GENEROS.HOMEMTRANS'),
                        this.translate.instant('GENEROS.MULHERCIS'),
                        this.translate.instant('GENEROS.MULHERTRANS'),
                        this.translate.instant('GENEROS.NAOBINARIO'),
                        this.translate.instant('GENEROS.NAODIZER')
                      ];
  optionsPronome: string[] = [
                        this.translate.instant('PRONOMES.MASC'),
                        this.translate.instant('PRONOMES.FEMI')
                      ];
  filteredOptions: Observable<string[]>;
  filteredOptionsPronome: Observable<string[]>;

  maxDate = new Date();

  ngOnInit() {
    this.form = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      senha: new FormControl('', [Validators.required,
        Validators.pattern('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$')]),
      confirmacaoSenha: new FormControl('', Validators.required),
      sexo: new FormControl('', Validators.required),
      dtnasc: new FormControl('', Validators.required),
      nome: new FormControl('', Validators.required),
      pronome: new FormControl('', Validators.required),
      termos: new FormControl(false, Validators.requiredTrue)
    }, {
      validator: MustMatch('senha', 'confirmacaoSenha')
      });
    this.filteredOptions = this.form.get('sexo').valueChanges
    .pipe(
      startWith(''),
      map(value => this._filter(value))
    );

    this.filteredOptionsPronome = this.form.get('pronome').valueChanges
    .pipe(
      startWith(''),
      map(value => this._filterPronome(value))
    );
  }

  _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  _filterPronome(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.optionsPronome.filter(option => option.toLowerCase().includes(filterValue));
  }

  signUpWithPachanga(nome, pronome, dtNasc, sexo, email, senha): void {
    const userJson = {
      tipConta: 'P',
      email,
      senha,
      pronome: pronome.toUpperCase(),
      nomeUser: nome,
      dtNasc: dtNasc.slice(6, 10) + '-' + dtNasc.slice(3, 5) + '-' + dtNasc.slice(0, 2),
      genero: sexo.toUpperCase()
    };
    this.cadastrar_se(userJson);
  }

  signUpWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then((user) => {
      this.user = user;
      const userJson = {
        tipConta: 'G',
        nomeUser: this.user.name,
        email: this.user.email,
        dtNasc: '',
        pronome: 'N',
        genero: 'N',
        conta: this.user.id
      };
      this.cadastrar_se(userJson);
    });
  }

  signUpWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID).then((user) => {
      this.user = user;
      const userJson = {
        tipConta: 'F',
        email: this.user.email,
        nomeUser: this.user.name,
        dtNasc: '',
        pronome: 'N',
        genero: 'N',
        conta: this.user.id
      };
      this.cadastrar_se(userJson);
    });
  }

  openTermosUso() {
    this.modal.open(TermosUsoDialogComponent, {
      width: '35rem'
    });
  }

}
