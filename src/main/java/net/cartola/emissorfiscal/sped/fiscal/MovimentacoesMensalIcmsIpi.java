package net.cartola.emissorfiscal.sped.fiscal;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.cartola.emissorfiscal.contador.Contador;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaAlteradoSped;
import net.cartola.emissorfiscal.produto.ProdutoAlteradoSped;
import net.cartola.emissorfiscal.produto.ProdutoUnidade;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Getter
@Setter
public class MovimentacoesMensalIcmsIpi {
	
//	private List<DocumentoFiscal> listCompras;
	private List<DocumentoFiscal> listDocumentoFiscal;
	private List<Pessoa> listCadastros;
	private List<PessoaAlteradoSped> listCadastrosAlteradosSped;
	
	private List<ProdutoAlteradoSped> listProdutoAlteradoSped;
//	private List<ProdutoUnidade> listProdutoUnidade;
	private Loja loja;
//	private Pessoa loja;			// CONFESSO que ainda tou na duvida se pego a loja de "CADASTROS" ou da tbl "LOJA" msm
	private Contador contador;
	
	
	private LocalDate dataInicio;
	private LocalDate dataFim;
	
	/***
	 * 
	 * Essa classe deverá encapsular os objetos, que tem no ERP (banco autogeral)
	 * Pois será a responsavel de receber o JSON no Controller, e gerar o ARQUIVO do SPED FISCAL
	 * Caso eu pense em um nome melhor, basta eu mudar aqui, já que criei apenas para colocar nos
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
