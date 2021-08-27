package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCodPart;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getIndicadorEmitente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.ChaveAcesso;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC113;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.util.XmlUtil;

/**
 * REGISTRO C113: DOCUMENTO FISCAL REFERENCIADO
 * 
 * @autor robson.costa
 * @data 14 de abr. de 2021
 */
@Service
class RegC113Service {

	private static final Logger LOG = Logger.getLogger(RegC113Service.class.getName());

	/**
	 * Informa "outros DocumentoFiscais", que tenham sido mencionados em Info. Compl. Do Atual Documento que está sendo escriturado
	 * No REG C100, EXCETO cupons fiscais, que deveram ser informados no C114.
	 * 
	 * @param docFisc
	 * @param lojaSped
	 * @param listDocFiscReferenciados
	 * @return
	 */
	public List<RegC113> montarGrupoRegC113(DocumentoFiscal docFisc, Loja lojaSped, List<DocumentoFiscal> listDocFiscReferenciados) {
//		LOG.log(Level.INFO, "Montando o REGISTRO C113" );
		List<RegC113> listRegC113 = new ArrayList<>();
		List<ModeloDocumentoFiscal> listCupons = Arrays.asList(ModeloDocumentoFiscal._2, ModeloDocumentoFiscal._2D, ModeloDocumentoFiscal._2E);
		
		// Se a Lista de Referencias DocumentoFiscal, estiver vazia, tenta preencher com base nas chaves de referencia que estejam no XML (caso tenha)
		if (listDocFiscReferenciados.isEmpty()) {
			List<String> listChavesRefNoXml = XmlUtil.getTagConteudo(docFisc.getXml(), "refNFe", false);
			
			listChavesRefNoXml.forEach(chaveRefXml -> {
				// Extrai as informações da chave de acesso;
				ChaveAcesso chaveAcesso = new ChaveAcesso(chaveRefXml);
				if (!listCupons.contains(chaveAcesso.getModeloDocumento())) {
					RegC113 regC113 = preencherRegC113PelaChaveAcesso(docFisc, lojaSped, chaveRefXml, chaveAcesso);
					listRegC113.add(regC113);
				}
			});
			LOG.log(Level.INFO, "Saindo do REGISTRO C113" );
			return listRegC113;
		}
		
		listDocFiscReferenciados.stream().forEach(docFiscReferenciado -> {
			if (!listCupons.contains(docFiscReferenciado.getModelo())) {
				RegC113 regC113 =preencherRegC113PeloDocumentoFiscalReferenciado(docFisc, lojaSped, docFiscReferenciado);
				listRegC113.add(regC113);
			}
		});
		
//		LOG.log(Level.INFO, "Saindo do REGISTRO C113" );
		return listRegC113;
	}

	
	/**
	 * Usado quando não tem nada em DocumentoFiscalReferencia, porém tem CHAVE de REFERENCIA no XML;
	 * Sendo irá preencher o C113, com base nessa chave do XML
	 * @param docFisc
	 * @param lojaSped
	 * @param chaveRefXml
	 * @param chaveAcesso
	 * @return
	 */
	private RegC113 preencherRegC113PelaChaveAcesso(DocumentoFiscal docFisc, Loja lojaSped, String chaveRefXml, ChaveAcesso chaveAcesso) {
		RegC113 regC113 = new RegC113();
		regC113.setIndOper(docFisc.getTipoOperacao());
		regC113.setIndEmit(getIndicadorEmitente(docFisc, lojaSped));
		regC113.setCodPart(getCodPart(docFisc));
		regC113.setCodMod(chaveAcesso.getModeloDocumento());
		regC113.setSer(Long.parseLong(chaveAcesso.getSerie()));
		regC113.setSub(null);				//	
		regC113.setNumDoc(Long.parseLong(chaveAcesso.getNumeroNota()));
		regC113.setDtDoc(LocalDate.of(Integer.parseInt(chaveAcesso.getAnoEmissao()), Integer.parseInt(chaveAcesso.getMesEmissao()), 1));
		regC113.setChvDocE(chaveRefXml);
		return regC113;
	}

	
	/**
	 * Usado quando tem tem algum DocumentoFiscal de referencia
	 * @param docFisc
	 * @param lojaSped
	 * @param docFiscReferenciado
	 * @return
	 */
	private RegC113 preencherRegC113PeloDocumentoFiscalReferenciado(DocumentoFiscal docFisc, Loja lojaSped, DocumentoFiscal docFiscReferenciado) {
		RegC113 regC113 = new RegC113();
		regC113.setIndOper(docFiscReferenciado.getTipoOperacao());
		regC113.setIndEmit(getIndicadorEmitente(docFisc, lojaSped));
		regC113.setCodPart(getCodPart(docFiscReferenciado));
		regC113.setCodMod(docFiscReferenciado.getModelo());
		regC113.setSer(docFiscReferenciado.getSerie());
		regC113.setSub(null);
		regC113.setNumDoc(docFiscReferenciado.getNumeroNota());
		regC113.setDtDoc(docFiscReferenciado.getEmissao());
		regC113.setChvDocE(docFiscReferenciado.getNfeChaveAcesso());
		return regC113;
	}

}
