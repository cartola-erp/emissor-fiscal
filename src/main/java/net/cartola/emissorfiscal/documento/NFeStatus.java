package net.cartola.emissorfiscal.documento;

import net.cartola.emissorfiscal.sped.fiscal.enums.EnumCodificado;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;

/**
 * @see SituacaoDoDocumento
 * @date 12 de jan. de 2021
 * @author robson.costa
 */
public enum NFeStatus implements EnumCodificado {
    
	/*OBS: Os que são "INEXISTENTE", entendi que são apenas para "regra" de negócio no ERP */
	
	DIGITACAO("Codigo Sped Inexistente"), 
	VALIDADA("Codigo Sped Inexistente"), 
	ASSINADA("Codigo Sped Inexistente"), 
	PROCESSAMENTO ("Codigo Sped Inexistente"), 
	AUTORIZADA("00"), 
	CANCELADA("02"), 
	INUTILIZADA("05"), 
	DENEGADA("04");
	
	private final String codigoSped;

	private NFeStatus(String codigoSped) {
		this.codigoSped = codigoSped;
	}
	
	@Override
	public String getCodigo() {
		return this.codigoSped;
	}
	
}
