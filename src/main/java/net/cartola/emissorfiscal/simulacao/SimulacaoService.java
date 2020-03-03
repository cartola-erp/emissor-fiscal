package net.cartola.emissorfiscal.simulacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualService;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederalService;
import net.cartola.emissorfiscal.util.ValidationHelper;

/**
 * 24 de fev de 2020
 * @author robson.costa
 */

@Service
public class SimulacaoService {
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private NcmService ncmService;
	
	@Autowired
	private TributacaoEstadualService icmsService;
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private TributacaoFederalService tributacaoFederalService;
	
	
	public List<String> getListMsgResultadoCalculo(DocumentoFiscal documentoFiscal ) {
		List<String> listResult = new ArrayList<String>();
		
		List<String> erros = validaTributacaoFederalAndEstadual(documentoFiscal);
		
		if (!ValidationHelper.collectionEmpty(erros)) {
			return erros;
		}
		DocumentoFiscalItem item = documentoFiscal.getItens().get(0);
		
		listResult.add(" =========================================== DADOS INFORMADOS =========================================== ");
		listResult.add("Operação: " +documentoFiscal.getOperacao().getDescricao());
		listResult.add("Uf Origem: " +documentoFiscal.getEmitente().getUf() );
		listResult.add("Uf Destino: " +documentoFiscal.getDestinatario().getUf() );
		listResult.add("QTD ITEM: " + item.getQuantidade() );
		listResult.add("VALOR DO ITEM: " + item.getValorUnitario() );
		listResult.add("FINALIDADE: " +item.getFinalidade() );
		listResult.add("NCM: " +item.getNcm().getNumero() + " | EXCEÇÃO: " +item.getNcm().getExcecao());
		listResult.add("REGIME TRIBUTÁRIO EMITENTE: " +documentoFiscal.getEmitente().getRegimeTributario());
		listResult.add(" ===================================== VALORES DOCUMENTO FISCAL ===================================== ");
		listResult.add("ICMS BASE (NFE): " +documentoFiscal.getIcmsBase().toPlainString());
		listResult.add("ICMS VALOR (NFE): " +documentoFiscal.getIcmsValor().toPlainString());
//		listResult.add("ICMS ALIQ.: " +documentoFiscal.getIcmsValor());
		listResult.add("PIS BASE (NFE): " +documentoFiscal.getPisBase().toPlainString());
		listResult.add("PIS VALOR (NFE): " +documentoFiscal.getPisValor().toPlainString());
		listResult.add("COFINS BASE (NFE): " +documentoFiscal.getCofinsBase().toPlainString());
		listResult.add("COFINS VALOR (NFE): " +documentoFiscal.getCofinsValor().toPlainString());
		listResult.add("IPI BASE (NFE): " +documentoFiscal.getIpiBase());
		listResult.add("IPI VALOR (NFE): " +documentoFiscal.getIpiValor());
		listResult.add(" =========================================== VALORES ITEM =========================================== ");
		listResult.add(" ----------------------------------- ICMS ----------------------------------- ");
		listResult.add("ICMS BASE (ITEM): " +item.getIcmsBase().toPlainString());
		listResult.add("ICMS VALOR (ITEM): " +item.getIcmsValor().toPlainString());
		listResult.add("ICMS ALIQ.: (ITEM): " +item.getIcmsAliquota().toPlainString());
		listResult.add("ICMS CST.: (ITEM): " +item.getIcmsCst());
		listResult.add("ICMS CEST.: (ITEM): " +item.getIcmsCest());
		listResult.add(" ----------------------------------- PIS ----------------------------------- ");
		listResult.add("PIS BASE (ITEM): " +item.getPisBase().toPlainString());
		listResult.add("PIS VALOR (ITEM): " +item.getPisValor().toPlainString());
		listResult.add("PIS ALIQ. (ITEM): " +item.getPisAliquota().toPlainString());
		listResult.add("PIS CST (ITEM): " +item.getPisCst());
		listResult.add(" ----------------------------------- COFINS ----------------------------------- ");
		listResult.add("COFINS BASE (ITEM): " +item.getCofinsBase().toPlainString());
		listResult.add("COFINS VALOR (ITEM): " +item.getCofinsValor().toPlainString());
		listResult.add("COFINS ALIQ. (ITEM): " +item.getCofinsAliquota().toPlainString());
		listResult.add("COFINS CST (ITEM): " +item.getCofinsCst());
		listResult.add(" ----------------------------------- IPI ----------------------------------- ");
		listResult.add("IPI BASE (ITEM): " +item.getIpiBase());
		listResult.add("IPI VALOR (ITEM): " +item.getIpiValor());
		listResult.add("IPI ALIQ. (ITEM): " +item.getIpiAliquota());
		listResult.add("IPI CST (ITEM): " +item.getIpiCst());

		
		if (listResult.isEmpty()) {
			listResult.add("Está faltando o cadastro de alguma tributação para esse NCM escolhido");
		}
		return listResult;
	}
	
