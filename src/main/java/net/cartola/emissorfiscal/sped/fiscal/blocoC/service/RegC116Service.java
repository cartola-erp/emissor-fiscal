package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getIndicadorEmitente;
import static net.cartola.emissorfiscal.util.XmlUtil.getTagConteudo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC116;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

/**
 * @autor robson.costa
 * @data 14 de abr. de 2021
 */
@Service
class RegC116Service {

	private static final Logger LOG = Logger.getLogger(RegC116Service.class.getName());

	
	/**
	 * REGISTRO C116: CUPOM FISCAL ELETRÔNICO REFERENCIADO
	 * 
	 * @param docFisc
	 * @param lojaSped 
	 * @param listDocFiscReferenciados 
	 * @return
	 */
	public List<RegC116> montarGrupoRegC116(DocumentoFiscal docFisc, Loja lojaSped, List<DocumentoFiscal> listDocFiscReferenciados) {
//		LOG.log(Level.INFO, "Montando o REGISTRO C116" );
		List<RegC116> listRegC116 = new ArrayList<>();
		
		for (DocumentoFiscal docFiscReferenciado : listDocFiscReferenciados) {
			LOG.log(Level.INFO, "Montando o REGISTRO C116" );
			boolean isEmissaoPropria = getIndicadorEmitente(docFiscReferenciado, lojaSped).equals(IndicadorDoEmitente.EMISSAO_PROPRIA);
			if (docFiscReferenciado.getModelo().equals(ModeloDocumentoFiscal._59) && isEmissaoPropria) {
				RegC116 regC116 = new RegC116();
				
				regC116.setCodMod(docFiscReferenciado.getModelo());
				regC116.setNrSat(getTagConteudo(docFiscReferenciado.getXml(), "nserieSAT", false).get(0));
				regC116.setChvCfe(docFiscReferenciado.getNfeChaveAcesso());
				regC116.setNumCfe(docFiscReferenciado.getNumeroNota());
				regC116.setDtDoc(docFiscReferenciado.getEmissao());
				listRegC116.add(regC116);
			}
			LOG.log(Level.INFO, "Saindo da montagem do REGISTRO C116" );
		}
//		LOG.log(Level.INFO, "Saindo da montagem do REGISTRO C116" );
		return listRegC116;
	}

}
