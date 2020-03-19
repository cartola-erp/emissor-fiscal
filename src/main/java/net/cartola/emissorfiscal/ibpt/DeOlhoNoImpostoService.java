package net.cartola.emissorfiscal.ibpt;

import java.math.BigDecimal;
import java.util.Optional;

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
	
	public Optional<DeOlhoNoImposto> findNcmByNumeroAndExcecao(int ncm, String exce) {
		return olhoNoImpostoRepository.findByNcmAndExce(ncm, exce);
	}
	
	public Optional<DeOlhoNoImposto> findOne(Long id) {
		return olhoNoImpostoRepository.findById(id);
	}
	
	//  ========================================================= CALCULO =================================================================
	
	public BigDecimal getTotalImposto(Optional<DocumentoFiscal> opDocFiscal) {
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
		DeOlhoNoImposto[] olhoNoImposto = {new DeOlhoNoImposto()};
		BigDecimal[] vlrImpostoFederal = {BigDecimal.ZERO};
		BigDecimal[] vlrImpostoEstadual = {BigDecimal.ZERO};
		BigDecimal[] vlrImpostoMunicipal = {BigDecimal.ZERO};
		
		if (opDocFiscal.isPresent() && opDocFiscal.get().getItens() != null) {
			opDocFiscal.get().getItens().stream().forEach(item -> {
				olhoNoImposto[0] = findNcmByNumeroAndExcecao(item.getNcm().getNumero(), Integer.toString(item.getNcm().getExcecao())).get();
				BigDecimal totalItem = totalItem(item);
				BigDecimal aliqEstadual = olhoNoImposto[0].getAliqEstadual();
				BigDecimal aliqMunicipal = olhoNoImposto[0].getAliqMunicipal();
				
				vlrImpostoFederal[0] = vlrImpostoFederal[0].add((CalculaImpostoFederal(item, olhoNoImposto[0])));
				vlrImpostoEstadual[0] = vlrImpostoEstadual[0].add(totalItem.multiply(aliqEstadual));
				vlrImpostoMunicipal[0] = vlrImpostoMunicipal[0].add(totalItem.multiply(aliqMunicipal));
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

