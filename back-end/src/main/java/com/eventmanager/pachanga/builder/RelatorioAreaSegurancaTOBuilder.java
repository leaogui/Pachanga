package com.eventmanager.pachanga.builder;

import java.util.List;
import java.util.Map;

import com.eventmanager.pachanga.dtos.ChamadasEmitidasFuncionarioTO;
import com.eventmanager.pachanga.dtos.RelatorioAreaSegurancaTO;

public class RelatorioAreaSegurancaTOBuilder {

	private Map<String, Integer> problemasArea;

	private List<ChamadasEmitidasFuncionarioTO> chamadasEmitidasFuncionario;

	private Map<String, Integer> solucionadorAlertasSeguranca;
	
	public static RelatorioAreaSegurancaTOBuilder getInstance() {
		return new RelatorioAreaSegurancaTOBuilder();
	}

	public RelatorioAreaSegurancaTOBuilder problemasArea(Map<String, Integer> problemasArea) {
		this.problemasArea = problemasArea;
		return this;
	}

	public RelatorioAreaSegurancaTOBuilder chamadasEmitidasFuncionario(List<ChamadasEmitidasFuncionarioTO> chamadasEmitidasFuncionario) {
		this.chamadasEmitidasFuncionario = chamadasEmitidasFuncionario;
		return this;
	}

	public RelatorioAreaSegurancaTOBuilder solucionadorAlertasSeguranca(Map<String, Integer> solucionadorAlertasSeguranca) {
		this.solucionadorAlertasSeguranca = solucionadorAlertasSeguranca;
		return this;
	}
	
	public RelatorioAreaSegurancaTO build() {
		RelatorioAreaSegurancaTO relatorioArea = new RelatorioAreaSegurancaTO();
		relatorioArea.setChamadasEmitidasFuncionario(chamadasEmitidasFuncionario);
		relatorioArea.setProblemasArea(problemasArea);
		relatorioArea.setSolucionadorAlertasSeguranca(solucionadorAlertasSeguranca);
		return relatorioArea;
	}
	
	

}
