package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC100;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.util.SpedFiscalUtil;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class RegC100Service implements MontaGrupoDeRegistroList<RegC100, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegC100Service.class.getName());

	
	@Autowired
	private RegC110Service regC110Service;
	
	@Autowired
	private RegC120Service regC120Service;
	
	@Autowired
	private RegC160Service regC160Service;
	
	@Autowired
	private RegC165Service regC165Service;
	
	@Autowired
	private RegC170Service regC170Service;
	
//	@Autowired
//	private RegC170Service regC170Service;
	
	@Autowired
	private RegC190Service regC190Service;

	@Autowired
	private RegC195Service regC195Service;	
	
	@Autowired
	private RegC197Service regC197Service;
	
//	@Autowired
//	private RegC190Service regC190Service;
	
	@Override
	public List<RegC100> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro C100");
		
		Loja lojaSped = movimentosIcmsIpi.getLoja();
		List<DocumentoFiscal> listDocFiscalEntrada = getDocFiscalEntrada(movimentosIcmsIpi);
		List<DocumentoFiscal> listDocFiscalSaida = getDocFiscalSaida(movimentosIcmsIpi);
		
		List<RegC100> listRegC100 = new ArrayList<>();
//		RegC100 regC100 = new RegC100();
		listDocFiscalEntrada.stream().forEach(docFiscEntrada -> {
			listRegC100.add(preecheRegC100(docFiscEntrada, lojaSped));
		});
		
		LOG.log(Level.INFO, "Registro C100, terminado. REG C100: {0} " ,listRegC100);

		return listRegC100;
	}


	
	
	private RegC100 preecheRegC100(DocumentoFiscal docFisc, Loja lojaSped) {
//		LOG.log(Level.INFO, "Montando o Registro C100");
		// TODO Auto-generated method stub
		RegC100 regC100 = new RegC100();
		IndicadorDeOperacao tipoOperacao = docFisc.getTipoOperacao();
		
		regC100.setIndOper(tipoOperacao);
		regC100.setIndEmit(getIndicadorEmitente(docFisc, lojaSped));
		regC100.setCodPart(getCodPart(docFisc));
		
		return null;
	}



	/**
	 * 
	 * @param movimentosIcmsIpi
	 * @return List<DocumentoFiscal> - Lista de Todos DocumentosFiscais, que devem ser escriturados na Entrada
	 */
	private List<DocumentoFiscal> getDocFiscalEntrada(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		List<DocumentoFiscal> listDocumentoFiscal = movimentosIcmsIpi.getListDocumentoFiscal();
		
		List<DocumentoFiscal> listDocFiscEntrada = listDocumentoFiscal.stream()
			.filter(docFisc -> docFisc.getTipoOperacao() == IndicadorDeOperacao.ENTRADA && getModelosDocFiscRegC100().contains(docFisc.getModelo()))
			.collect(toList());
		
		return listDocFiscEntrada;
	}

	
	/**
	 * 
	 * @param movimentosIcmsIpi
	 * @return List<DocumentoFiscal> - Lista de Todos DocumentosFiscais, que devem ser escriturados na Saída
	 */
	private List<DocumentoFiscal> getDocFiscalSaida(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		List<DocumentoFiscal> listDocumentoFiscal = movimentosIcmsIpi.getListDocumentoFiscal();
		List<ModeloDocumentoFiscal> modelosDocFisc = getModelosDocFiscRegC100();
		modelosDocFisc.add(ModeloDocumentoFiscal._65);
		
		List<DocumentoFiscal> listDocFiscSaida = listDocumentoFiscal.stream()
				.filter(docFisc -> docFisc.getTipoOperacao() == IndicadorDeOperacao.SAIDA && modelosDocFisc.contains(docFisc.getModelo()))
				.collect(toList());
		
		return listDocFiscSaida;
	}

	
	/**
	 * Obtem Todos os Modelos de Documentos Fiscais de ENTRADA e SAIDA, que devem ser escriturados na REGISTRO C100.
	 * 
	 * PS: Modelo 65 -> Somente deve ser escriturado na saída, por isso não foi adicionado aqui
	 * 
	 * @return List<ModeloDocumentoFiscal> 
	 */
	private List<ModeloDocumentoFiscal> getModelosDocFiscRegC100() {
		List<ModeloDocumentoFiscal> listModeloDocFisc = new ArrayList<>();
		
		listModeloDocFisc.add(ModeloDocumentoFiscal._1);
		listModeloDocFisc.add(ModeloDocumentoFiscal._1B);
		listModeloDocFisc.add(ModeloDocumentoFiscal._4);
		listModeloDocFisc.add(ModeloDocumentoFiscal._55);
		
		return listModeloDocFisc;
	}
	
	
	private IndicadorDoEmitente getIndicadorEmitente(DocumentoFiscal docFisc, Loja lojaSped) {
		LOG.log(Level.INFO, "Obtendo o indicador do emitente para o DocumentoFiscal {0} " ,docFisc);

		Pessoa emitente = docFisc.getEmitente();
		if (emitente.getCnpj().equals(lojaSped.getCnpj())) {
			return IndicadorDoEmitente.EMISSAO_PROPRIA;
		}
		return IndicadorDoEmitente.TERCEIROS;
	}
	
	/**
	 * Obtem o código do participante para o <b>destinatário</b> se for operacao de <b>Entrada</b>
	 * <b>Saída</b> codigo particapante do Emitente
	 * 
	 * Caso o Modelo documento seja == 65 (NFC-e), retorna String <b>Vazia</b>
	 * @param docFisc
	 * @return
	 */
	private String getCodPart(DocumentoFiscal docFisc) {
		LOG.log(Level.INFO, "Obtendo o CODIGO DO PARTICIPANTE para o DocumentoFiscal {0} " ,docFisc);
		if (docFisc.getModelo() != ModeloDocumentoFiscal._65) {
			if (docFisc.getTipoOperacao() == IndicadorDeOperacao.ENTRADA) {
				return SpedFiscalUtil.getCodPart(docFisc.getDestinatario());
			}
			return SpedFiscalUtil.getCodPart(docFisc.getEmitente());
		}
		return "";
	}

		
}
