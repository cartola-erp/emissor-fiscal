package net.cartola.emissorfiscal.documento;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

/**
 * Model que representa os campos referentes a uma chave de acesso.
 * Atualmente o uso é unico e exclusivamente para "extrair" algumas informações de algumas chaves de acessos, que temos em alguns,
 * XMLs de entrada, e é preciso informar no SPED;
 * 
 * @autor robson.costa
 * @data 15 de abr. de 2021
 */
@ToString
@Getter
@Setter
public class ChaveAcesso {
	
	private EstadoSigla ufEmitente;
	private String anoEmissao;		// AA
	private String mesEmissao;		// MM
	private String cnpjEmitente;
	private ModeloDocumentoFiscal modeloDocumento;
	private String serie;
//	private Long numeroNota;
	private String numeroNota;
	private FinalidadeEmissao tipoEmissao;
	private String codigoNfe; 	//cNF
	private String digitoVerificador;	//cDV
	
	
	/**
	 * Dado uma CHAVE de acesso, será criado um objeto preenchido com as informações, referente a chave da nfe.
	 * 
	 * @param chaveRefXml
	 */
	public ChaveAcesso(String chaveAcesso) {
		if(chaveAcesso == null || chaveAcesso.length() != 44) {
			throw new IllegalArgumentException("Formato da Chave de acesso passada é inválida: " +chaveAcesso);
		}
		
		String codigoIbgeUf = chaveAcesso.substring(0, 2);
		setUfEmitente(codigoIbgeUf);
	
		this.anoEmissao = chaveAcesso.substring(2, 4);
		this.mesEmissao = chaveAcesso.substring(4, 6);
		this.cnpjEmitente = chaveAcesso.substring(6, 20);
		
		String numModeloDocFisc = chaveAcesso.substring(20, 22);
		setModeloDocumento(numModeloDocFisc);
		
		this.serie = chaveAcesso.substring(22, 25);
		this.numeroNota = chaveAcesso.substring(26, 34);
//		this.tipoEmissao = chaveAcesso.substring(34, 35);
		
		String tipoFinalidadeEmissao = chaveAcesso.substring(34, 35);
		setTipoEmissao(tipoFinalidadeEmissao);
		
		this.codigoNfe = chaveAcesso.substring(35, 43);
		this.digitoVerificador = chaveAcesso.substring(43, 44);
	}


	private void setUfEmitente(String codigoIbgeUf) {
		for (EstadoSigla estadoSigla : EstadoSigla.values()) {
			if (estadoSigla.getCodigoIbge() == Integer.parseInt(codigoIbgeUf)) {
				this.ufEmitente = estadoSigla;
			}
		}		
	}


	private void setModeloDocumento(String numModeloDocFisc) {
		for (ModeloDocumentoFiscal modeloDocFisc : ModeloDocumentoFiscal.values()) {
			if (modeloDocFisc.getCodigo().equals(numModeloDocFisc)) {
				this.modeloDocumento = modeloDocFisc;
			}
		}
	}
	
	private void setTipoEmissao(String tipoFinalidadeEmissao) {
		for (FinalidadeEmissao finalidadeEmissao : FinalidadeEmissao.values()) {
			if (finalidadeEmissao.getCodigo().equals(tipoFinalidadeEmissao)) {
				this.tipoEmissao = finalidadeEmissao;
			}
		}		
	}
	
}
