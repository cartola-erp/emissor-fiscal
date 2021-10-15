package net.cartola.emissorfiscal.sped.fiscal.blocoD.service;

import static java.util.stream.Collectors.toSet;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCodPart;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCodSituacao;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getIndicadorEmitente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.ChaveAcesso;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.FinalidadeEmissao;
import net.cartola.emissorfiscal.documento.NFeStatus;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD100;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD195;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.util.ValidationHelper;

/**
 * 
 * OBS: Se A operação do CTE, for intramunicipal (dentro do municipio), o que será pago é ISS, ou seja, não estará no SPED.
 * PS: Aparentemente nesses casos é emitido uma NFSE
 * 
 * @autor robson.costa
 * @data 5 de mai. de 2021
 */
@Service
class RegD100Service implements MontaGrupoDeRegistroList<RegD100, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegD100Service.class.getName());

	@Autowired
	private RegD190Service regD190Service;

	@Autowired
	private RegD195Service regD195Service;
	
	private MovimentoMensalIcmsIpi movimentosIcmsIpi;
	
	
	@Override
	public List<RegD100> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro D100");
		this.movimentosIcmsIpi = movimentosIcmsIpi;
		
		Loja lojaSped = movimentosIcmsIpi.getLoja();
		Set<DocumentoFiscal> listDocFiscalServicoTransporte = getDocFiscalServicoTransporte(movimentosIcmsIpi);
		
		List<RegD100> listRegD100 = new ArrayList<>(listDocFiscalServicoTransporte.size());
		
		listDocFiscalServicoTransporte.stream().forEach(servicoTransporte -> {
			listRegD100.add(gerarRegistroD100(servicoTransporte, lojaSped));
		});

		LOG.log(Level.INFO, "Registro D100, terminado. REG D100: ");
		return listRegD100;
	}

	private RegD100 gerarRegistroD100(DocumentoFiscal servicoTransporte, Loja lojaSped) {
		// TODO Auto-generated method stub
		RegD100 regD100 = new RegD100();
		ChaveAcesso chaveAcesso = new ChaveAcesso(servicoTransporte.getNfeChaveAcesso());
		servicoTransporte.setSerie(Long.parseLong(chaveAcesso.getSerie()));
		
		TipoPreenchimentoRegD100 tipoPreenchimentoRegD100 = verificarTipoPreenchimento(servicoTransporte, lojaSped);
		
		switch (tipoPreenchimentoRegD100) {
		case EX_1_COD_SITUACAO:
			regD100 = preencherD100ExcecaoUmCodSit(servicoTransporte, lojaSped);
			break;
		
		case EX_2_CTE_COMPLEMENTAR:
			regD100 = preencherD100ExcecaoDoisCteComplementar(servicoTransporte, lojaSped);
			break;
		// EX 3
			
		case EX_4_CTE_EMISSAO_PROPRIA: 
			LOG.log(Level.WARNING, "Não existe regra para o preenchimento de CTE de emissão própria. CHV DOC = {0} " ,servicoTransporte.getNfeChaveAcesso());
			return null;
			
		
		default:
			regD100 = new RegD100(servicoTransporte, lojaSped, this.movimentosIcmsIpi.getMapLojasPorCnpj());
			regD100.setRegD190(regD190Service.montarGrupoRegC190(servicoTransporte, this.movimentosIcmsIpi));
			break;
		}
		
		if (isGeraRegD195PortariaCat66de2018(regD100)) {
			List<RegD195> listRegD195 = regD195Service.montarGrupoRegD195PortariaCat66De2018(regD100.getRegD190(), servicoTransporte, this.movimentosIcmsIpi);
			movimentosIcmsIpi.addObservacaoLancamentoFiscal(listRegD195);
			regD100.setRegD195(listRegD195);
		}
		movimentosIcmsIpi.addDocumentoFiscalPorSituacao(servicoTransporte);
		return regD100;
	}

	
	private boolean isGeraRegD195PortariaCat66de2018(RegD100 regD100) {
		if (regD100 != null && ValidationHelper.collectionNotEmptyOrNull(regD100.getRegD190())) {
			return true;
		}
		return false;
	}

	/**
	 * OBS: Não está verificando TODAS as EXCEÇÕES do GUIA PRATICO. 
	 * E sim somente, aquelas em que podemos nos encaixar, atualmente.
	 * 
	 * @param servicoTransporte
	 * @param lojaSped
	 * @return
	 */
	private TipoPreenchimentoRegD100 verificarTipoPreenchimento(DocumentoFiscal servicoTransporte, Loja lojaSped) {
//		LOG.log(Level.INFO, "Verificando o tipo de preenchimento do Registro D100");
		List<NFeStatus> docuFiscalNaoAutorizadas = Arrays.asList(NFeStatus.CANCELADA, NFeStatus.DENEGADA, NFeStatus.INUTILIZADA);

		if (docuFiscalNaoAutorizadas.contains(servicoTransporte.getStatus())) {
			return TipoPreenchimentoRegD100.EX_1_COD_SITUACAO;
		}
		
		if (servicoTransporte.getFinalidadeEmissao().equals(FinalidadeEmissao.COMPLEMENTAR)) {
			return TipoPreenchimentoRegD100.EX_2_CTE_COMPLEMENTAR;
		}
		
		// EXCECAO 03 -EX_3_CTE_REGIME_ESPECIAL_NORMA_ESPECIFICA
		
		// EXCECAO 04 - EX_4_CTE_EMISSAO_PROPRIA
		if (servicoTransporte.getEmitente().getCnpj().equals(lojaSped.getCnpj())) {
			return TipoPreenchimentoRegD100.EX_4_CTE_EMISSAO_PROPRIA;
		}
		
		// EXCECAO 05 - EX_5_CTE_EMITIDA_TERCEIROS, equivale a EXCECAO 07 do REG C100
		
		
		return TipoPreenchimentoRegD100.NORMAL;
	}

	/**
	 * Será preenchido o REG D100 - Caso o DocumentoFiscal (de servico de transporte), se encaixe na EXCEÇÃO 1 - do Guia Prático da EFD, que basicamente é:
	 * Referente aos DocumentosFiscais -> "com o CODIGO SITUACAO 2,3,4 (Cancelado, Cancelado extemporêno e Denegado)...",
	 * @param servicoTransporte
	 * @param lojaSped
	 * @return
	 */
	private RegD100 preencherD100ExcecaoUmCodSit(DocumentoFiscal servicoTransporte, Loja lojaSped) {
		RegD100 regD100 = new RegD100();
		
		regD100.setIndOper(servicoTransporte.getTipoOperacao());
		regD100.setIndEmit(getIndicadorEmitente(servicoTransporte, lojaSped));
		regD100.setCodMod(servicoTransporte.getModelo());
		regD100.setCodSit(getCodSituacao(servicoTransporte));
		regD100.setSer(servicoTransporte.getSerie());
//		regD100.setSub(sub);		// SubSerie (os arquivos que eu vi de exemplo estavem smp VAZIO
		regD100.setNumDoc(servicoTransporte.getNumeroNota());
		regD100.setChvCte(servicoTransporte.getNfeChaveAcesso());
		
		return regD100;
	}
	
	private RegD100 preencherD100ExcecaoDoisCteComplementar(DocumentoFiscal servicoTransporte, Loja lojaSped) {
		// TODO Auto-generated method stub
		RegD100 regD100 = new RegD100();
		
		regD100.setIndOper(servicoTransporte.getTipoOperacao());
		regD100.setIndEmit(getIndicadorEmitente(servicoTransporte, lojaSped));
		regD100.setCodPart(getCodPart(servicoTransporte, movimentosIcmsIpi.getMapLojasPorCnpj()));
		regD100.setCodMod(servicoTransporte.getModelo());
		regD100.setCodSit(getCodSituacao(servicoTransporte));
		regD100.setSer(servicoTransporte.getSerie());
//		regD100.setSub(servicoTransporte.getSub);
		
		regD100.setNumDoc(servicoTransporte.getNumeroNota());
		regD100.setChvCte(servicoTransporte.getNfeChaveAcesso());
		regD100.setDtDoc(servicoTransporte.getEmissao());

		regD100.setRegD190(regD190Service.montarGrupoRegC190(servicoTransporte, this.movimentosIcmsIpi));
		return regD100;
	}
	
	/**
	 * Irá retornar apenas os DocumentosFiscais, de "TRANSPORTE"
	 * 
	 * @param movimentosIcmsIpi
	 * @return
	 */
	private Set<DocumentoFiscal> getDocFiscalServicoTransporte(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		List<DocumentoFiscal> listDocumentoFiscal = movimentosIcmsIpi.getListDocumentoFiscal();
//		Loja lojaSped = movimentosIcmsIpi.getLoja();
		
		Set<DocumentoFiscal> setServicoTransporte = listDocumentoFiscal.stream()
				.filter(docFisc -> getModelosDocFiscRegD100().contains(docFisc.getModelo())).collect(toSet());
		return setServicoTransporte;
	}

	
	/**
	 * Modelos Dos DocumentoFiscais de Transportes, válidos para ser escriturados no REG D100.
	 * 
	 * PS: A maioria ou todos, serão o {@link ModeloDocumentoFiscal._57} (CTE)
	 * @return
	 */
	private List<ModeloDocumentoFiscal> getModelosDocFiscRegD100() {
		List<ModeloDocumentoFiscal> listModelosServicoTransporte = new ArrayList<>();
		listModelosServicoTransporte.add(ModeloDocumentoFiscal._7);
		listModelosServicoTransporte.add(ModeloDocumentoFiscal._8);
		listModelosServicoTransporte.add(ModeloDocumentoFiscal._8B);
		listModelosServicoTransporte.add(ModeloDocumentoFiscal._9);
		listModelosServicoTransporte.add(ModeloDocumentoFiscal._10);
		listModelosServicoTransporte.add(ModeloDocumentoFiscal._11);
		listModelosServicoTransporte.add(ModeloDocumentoFiscal._26);
		listModelosServicoTransporte.add(ModeloDocumentoFiscal._27);
		listModelosServicoTransporte.add(ModeloDocumentoFiscal._57);
//		listModelosServicoTransporte.add(ModeloDocumentoFiscal._63);	--> Só é obrigatório nas SAÍDAS (e não emitimos esse modelo de DocFiscal)
		listModelosServicoTransporte.add(ModeloDocumentoFiscal._67);
		return listModelosServicoTransporte;
	}

	
}
