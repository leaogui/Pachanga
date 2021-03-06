import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { CustomMaterialModule } from '../material/material.module';
import { NavbarComponent } from '../navbar/navbar.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from '../../app-routing.module';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { IndexComponent } from '../index/index.component';

import { SocialLoginModule, AuthServiceConfig } from 'angular4-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angular4-social-login';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PerfilComponent } from '../perfil/perfil.component';
import { InfoCompleteComponent } from '../info-complete/info-complete.component';
import { MenuFestasComponent } from '../menu-festas/menu-festas.component';
import { FestaPainelControleComponent } from '../festa-painel-controle/festa-painel-controle.component';
import { CriarFestaComponent } from '../criar-festa/criar-festa.component';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { EditarFestaComponent } from '../editar-festa/editar-festa.component';
import { NotFoundComponent } from '../not-found/not-found.component';
import { CriarGrupoComponent } from '../criar-grupo/criar-grupo.component';
import { FiltroFestaPipe } from '../menu-festas/filtroFesta.pipe';
import { GerenciadorMembrosComponent } from '../gerenciador-membros/gerenciador-membros.component';
import { EditarGrupoComponent } from '../editar-grupo/editar-grupo.component';
import { DistribuicaoPermissoesComponent } from '../distribuicao-permissoes/distribuicao-permissoes.component';
import { EstoquePainelComponent } from '../estoque-painel/estoque-painel.component';
import { GerenciadorProdutosComponent } from '../gerenciador-produtos/gerenciador-produtos.component';
import { FestaDetalhesDialogComponent } from '../festa-detalhes-dialog/festa-detalhes-dialog.component';
import { NotificacoesComponent } from '../notificacoes/notificacoes.component';
import { MomentModule } from 'ngx-moment';
import { PainelSegurancaComponent } from '../painel-seguranca/painel-seguranca.component';
import { RelatoriosPainelComponent } from '../relatorios-painel/relatorios-painel.component';
import { FormsPainelComponent } from '../forms-painel/forms-painel.component';
import { PainelIngressoComponent } from '../painel-ingresso/painel-ingresso.component';
import { CriarLoteComponent } from '../criar-lote/criar-lote.component';
import { EditarLoteComponent } from '../editar-lote/editar-lote.component';
import { VendaIngressosComponent } from '../venda-ingressos/venda-ingressos.component';
import { ThirdPartyPainelComponent } from '../third-party-painel/third-party-painel.component';
import { CheckoutComponent } from '../checkout/checkout.component';
import { LeitorQrComponent } from '../leitor-qr/leitor-qr.component';
import { CheckInComponent } from '../check-in/check-in.component';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { MeusIngressosComponent } from '../meus-ingressos/meus-ingressos.component';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { NgxPayPalModule } from 'ngx-paypal';
import { GerenciadorCuponsComponent } from '../gerenciador-cupons/gerenciador-cupons.component';
import { ControleSidenavComponent } from '../controle-sidenav/controle-sidenav.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RelatoriosExportComponent } from '../relatorios-export/relatorios-export.component';
import { PermissionFilter } from '../utils/permission-filter.pipe';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

const config = new AuthServiceConfig([
  {
    id: GoogleLoginProvider.PROVIDER_ID,
    provider: new GoogleLoginProvider('215875672881-jp0n01npv48op3j0c67mm0jlauoov3hb.apps.googleusercontent.com')
  },
  {
    id: FacebookLoginProvider.PROVIDER_ID,
    provider: new FacebookLoginProvider('620215655237701')
  }
]);

export function provideConfig() {
  return config;
}

