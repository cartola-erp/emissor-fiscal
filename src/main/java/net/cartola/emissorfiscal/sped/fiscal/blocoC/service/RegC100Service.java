package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.documento.NFeStatus;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC100;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;
import net.cartola.emissorfiscal.util.NumberUtilRegC100;
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

	
	@Override
	public List<RegC100> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro C100");
		
		Loja lojaSped = movimentosIcmsIpi.getLoja();
		List<DocumentoFiscal> listDocFiscalEntrada = getDocFiscalEntrada(movimentosIcmsIpi);
		List<DocumentoFiscal> listDocFiscalSaida = getDocFiscalSaida(movimentosIcmsIpi);
		
		List<RegC100> listRegC100 = new ArrayList<>();

		listDocFiscalEntrada.stream().forEach(docFiscEntrada -> {
			listRegC100.add(preecheRegC100Entrada(docFiscEntrada, lojaSped));
		});
		
		
		listDocFiscalSaida.stream().forEach(docFiscSaida -> {
			listRegC100.add(preencheRegC100Saida(docFiscSaida, lojaSped));
		});
		
		LOG.log(Level.INFO, "Registro C100, terminado. REG C100: {0} " ,listRegC100);
		return listRegC100;
	}


	
	/**
	 * Irá escriturar todas NFES de entrada, conforme as regras do Guia de Preenchimento. 
	 * (CASO NÃO se encaixe em nenhuma das DEZ EXCECOES do GUIA, a NFE será escriturada NORMAL: REG C100, C170 e C190)
	 * 
	 * @param docFisc
	 * @param lojaSped
	 * @return
	 */
	private RegC100 preecheRegC100Entrada(DocumentoFiscal docFisc, Loja lojaSped) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro C100, para as Entradas");
		RegC100 regC100Entrada = new RegC100();
		
		/** PS: Por enquanto só tem a validação do PRIMEIRO preenchimento **/
		TipoPreenchimentoRegC100 tipoPreenchimentoRegC100 = verificaTipoPreenchimento(docFisc, lojaSped);
		
		switch (tipoPreenchimentoRegC100) {
		case EX_1_COD_SITUACAO:
			regC100Entrada = prencheC100ExcecaoUmCodSit(docFisc, lojaSped);
			break;
		case EX_2_NFE_EMISSAO_PROPRIA:
			regC100Entrada = preencheC100ExcecaoDoisEmissaoPropria(docFisc, lojaSped);
			break;

		case EX_3_NFE_COMPLEMENTAR:
			
			break;
		default:
			/** Se não for nenhum dos casos acima en tão é o preenchimento normal **/
			regC100Entrada = preencheC100(docFisc, lojaSped);
			regC100Entrada.setRegC170(regC170Service.montarGrupoRegC170(docFisc));
			regC100Entrada.setRegC190(regC190Service.montarGrupoRegC190(docFisc));
			regC100Entrada.setRegC195(regC195Service.montarGrupoRegC195(docFisc));
			break;
		}
			
		
		return regC100Entrada;
	}

	
	private RegC100 preencheRegC100Saida(DocumentoFiscal docFiscSaida, Loja lojaSped) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro C100, para as Saídas");

		RegC100 regC100Saida = new RegC100();
		
		regC100Saida = preencheC100(docFiscSaida, lojaSped);
		
		return regC100Saida;
	}
	
	
	/**
	 * Preenchimento do REGISTRO C100 (Seja Entrada ou Saída)
	 * OBS: É Apenas do REG C100.  
	 * Os FILHOS NÃO estão sendo preenchidos nesse método
	 * 
	 * @param docFisc
	 * @param lojaSped
	 * @return
	 */
	private RegC100 preencheC100(DocumentoFiscal docFisc, Loja lojaSped) {
		RegC100 regC100 = new RegC100();
		IndicadorDeOperacao tipoOperacao = docFisc.getTipoOperacao();

		regC100.setIndOper(tipoOperacao);
		regC100.setIndEmit(getIndicadorEmitente(docFisc, lojaSped));
		regC100.setCodPart(getCodPart(docFisc));
		regC100.setCodMod(docFisc.getModelo());
		regC100.setCodSit(getCodSit(docFisc));
		regC100.setSer(docFisc.getSerie());
		regC100.setNumDoc(docFisc.getNumero());
		regC100.setChvNfe(docFisc.getNfeChaveAcesso());
		regC100.setDtDoc(docFisc.getEmissao());
		regC100.setDtES(docFisc.getCadastro().toLocalDate());

		regC100.setVlDoc(NumberUtilRegC100.getVlrOrBaseCalc(docFisc.getVlrTotalProduto(), tipoOperacao));

		regC100.setIndPgto(docFisc.getIndicadorPagamento());
		regC100.setVlDesc(docFisc.getValorDesconto());
		regC100.setVlAbatNt(BigDecimal.ZERO);
		regC100.setVlMerc(docFisc.getVlrTotalProduto());
		regC100.setIndFrt(docFisc.getIndicadorFrete());
		regC100.setVlFrt(docFisc.getValorFrete());
		regC100.setVlSeg(docFisc.getValorSeguro());
		regC100.setVlOutDa(docFisc.getValorOutrasDespesasAcessorias());
		regC100.setVlIcms(docFisc.getIcmsValor());
		regC100.setVlBcIcmsSt(docFisc.getIcmsStBase());
		regC100.setVlIcmsSt(docFisc.getIcmsStValor());
		regC100.setVlIpi(docFisc.getIpiValor());
		regC100.setVlPis(docFisc.getPisValor());
		regC100.setVlPisSt(BigDecimal.ZERO);
		regC100.setVlCofinsSt(BigDecimal.ZERO);		
		
		return regC100;
	}
	
	
	/**
	 * Dado um DocumentoFiscal, irá validar qual é o "tipo de preenchimento" (Se o mesmo se encaixa em alguma das DEZ EXCECOES, da Documentacao, GUIA PRATICO EFD).
	 * 
	 * Caso não se encaixe o {@link TipoPreenchimentoRegC100} então é "NORMAL"
	 * 
	 * @param docFisc
	 * @param lojaSped
	 * @return
	 */
	private TipoPreenchimentoRegC100 verificaTipoPreenchimento(DocumentoFiscal docFisc, Loja lojaSped) {
		LOG.log(Level.INFO, "Verificando o tipo de preenchimento do Registro C100");

		List<NFeStatus> nfesNaoAutorizadas = Arrays.asList(NFeStatus.CANCELADA, NFeStatus.DENEGADA, NFeStatus.INUTILIZADA);
		
		if (nfesNaoAutorizadas.contains(docFisc.getStatus())) {
			return TipoPreenchimentoRegC100.EX_1_COD_SITUACAO;
		}
		
		/** PS: Por enquanto só tem a validação do PRIMEIRO preenchimento **/
		return TipoPreenchimentoRegC100.NORMAL;
	}


	

	/**
	 * Irá preencher o REG C100 - Caso se encaixe na EXCEÇÃO 1, do Guia Pratico da EFD, que diz basicamente o Seguinte:
	 * "...DocumentosFiscais com o CODIGO SITUACAO 2,3,4 (Cancelado, Cancelado extemporêno e Denegado), Preencha somente --> REG, IND_OPER, IND_EMIT, COD_MOD, COD_SIT, SER, NUM_DOC e CHV_NF-e..." 
	 * "...Para os com CODIGO SITUACAO 5 (Numercao Inutilizada), todos os campos anteriores. EXCETO o campo CHV_NF-e...."
	 * 
	 * Demais campos deverão ser apresentados com conteúdo VAZIO “||”. Não informar registros filhos. 
	 * 
	 * @param docFisc
	 * @param lojaSped
	 * @return
	 */
	private RegC100 prencheC100ExcecaoUmCodSit(DocumentoFiscal docFisc, Loja lojaSped) {
		RegC100 regC100 = new RegC100();
		
		regC100.setIndOper(docFisc.getTipoOperacao());
		regC100.setIndEmit(getIndicadorEmitente(docFisc, lojaSped));
		regC100.setCodMod(docFisc.getModelo());
		regC100.setCodSit(getCodSit(docFisc));
		regC100.setSer(docFisc.getSerie());
		regC100.setNumDoc(docFisc.getNumero());
		regC100.setChvNfe(docFisc.getNfeChaveAcesso());
		// ACHO QUE NEM PRECISA DISSO
//		if (docFisc.getStatus().equals(NFeStatus.INUTILIZADA)) {
//			regC100.setChvNfe("");
//		}
		
		return regC100;
	}


	private RegC100 preencheC100ExcecaoDoisEmissaoPropria(DocumentoFiscal docFisc, Loja lojaSped) {
		// TODO Auto-generated method stub
		
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

		
	private SituacaoDoDocumento getCodSit(DocumentoFiscal docFisc) {
		// TODO Colocar regra para quando for um DocumentoFiscal "COMPLEMENTAR"
		NFeStatus nfeStatus = docFisc.getStatus();
			if (nfeStatus.getCodigo().length() == 2) {
				int nfeStatusCodigo = Integer.parseInt(nfeStatus.getCodigo());
				SituacaoDoDocumento sitDoc = SituacaoDoDocumento.values()[nfeStatusCodigo];
				return sitDoc;
			}
		return null;
	}
	
	
}