	/**
	 * Irá retornar cada resultado do calculo fiscal, em uma linha diferente
	 * @param documentoFiscal
	 * @return 
	 */
	public StringBuffer getStrbuffMsgResultadoCalculo(DocumentoFiscal documentoFiscal) {
		final StringBuffer sb = new StringBuffer();
		List<String> listMsgResultado = getListMsgResultadoCalculo(documentoFiscal);
		
		listMsgResultado.forEach(msg -> {
			sb.append(msg).append("\n");
		});
		return sb;
	}
	
	
	// Isso é uma parte das validações que tem no DocumentoFiscalService
	// Como é para o SIMULADOR, o que importa aqui é a parte de Existir tributações, conforme: operação, finalide e regime tributario emitente
	private List<String> validaTributacaoFederalAndEstadual(DocumentoFiscal documentoFiscal) {
		Map<String, Boolean> map = new HashMap<>();
		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(documentoFiscal.getOperacao().getDescricao());
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());
		Set<Finalidade> finalidades = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getFinalidade).collect(Collectors.toSet());
		Estado estadoOrigem = estadoService.findBySigla(documentoFiscal.getEmitente().getUf()).get();
		Estado estadoDestino = estadoService.findBySigla(documentoFiscal.getDestinatario().getUf()).get();

		List<TributacaoEstadual> tributacoesEstaduais = new ArrayList<TributacaoEstadual>();
		List<TributacaoFederal> tributacoesFederais = new ArrayList<TributacaoFederal>();
		
//		if (opOperacao.isPresent() && !ncms.isEmpty() && !map.containsValue(false) ) {
			tributacoesEstaduais = icmsService.findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(opOperacao.get(), estadoOrigem, estadoDestino,  documentoFiscal.getEmitente().getRegimeTributario(), finalidades, ncms);
			tributacoesFederais = tributacaoFederalService.findTributacaoFederalByVariosNcmsEOperacaoEFinalidadeERegimeTributario(documentoFiscal.getOperacao(), documentoFiscal.getEmitente().getRegimeTributario(), finalidades, ncms);
//		}
		if (tributacoesFederais.isEmpty()) {
			map.put("Não Existe TRIBUTAÇÃO FEDERAL para o Regime (do emitente): " + documentoFiscal.getEmitente().getRegimeTributario(), false);
		}
		
		if(tributacoesEstaduais.isEmpty()) {
			map.put("Não Existe TRIBUTAÇÃO ESTADUAL para o Regime (do emitente): " + documentoFiscal.getEmitente().getRegimeTributario(), false);
		}
		
		return ValidationHelper.processaErros(map);
	}

	
	public DocumentoFiscal setValuesForCalc(DocumentoFiscal documentoFiscal, Long ufOrigemId, Long ufDestinoId, Long operacaoId, Long ncmId, String regimeTributario) {
		Estado estadoOrigem = estadoService.findOne(ufOrigemId).get();
		Estado estadoDestino = estadoService.findOne(ufDestinoId).get();
		Operacao operacao = operacaoService.findOne(operacaoId).get();
		Ncm ncm = ncmService.findOne(ncmId).get();
		
		Pessoa emitente = new Pessoa();
		emitente.setCnpj(123456789012L);
		emitente.setUf(estadoOrigem.getSigla());
		setRegimeTributario(emitente, regimeTributario);
		
		Pessoa destinatario = new Pessoa();
		destinatario.setCnpj(98765432102L);
		destinatario.setUf(estadoDestino.getSigla());;
		
		documentoFiscal.setEmitente(emitente);
		documentoFiscal.setDestinatario(destinatario);
		documentoFiscal.setOperacao(operacao);
		documentoFiscal.getItens().get(0).setNcm(ncm);
		
		return documentoFiscal;
	}
	
	private void setRegimeTributario(Pessoa pessoa, String regimeTributario) {
		for(RegimeTributario regimeTributa : RegimeTributario.values()) {
			if(regimeTributa.getDescricao().equalsIgnoreCase(regimeTributario)) {
				pessoa.setRegimeTributario(regimeTributa);
			}
		}
	}
	
	
}