describe('LoginComponent', () => {
  jasmine.DEFAULT_TIMEOUT_INTERVAL = 10000;
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        LoginComponent,
        NavbarComponent,
        CadastroComponent,
        IndexComponent,
        PerfilComponent,
        InfoCompleteComponent,
        MenuFestasComponent,
        FestaPainelControleComponent,
        CriarFestaComponent,
        EditarFestaComponent,
        NotFoundComponent,
        CriarGrupoComponent,
        FiltroFestaPipe,
        GerenciadorMembrosComponent,
        EditarGrupoComponent,
        DistribuicaoPermissoesComponent,
        EstoquePainelComponent,
        GerenciadorProdutosComponent,
        FestaDetalhesDialogComponent,
        NotificacoesComponent,
        PainelSegurancaComponent,
        RelatoriosPainelComponent,
        FormsPainelComponent,
        PainelIngressoComponent,
        CriarLoteComponent,
        EditarLoteComponent,
        VendaIngressosComponent,
        ThirdPartyPainelComponent,
        CheckoutComponent,
        LeitorQrComponent,
        CheckInComponent,
        MeusIngressosComponent,
        GerenciadorCuponsComponent,
        ControleSidenavComponent,
        RelatoriosExportComponent,
        PermissionFilter
      ],
      imports: [
        MomentModule,
        CustomMaterialModule,
        NgxMaterialTimepickerModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        SocialLoginModule,
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
        ZXingScannerModule,
        MaterialFileInputModule,
        NgxPayPalModule
      ],
      providers: [
        {
          provide: AuthServiceConfig,
          useFactory: provideConfig
        }
      ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form invalid when empty', () => {
    expect(component.form.valid).toBeFalsy();
  });

  it('form valid when correctly filled', () => {
    const field1 = 'email';
    const field2 = 'senha';

    const email = component.form.controls[field1];
    const senha = component.form.controls[field2];

    email.setValue('teste@teste.com');
    senha.setValue('123123Aa!');

    expect(component.form.valid).toBeTruthy();
  });

  it('email required', () => {
    const field = 'email';

    const email = component.form.controls[field];


    let errors = {};
    errors = email.errors || {};

    const errorName = 'required';

    expect(errors[errorName]).toBeTruthy();

    email.setValue('teste@teste.com');

    errors = {};
    errors = email.errors || {};

    expect(errors[errorName]).toBeFalsy();
  });

  it('email format', () => {
    const field = 'email';

    const email = component.form.controls[field];

    email.setValue('teste@teste.com');

    let errors = {};
    errors = email.errors || {};

    const errorName = 'email';

    expect(errors[errorName]).toBeFalsy();

    email.setValue('teste');

    errors = {};
    errors = email.errors || {};

    expect(errors[errorName]).toBeTruthy();

  });

  it('senha required', () => {
    const field = 'senha';

    const senha = component.form.controls[field];


    let errors = {};
    errors = senha.errors || {};

    const errorName = 'required';

    expect(errors[errorName]).toBeTruthy();

    senha.setValue('123');

    errors = {};
    errors = senha.errors || {};

    expect(errors[errorName]).toBeFalsy();
  });

  it('should call cadastrar_se at SignUpWithPachanga', () => {
    spyOn(component, 'autenticar');
    component.signInWithPachanga('teste', 'teste');
    expect(component.autenticar).toHaveBeenCalled();
  });

  it('should get f to get form controls', () => {
    expect(component.f).toBe(component.form.controls);
  });

  it('should sign in facebook at signUpWithFB', () => {
    spyOn(component, 'autenticar');

    const socialUser = {
      provider: '',
      id: 'teste123',
      email: 'Email',
      name: 'Name',
      photoUrl: '',
      firstName: '',
      lastName: '',
      authToken: ''
    };
    const spy = spyOn(component.authService, 'signIn')
    .and
    .returnValue(Promise.resolve(socialUser));

    const userJson = {
      tipConta: 'F',
      email: 'Email',
      nomeUser: 'Name',
      conta: 'teste123'
    };

    component.signInWithFB();

    spy.calls.mostRecent().returnValue.then(() => {
      expect(component.user).toEqual(socialUser);
      expect(component.autenticar).toHaveBeenCalledWith(userJson);
    });
  });

  it('should sign in google at signInWithGoogle', () => {
    spyOn(component, 'autenticar');

    const socialUser = {
      provider: '',
      id: 'teste123',
      email: 'Email',
      name: 'Name',
      photoUrl: '',
      firstName: '',
      lastName: '',
      authToken: ''
    };
    const spy = spyOn(component.authService, 'signIn')
    .and
    .returnValue(Promise.resolve(socialUser));

    const userJson = {
      tipConta: 'G',
      email: 'Email',
      nomeUser: 'Name',
      conta: 'teste123'
    };

    component.signInWithGoogle();

    spy.calls.mostRecent().returnValue.then(() => {
      expect(component.user).toEqual(socialUser);
      expect(component.autenticar).toHaveBeenCalledWith(userJson);
    });
  });

});
