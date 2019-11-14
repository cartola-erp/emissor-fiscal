package net.cartola.emissorfiscal.emissorfiscal.model;

import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
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

	public TributacaoFederalBuilder withNcm(Ncm ncm) {
		tributacaoFederal.setNcm(ncm);
		return this;
	}

	public TributacaoFederalBuilder withPisCst(int pisCst) {
		tributacaoFederal.setPisCst(pisCst);
		return this;
	}

	public TributacaoFederalBuilder withCofinsCst(int cofinsCst) {
		tributacaoFederal.setCofinsCst(cofinsCst);
		return this;
	}

	public TributacaoFederalBuilder withIpiCst(int ipiCst) {
		tributacaoFederal.setIpiCst(ipiCst);
		return this;
	}
}
