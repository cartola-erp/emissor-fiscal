package net.cartola.emissorfiscal.sped.fiscal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import net.cartola.emissorfiscal.contador.Contador;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.operacao.Operacao;
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
public class MovimentoMensalIcmsIpi {
	
//	private List<DocumentoFiscal> listCompras;
	private List<DocumentoFiscal> listDocumentoFiscal;
	private List<DocumentoFiscal> listDocumentoFiscalServico;
	private List<DocumentoFiscal> listSatsEmitidos; 		// DocumentoFiscal - Modelo _59
	private List<Pessoa> listCadastros;
	private List<PessoaAlteradoSped> listCadastrosAlteradosSped;
	
	private Set<DocumentoFiscalItem> listItens;
	private List<ProdutoAlteradoSped> listProdutoAlteradoSped;
	private List<ProdutoUnidade> listProdUnid;
	private Collection<Operacao> listOperacoes;
	
	private Loja loja;
//	private Pessoa loja;			// CONFESSO que ainda tou na duvida se pego a loja de "CADASTROS" ou da tbl "LOJA" msm
	private Contador contador;
	
	private List<CodificacaoReg0450InfoComplementarFisco> listCodInfoComplementarFisco;
	
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
	
	
	public void setListProdutoAlteradoSped(List<ProdutoAlteradoSped> listProdutoAlterado) { 
		if ((this.listProdutoAlteradoSped == null || this.listProdutoAlteradoSped.isEmpty()) && listProdutoAlterado == null) {
			this.listProdutoAlteradoSped = new ArrayList<>();
		} else {
			this.listProdutoAlteradoSped = listProdutoAlterado;
		}
	}
	
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

	public List<CodificacaoReg0450InfoComplementarFisco> getListCodInfoComplementarFisco() {
		if (this.listCodInfoComplementarFisco == null || this.listCodInfoComplementarFisco.isEmpty()) {
			this.listCodInfoComplementarFisco = new ArrayList<>();
			CodificacaoReg0450InfoComplementarFisco codInfoComplementarFisco = new CodificacaoReg0450InfoComplementarFisco();
			
			codInfoComplementarFisco.setId(1L);
			codInfoComplementarFisco.setCodInfo("01");
			codInfoComplementarFisco.setDescricao("INFORMACOES COMPLEMENTARES DE INTERESSE DO FISCO");
			listCodInfoComplementarFisco.add(codInfoComplementarFisco);
		}
		return listCodInfoComplementarFisco;
	}

	public void setListCodInfoComplementarFisco(List<CodificacaoReg0450InfoComplementarFisco> listCodInfoComplementarFisco) {
		this.listCodInfoComplementarFisco = listCodInfoComplementarFisco;
	}
	
	
}
