package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static java.util.stream.Collectors.toSet;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.isNfeReferenteASat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalReferencia;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.CodificacaoReg0450InfoComplementarFisco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC110;

@Service
class RegC110Service {

	private static final Logger LOG = Logger.getLogger(RegC110Service.class.getName());
	
	@Autowired
	private RegC113Service regC113Service;
	
	@Autowired
	private RegC116Service regC116Service;
	
	@Autowired
	private DocumentoFiscalService docFiscService;
	
	public List<RegC110> montarGrupoRegC110(DocumentoFiscal docFisc, Loja lojaSped, MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o REGISTRO C110" );
		List<RegC110> listRegC110 = new ArrayList<>();
		Set<String> setChaveAcessoReferencia = docFisc.getReferencias().stream().map(DocumentoFiscalReferencia::getChave).collect(toSet());
		List<DocumentoFiscal> listDocFiscReferenciados = docFiscService.findByNfeChaveAcessoIn(setChaveAcessoReferencia);
		CodificacaoReg0450InfoComplementarFisco codificaocaReg0450InfoComplFisco = movimentosIcmsIpi.getListCodInfoComplementarFisco().get(0);
		
		RegC110 regC110 = new RegC110();
		regC110.setCodInf(codificaocaReg0450InfoComplFisco.getCodInfo());
		regC110.setTxtCompl(docFisc.getInfoAdicionalFisco());
		
		// ==========================================  PREENCHENDO os REGISTORS filhos ===================================================
//		REGISTRO C112: DOCUMENTO DE ARRECADAÇÃO REFERENCIADO
		
		// Preenchendo o REG C113
//		if (docFisc.getOperacao().isDevolucao()) {
			regC110.setRegC113(regC113Service.montarGrupoRegC113(docFisc, lojaSped, listDocFiscReferenciados));
//		}	
		// Preenchendo o REG C114 (na verdade NÃO estou preenchendo no momento, acho que nunca será preciso, já que aparenemente é referente a uns modelos de notas mais velho que eu)
//		{ regC110.setRegC114(regC114Service.montarGrupoRegC114(docFisc, lojaSped, listDocFiscReferenciados)); }
		
		// REG C115 - LOCAL DA COLETA E/OU ENTREGA (CÓDIGO 01, 1B E 04) (aparentemente não será usado tbm, pois são Modelos de Documento bem antigos)
		
		// Se o DocFisc -> For uma Nfe de uma venda feita por SAT anteriormente, irá escriturar a o SAT referenciado na NFE no REG C116
		if (isNfeReferenteASat(docFisc)) {
			regC110.setRegC116(regC116Service.montarGrupoRegC116(docFisc, lojaSped, listDocFiscReferenciados));
		}

		listRegC110.add(regC110);
		LOG.log(Level.INFO, "Saindo da montagem do REGISTRO C110" );
		return listRegC110;
	}

	
	
	
}
