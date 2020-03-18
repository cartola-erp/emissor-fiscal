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
			totalImposto.add(docFiscal.getValorImpostoFederal()
					.add(docFiscal.getValorImpostoEstadual()
					.add(docFiscal.getValorImpostoMunicipal())));
		}
		return totalImposto;
	}
	
	// ACho melhor só listar os impostos aqui retornar numa lista,
	// daí em outro método eu totalizo
	public void setDeOlhoNoImposto(Optional<DocumentoFiscal> opDocFiscal) {
		DeOlhoNoImposto[] opOlhoNoImposto = new DeOlhoNoImposto[0];
		BigDecimal vlrImpostoFederal = BigDecimal.ZERO;
		BigDecimal vlrImpostoEstadual = BigDecimal.ZERO;
		BigDecimal vlrImpostoMunicipal = BigDecimal.ZERO;
		
		if (opDocFiscal.isPresent()) {
			opDocFiscal.get().getItens().stream().forEach(item -> {
				opOlhoNoImposto[0] = findNcmByNumeroAndExcecao(item.getNcm().getNumero(), Integer.toString(item.getNcm().getExcecao())).get();
				BigDecimal totalItem = totalItem(item);
				vlrImpostoFederal.add(CalculaImpostoFederal(item, opOlhoNoImposto[0]));
				vlrImpostoEstadual.add(totalItem.multiply(opOlhoNoImposto[0].getAliqEstadual()));
				vlrImpostoMunicipal.add(totalItem.multiply(opOlhoNoImposto[0].getAliqMunicipal()));
			});
			
			opDocFiscal.get().setValorImpostoFederal(vlrImpostoFederal);
			opDocFiscal.get().setValorImpostoEstadual(vlrImpostoEstadual);
			opDocFiscal.get().setValorImpostoMunicipal(vlrImpostoMunicipal);
		}
	}
	
	private BigDecimal CalculaImpostoFederal(DocumentoFiscalItem item, DeOlhoNoImposto opOlhoNoImposto) {
		switch (item.getOrigem()) {
			case NACIONAL:
			case NACIONAL_CONTEUDO_IMPORTADO_MAIOR_40:
			case NACIONAL_CONFORMIDADE_PROCESSOS:
			case NACIONAL_CONTEUDO_IMPORTADO_MENOR_40:
			case NACIONAL_CONTEUDO_IMPORTADO_MAIOR_70:
			return totalItem(item).multiply(opOlhoNoImposto.getAliqFederalNacional());
		default:
			return totalItem(item).multiply(opOlhoNoImposto.getAliqFederalImportado());
		}
	}

	
	private BigDecimal totalItem(DocumentoFiscalItem item) {
		return item.getValorUnitario().multiply(item.getQuantidade());
	}
	
}

