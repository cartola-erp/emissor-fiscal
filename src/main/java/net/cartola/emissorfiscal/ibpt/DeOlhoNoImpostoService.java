package net.cartola.emissorfiscal.ibpt;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;


/**
 * 17 de mar de 2020
 * @author robson.costa
 */	
@Service
public class DeOlhoNoImpostoService {
	
	@Autowired
	private DeOlhoNoImpostoRepository olhoNoImpostoRepository;
	
	public List<DeOlhoNoImposto> findNcmByNumeroInAndExcecaoIn(Collection<Integer> ncm, Collection<String> exce) {
		return olhoNoImpostoRepository.findByNcmInAndExceIn(ncm, exce);
	}
	
	public Optional<DeOlhoNoImposto> findOne(Long id) {
		return olhoNoImpostoRepository.findById(id);
	}
	
	//  ========================================================= CALCULO =================================================================
	
	public BigDecimal getTotalImpostoDocumentoFiscal(Optional<DocumentoFiscal> opDocFiscal) {
		BigDecimal totalImposto = BigDecimal.ZERO;
		if (opDocFiscal.isPresent()) {
			setDeOlhoNoImposto(opDocFiscal);
			DocumentoFiscal docFiscal = opDocFiscal.get();
			totalImposto = totalImposto.add(docFiscal.getValorImpostoFederal()
					.add(docFiscal.getValorImpostoEstadual()
				    .add(docFiscal.getValorImpostoMunicipal())));
		}
		return totalImposto;
	}
	
	public void setDeOlhoNoImposto(Optional<DocumentoFiscal> opDocFiscal) {
		BigDecimal[] vlrImpostoFederal = {BigDecimal.ZERO};
		BigDecimal[] vlrImpostoEstadual = {BigDecimal.ZERO};
		BigDecimal[] vlrImpostoMunicipal = {BigDecimal.ZERO};
		
		if (opDocFiscal.isPresent() && opDocFiscal.get().getItens() != null) {
			Set<Integer> numeroNcm = new HashSet<>();
			Set<String> excecaoNcm = new HashSet<>();
			opDocFiscal.get().getItens().forEach(item -> {
				numeroNcm.add(item.getNcm().getNumero());
				excecaoNcm.add(Integer.toString(item.getNcm().getExcecao()));
			});
			List<DeOlhoNoImposto> listOlhoNoImposto = findNcmByNumeroInAndExcecaoIn(numeroNcm, excecaoNcm);
			// SÒ VOU AO BD UMA VEZ
			opDocFiscal.get().getItens().stream().forEach(item -> {
				Optional<DeOlhoNoImposto> opOlhoNoImposto = listOlhoNoImposto.stream().filter( o -> o.getNcm() == item.getNcm().getNumero() && Integer.parseInt(o.getExce())== item.getNcm().getExcecao()).findAny();
				if (opOlhoNoImposto.isPresent()) {
					BigDecimal totalItem = totalItem(item);
					BigDecimal aliqEstadual = opOlhoNoImposto.get().getAliqEstadual();
					BigDecimal aliqMunicipal = opOlhoNoImposto.get().getAliqMunicipal();
					
					vlrImpostoFederal[0] = vlrImpostoFederal[0].add((CalculaImpostoFederal(item, opOlhoNoImposto.get())));
					vlrImpostoEstadual[0] = vlrImpostoEstadual[0].add(totalItem.multiply(aliqEstadual));
					vlrImpostoMunicipal[0] = vlrImpostoMunicipal[0].add(totalItem.multiply(aliqMunicipal));
				}
			});
			opDocFiscal.get().setValorImpostoFederal(vlrImpostoFederal[0]);
			opDocFiscal.get().setValorImpostoEstadual(vlrImpostoEstadual[0]);
			opDocFiscal.get().setValorImpostoMunicipal(vlrImpostoMunicipal[0]);
		}
	}
	
	private BigDecimal CalculaImpostoFederal(DocumentoFiscalItem item, DeOlhoNoImposto olhoNoImposto) {
		BigDecimal aliqFederalNacional = olhoNoImposto.getAliqFederalNacional();
		BigDecimal aliqFederalImportado = olhoNoImposto.getAliqFederalImportado();
		
		switch (item.getOrigem()) {
			case NACIONAL:
			case NACIONAL_CONTEUDO_IMPORTADO_MAIOR_40:
			case NACIONAL_CONFORMIDADE_PROCESSOS:
			case NACIONAL_CONTEUDO_IMPORTADO_MENOR_40:
			case NACIONAL_CONTEUDO_IMPORTADO_MAIOR_70:
			return totalItem(item).multiply(aliqFederalNacional);
		default:
			return totalItem(item).multiply(aliqFederalImportado);
		}
	}

	
	private BigDecimal totalItem(DocumentoFiscalItem item) {
		return item.getValorUnitario().multiply(item.getQuantidade());
	}
	
}

