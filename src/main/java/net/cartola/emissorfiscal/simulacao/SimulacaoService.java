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
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
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
	DocumentoFiscalService docFiscalService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private TributacaoEstadualService icmsService;
	
	@Autowired
	private TributacaoFederalService tributacaoFederalService;
	
	
	public List<String> getListMsgResultadoCalculo(DocumentoFiscal documentoFiscal ) {
		List<String> listResult = new ArrayList<String>();
		
		List<String> erros = validaTributacaoFederal(documentoFiscal);
		
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
		listResult.add("REGIME TRIBUTÁRIO EMITENTE: " +documentoFiscal.getDestinatario().getRegimeTributario());
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
	private List<String> validaTributacaoFederal(DocumentoFiscal documentoFiscal) {
		Map<String, Boolean> map = new HashMap<>();

		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(documentoFiscal.getOperacao().getDescricao());

		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());
		List<TributacaoEstadual> tributacoesEstaduais = new ArrayList<TributacaoEstadual>();
		List<TributacaoFederal> tributacoesFederais = new ArrayList<TributacaoFederal>();
		
//		if (opOperacao.isPresent() && !ncms.isEmpty() && !map.containsValue(false) ) {
			tributacoesEstaduais = icmsService.findTributacaoEstadualByOperacaoENcms(opOperacao.get(), ncms);
			tributacoesFederais = tributacaoFederalService.findTributacaoFederalByVariosNcmsEOperacao(documentoFiscal.getOperacao(), ncms);
//		}
		if (!tributacoesFederais.isEmpty()) {
			docFiscalService.validaTributacaoFederalDoDocumentoFiscal(documentoFiscal, ncms, tributacoesFederais, map);
		}
		
		if(!tributacoesEstaduais.isEmpty()) {
			
		}
		
		return ValidationHelper.processaErros(map);
	}

	
}

