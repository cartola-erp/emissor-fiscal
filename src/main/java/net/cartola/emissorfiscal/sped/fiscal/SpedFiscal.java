package net.cartola.emissorfiscal.sped.fiscal;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Bloco0;
import net.cartola.emissorfiscal.sped.fiscal.bloco1.Bloco1;
import net.cartola.emissorfiscal.sped.fiscal.bloco9.Bloco9;
import net.cartola.emissorfiscal.sped.fiscal.blocoB.BlocoB;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.BlocoC;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.BlocoD;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.BlocoE;
import net.cartola.emissorfiscal.sped.fiscal.blocoG.BlocoG;
import net.cartola.emissorfiscal.sped.fiscal.blocoH.BlocoH;
import net.cartola.emissorfiscal.sped.fiscal.blocoK.BlocoK;

/**
 * 20/08/2020
 * @author robson.costa
 *
 * Bloco 0 - Abertura, Identificação e Referências
 * Bloco B - Escrituração e Apuração do ISS
 * Bloco C - Documentos Fiscais I - Mercadorias (ICMS/IPI)
 * Bloco D - Documentos Fiscais II - Serviços (ICMS)
 * Bloco E - Apuração do ICMS e do IPI
 * Bloco G - Controle do Crédito de ICMS do Ativo Permanente - CIAP
 * Bloco H - Inventário Físico
 * Bloco K - Controle da Produção e do Estoque
 * Bloco 1 - Outras Informações
 * Bloco 9 - Controle e Encerramento do Arquivo Digital 
 *
 */
@Record(fields = {
		@Field(name = "bloco0"),
		@Field(name = "blocoB"),
		@Field(name = "blocoC"),
		@Field(name = "blocoD"),
		@Field(name = "blocoE"),
		@Field(name = "blocoG"),
		@Field(name = "blocoH"),
		@Field(name = "blocoK"),
		@Field(name = "bloco1"),
		@Field(name = "bloco9")
})
public class SpedFiscal {
	
	private Bloco0 bloco0;
	private BlocoB blocoB;
	private BlocoC blocoC;
	private BlocoD blocoD;
	private BlocoE blocoE;
	private BlocoG blocoG;
	private BlocoH blocoH;
	private BlocoK blocoK;
	private Bloco1 bloco1;
	private Bloco9 bloco9;
	
	public Bloco0 getBloco0() {
		return bloco0;
	}
	
	public void setBloco0(Bloco0 bloco0) {
		this.bloco0 = bloco0;
	}

	public BlocoB getBlocoB() {
		return blocoB;
	}

	public void setBlocoB(BlocoB blocoB) {
		this.blocoB = blocoB;
	}

	public BlocoC getBlocoC() {
		return blocoC;
	}

	public void setBlocoC(BlocoC blocoC) {
		this.blocoC = blocoC;
	}

	public BlocoD getBlocoD() {
		return blocoD;
	}

	public void setBlocoD(BlocoD blocoD) {
		this.blocoD = blocoD;
	}

	public BlocoE getBlocoE() {
		return blocoE;
	}

	public void setBlocoE(BlocoE blocoE) {
		this.blocoE = blocoE;
	}

	public BlocoG getBlocoG() {
		return blocoG;
	}

	public void setBlocoG(BlocoG blocoG) {
		this.blocoG = blocoG;
	}

	public BlocoH getBlocoH() {
		return blocoH;
	}

	public void setBlocoH(BlocoH blocoH) {
		this.blocoH = blocoH;
	}

	public BlocoK getBlocoK() {
		return blocoK;
	}

	public void setBlocoK(BlocoK blocoK) {
		this.blocoK = blocoK;
	}

	public Bloco1 getBloco1() {
		return bloco1;
	}

	public void setBloco1(Bloco1 bloco1) {
		this.bloco1 = bloco1;
	}

	public Bloco9 getBloco9() {
		return bloco9;
	}

	public void setBloco9(Bloco9 bloco9) {
		this.bloco9 = bloco9;
	}

}
