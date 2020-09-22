package net.cartola.emissorfiscal.sped.fiscal;

import java.time.LocalDate;

/**
 * 21/09/2020
 * @author robson.costa
 */
public class MovimentacoesMensalIcmsIpi {
	
	private LocalDate dataInicio;
	private LocalDate dataFim;
	
	/***
	 * 
	 * Essa classe deverá encapsular os objetos, que tem no ERP (banco autogeral)
	 * Pois será a responsavel de receber o JSON no Controller, e gerar o ARQUIVO do SPED FISCAL
	 * Caso eu pensei em um nome melhor, basta eu mudar aqui, já que criei apenas para colocar nos
	 * generics das interface que irei usar nas Services
	 * 
	 * PS: Tbm tenho que ver se esse é o melhor pacote para ficar essa model ( @MovimentacoesMensalIcmsIpi)
	 */
	
	
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	

	
}
