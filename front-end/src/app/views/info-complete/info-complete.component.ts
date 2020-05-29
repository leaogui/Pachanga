import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/loginService/login.service';

@Component({
  selector: 'app-info-complete',
  templateUrl: './info-complete.component.html',
  styleUrls: ['./info-complete.component.scss']
})
export class InfoCompleteComponent implements OnInit {

  constructor(public loginService: LoginService) { }

  public mensagem: boolean;

  ngOnInit() {
    this.mensagem = true;
  }

  fecharMensagem() {
    this.mensagem = false;
  }

}