package net.cartola.emissorfiscal.model;

import java.math.BigDecimal;

import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;

/**
 * 14 de nov de 2019
 * 
 * @author gregory.feijon
 */

public class TributacaoFederalBuilder {

	private TributacaoFederal tributacaoFederal = new TributacaoFederal();

	public void novo() {
		this.tributacaoFederal = new TributacaoFederal();
	}

	public TributacaoFederal build() {
		return tributacaoFederal;
	}

	public TributacaoFederalBuilder withOperacao(Operacao operacao) {
		tributacaoFederal.setOperacao(operacao);
		return this;
	}
	
	public TributacaoFederalBuilder withFinalidadeo(Finalidade finalidade) {
		tributacaoFederal.setFinalidade(finalidade);
		return this;
	}
	
	public TributacaoFederalBuilder withRegimeTributario(RegimeTributario regimeTributario) {
		tributacaoFederal.setRegimeTributario(regimeTributario);
		return this;
	}

	public TributacaoFederalBuilder withNcm(Ncm ncm) {
		tributacaoFederal.setNcm(ncm);
		return this;
	}

	public TributacaoFederalBuilder withPisCst(int pisCst) {
		tributacaoFederal.setPisCst(pisCst);
		return this;
	}

	public TributacaoFederalBuilder withPisBase(BigDecimal pisBase) {
		tributacaoFederal.setPisBase(pisBase);
		return this;
	}

	public TributacaoFederalBuilder withPisAliquota(BigDecimal pisAliquota) {
		tributacaoFederal.setPisAliquota(pisAliquota);
		return this;
	}

	public TributacaoFederalBuilder withCofinsCst(int cofinsCst) {
		tributacaoFederal.setCofinsCst(cofinsCst);
		return this;
	}

	public TributacaoFederalBuilder withCofinsBase(BigDecimal cofinsBase) {
		tributacaoFederal.setCofinsBase(cofinsBase);
		return this;
	}

	public TributacaoFederalBuilder withCofinsAliquota(BigDecimal cofinsAliquota) {
		tributacaoFederal.setCofinsAliquota(cofinsAliquota);
		return this;
	}

	public TributacaoFederalBuilder withIpiCst(int ipiCst) {
		tributacaoFederal.setIpiCst(ipiCst);
		return this;
	}

	public TributacaoFederalBuilder withIpiBase(BigDecimal ipiBase) {
		tributacaoFederal.setIpiBase(ipiBase);
		return this;
	}

	public TributacaoFederalBuilder withIpiAliquota(BigDecimal ipiAliquota) {
		tributacaoFederal.setIpiAliquota(ipiAliquota);
		return this;
	}

	public TributacaoFederalBuilder withMensagem(String mensagem) {
		tributacaoFederal.setMensagem(mensagem);
		return this;
	}
}
