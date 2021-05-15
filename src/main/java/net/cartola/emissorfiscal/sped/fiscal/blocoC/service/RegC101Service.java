package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC101;

/**
 * REGISTRO C101: INFORMAÇÃO COMPLEMENTAR DOS DOCUMENTOS FISCAIS QUANDO DAS
 * OPERAÇÕES INTERESTADUAIS DESTINADAS A CONSUMIDOR FINAL NÃO CONTRIBUINTE EC
 * 87/15 (CÓDIGO 55)
 * 
 * @data 14 de mai. de 2021
 * @author robson.costa
 */
@Service
//class RegC101Service implements MontaGrupoRegistroSimples<RegC101, MovimentoMensalIcmsIpi> {
class RegC101Service {
	
	private static final Logger LOG = Logger.getLogger(RegC101Service.class.getName());

	
	/**
	 * TODO AQUI IREI adicionar o DOCUMENTO FISCAL NUMA LISTA, no obj movimentosIcmsIpi, que Servirá para escriturar o REGE300
	 * @param movimentosIcmsIpi
	 * @param docFisc
	 * @return
	 */
	public RegC101 montarRegC101(MovimentoMensalIcmsIpi movimentosIcmsIpi, DocumentoFiscal docFisc) {
		LOG.log(Level.INFO, "Montando o Registro C101 ");
		RegC101 regC101 = new RegC101();
		regC101.setVlFcpUfDest(docFisc.getIcmsFcpValor());
		regC101.setVlIcmsUfDest(docFisc.getIcmsValorUfDestino());
		regC101.setVlIcmsUfRem(BigDecimal.ZERO);
		LOG.log(Level.INFO, "Registro E100, terminado. REG C101: ");
		return regC101;
	}
	
	
	QUALQUER COISA ERRADA apenas para eu lembrar de abrir essa classe
	e ler o TODO
	
}
