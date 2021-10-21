package net.cartola.emissorfiscal.tributacao.federal;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static net.cartola.emissorfiscal.documento.TipoServico.ENERGIA;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.devolucao.Devolucao;
import net.cartola.emissorfiscal.devolucao.DevolucaoItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.documento.TipoServico;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.tributacao.CalculoFiscal;
import net.cartola.emissorfiscal.tributacao.CalculoImposto;
import net.cartola.emissorfiscal.tributacao.Imposto;

@Service
public class CalculoFiscalFederal implements CalculoFiscal {

	private static final Logger LOG = Logger.getLogger(CalculoFiscalFederal.class.getName());
	
	@Autowired
	private TributacaoFederalService tributacaoFederalService;

	@Autowired
	private CalculoPisCofins calculoPisCofins;

	@Autowired
	private CalculoIpi calculoIpi;
	
	@Override
	public void calculaImposto(DocumentoFiscal documentoFiscal) {
		LOG.log(Level.INFO, "Fazendo o calculo das TRIBUTAÇÕES FEDERAIS, para o Documento = {0} ", documentoFiscal.getDocumento());
		List<CalculoImposto> listaImpostos = new LinkedList<>();
		Set<Ncm> ncms = documentoFiscal.getNcms();
		
		Set<Finalidade> finalidades = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getFinalidade).collect(Collectors.toSet());

		Set<TributacaoFederal> tributacoesFederais = tributacaoFederalService
				.findTributacaoFederalByVariosNcmsEOperacaoEFinalidadeERegimeTributario(documentoFiscal.getOperacao(), documentoFiscal.getEmitente().getRegimeTributario(), finalidades, ncms);
		
		Map<Ncm, TributacaoFederal> mapaTributacoesPorNcm = ncms.stream()
				.collect(toMap(ncm -> ncm,
						ncm -> tributacoesFederais.stream()
								.filter(tributacaoFederal -> tributacaoFederal.getNcm().getId().equals(ncm.getId()))
								.findAny().get()));

		documentoFiscal.getItens().stream().forEach(di -> {
			TributacaoFederal tributacao = mapaTributacoesPorNcm.get(di.getNcm());
			listaImpostos.add(calculoPisCofins.calculaPis(di, tributacao));
			listaImpostos.add(calculoPisCofins.calculaCofins(di, tributacao));
			listaImpostos.add(calculoIpi.calculaIpi(di, tributacao));
		});

		setaPisBaseValor(documentoFiscal, listaImpostos);
		setaCofinsBaseValor(documentoFiscal, listaImpostos);
		setaIpiBaseValor(documentoFiscal, listaImpostos);
	}

	@Override
	public DocumentoFiscal calculaImposto(DocumentoFiscal documentoFiscal, Devolucao devolucao) {
		this.calculaImposto(documentoFiscal);
		List<CalculoImposto> listaImpostos =  new LinkedList<>();
		
		Map<Integer, Map<Long, Map<String, DevolucaoItem>>> mapDevolucaoPorItemCodigoXECodigoSeq = devolucao.getItens().stream()
				.collect(groupingBy(DevolucaoItem::getItem, groupingBy(DevolucaoItem::getCodigoX,
						toMap(DevolucaoItem::getCodigoSequencia,
								(DevolucaoItem devoItem) -> devoItem))));
		
		documentoFiscal.getItens().forEach(di -> {
			DevolucaoItem devoItem = mapDevolucaoPorItemCodigoXECodigoSeq.get(di.getItem()).get(di.getCodigoX()).get(di.getCodigoSequencia());
			listaImpostos.add(calculoIpi.calculaIpi(di, devoItem));
		});
		
		setaIpiBaseValor(documentoFiscal, listaImpostos);
		return documentoFiscal;
	}
	
	
	/**
	 * Atualmente o que tem aqui é o calcular do total, que temos de crédito nas entradas. Referente ao "DocumentoFiscal" (compra)
	 * Pois dos itens já foram feitos, calculados previamente no ERP, porém não é calculado o TOTAL que temos de crédito referente ao DocumentoFiscal.
	 * 
	 * @param docFiscal
	 */
	public void calculaImpostoEntrada(DocumentoFiscal docFiscal) {
		// Se TipoServico == NENHUM, então tem ITENS. E isso será usado para calcular o imposto
		if (docFiscal.getTipoServico().equals(TipoServico.NENHUM)) {
			BigDecimal pisValor = docFiscal.getItens().stream().map(DocumentoFiscalItem::getPisValor).reduce(BigDecimal.ZERO, BigDecimal::add);
			BigDecimal cofinsValor = docFiscal.getItens().stream().map(DocumentoFiscalItem::getCofinsValor).reduce(BigDecimal.ZERO, BigDecimal::add);
			
			docFiscal.setPisValor(pisValor);
			docFiscal.setCofinsValor(cofinsValor);
		} else {
			calculaImpostoEntradaServico(docFiscal);
		}
	}
	
	/**
	 * Calculando os IMPOSOTO, entrada dos SERVICO: ENERGIA
	 * @param documentoFiscal
	 */
	private void calculaImpostoEntradaServico(DocumentoFiscal docuFisc) {
		TipoServico tipoServico = docuFisc.getTipoServico();
		if (tipoServico.equals(ENERGIA)) {		// TODO Tenho que ver como que funciona para os casos que são CTEs
			BigDecimal pisValor = docuFisc.getValorTotalDocumento().multiply(new BigDecimal(0.0165));
			BigDecimal cofinsValor = docuFisc.getValorTotalDocumento().multiply(new BigDecimal(0.0760));
			docuFisc.setPisValor(pisValor);
			docuFisc.setCofinsValor(cofinsValor);
		}
	}
	
	private void setaPisBaseValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listaImpostos) {
		LOG.log(Level.INFO, "Totalizando o PIS BASE e o VALOR para o DocFiscId: {0} ", documentoFiscal.getId());
		documentoFiscal.setPisBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getPisBase)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		documentoFiscal.setPisValor(totaliza(listaImpostos.stream()
				.filter(pis -> pis.getImposto().equals(Imposto.PIS)).collect(Collectors.toList())));
	}

	private void setaCofinsBaseValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listaImpostos) {
		LOG.log(Level.INFO, "Totalizando o COFINS BASE e o VALOR para o DocFiscId: {0} ", documentoFiscal.getId());
		documentoFiscal.setCofinsBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getCofinsBase)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		documentoFiscal.setCofinsValor(totaliza(listaImpostos.stream()
				.filter(cofins -> cofins.getImposto().equals(Imposto.COFINS)).collect(Collectors.toList())));
	}
	
	private void setaIpiBaseValor(DocumentoFiscal documentoFiscal, List<CalculoImposto> listaImpostos) {
		LOG.log(Level.INFO, "Totalizando o IPI BASE e o VALOR para o DocumentoFiscal: {0} ", documentoFiscal);
		documentoFiscal.setIpiBase(documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getIpiBase)
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		documentoFiscal.setIpiValor(totaliza(listaImpostos.stream()
				.filter(ipi -> ipi.getImposto().equals(Imposto.IPI)).collect(Collectors.toList())));
	}
	
	private BigDecimal totaliza(List<CalculoImposto> listaImposto) {
		return listaImposto.stream().map(CalculoImposto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
