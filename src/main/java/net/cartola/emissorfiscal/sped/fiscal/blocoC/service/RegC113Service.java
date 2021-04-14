package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCodPart;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getIndicadorEmitente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC113;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

/**
 * @autor robson.costa
 * @data 14 de abr. de 2021
 */
@Service
class RegC113Service {

	private static final Logger LOG = Logger.getLogger(RegC113Service.class.getName());

	
	public List<RegC113> montarGrupoRegC113(DocumentoFiscal docFisc, Loja lojaSped, List<DocumentoFiscal> listDocFiscReferenciados) {
		LOG.log(Level.INFO, "Montando o REGISTRO C113" );
		List<RegC113> listRegC113 = new ArrayList<>();
		
		List<ModeloDocumentoFiscal> listCupons = Arrays.asList(ModeloDocumentoFiscal._2, ModeloDocumentoFiscal._2D, ModeloDocumentoFiscal._2E);
		
		listDocFiscReferenciados.stream().forEach(docFiscReferenciado -> {
			if (!listCupons.contains(docFiscReferenciado.getModelo())) {
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
				listRegC113.add(regC113);
			}
		});
		
		LOG.log(Level.INFO, "Saindo do REGISTRO C113" );
		return listRegC113;
	}

}
