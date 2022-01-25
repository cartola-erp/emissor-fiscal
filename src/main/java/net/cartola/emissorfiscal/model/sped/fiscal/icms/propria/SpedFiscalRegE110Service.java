package net.cartola.emissorfiscal.model.sped.fiscal.icms.propria;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalParametrosBusca;

/**
 * @date 5 de jul. de 2021
 * @author robson.costa
 */
@Service
public class SpedFiscalRegE110Service {
	
	
	@Autowired
	private SpedFiscalRegE111Service spedFiscRegE111Service;
	
	@Autowired
	private SpedFiscalRegE110Repository spedFiscRegE110Repo;
	
	
	
	public List<SpedFiscalRegE110> save(List<SpedFiscalRegE110> listSpedFiscRegE110) {
		return spedFiscRegE110Repo.saveAll(listSpedFiscRegE110);
	}
	
	
	public Optional<SpedFiscalRegE110> save(SpedFiscalRegE110 spedFiscRegE110) {
		/**
		 * TODO 
		 * Faça as validações que tiver que ser feita;
		 * 
		 * Ex.: Procurar se já existe algum registro E110 para o periodo, caso sim os Registro E111, ficaram linkados com 
		 * esse registro E110 já existente, caso não salve um no REGE110
		 * 
		 */
		return Optional.ofNullable(spedFiscRegE110Repo.saveAndFlush(spedFiscRegE110));
	}
	
	public Set<SpedFiscalRegE110> findRegE110ByPeriodoAndLoja(LocalDate dataInicio, LocalDate dataFim, Loja loja) {
		MovimentoMensalParametrosBusca paramBuscaSped = new MovimentoMensalParametrosBusca();
		paramBuscaSped.setDataInicioSped(dataInicio);
		paramBuscaSped.setDataFimSped(dataFim);
		return findRegE110ByPeriodoAndLoja(paramBuscaSped, loja);
	}
	
	public Set<SpedFiscalRegE110> findRegE110ByPeriodoAndLoja(MovimentoMensalParametrosBusca paramBuscaSped, Loja loja) {
		Set<SpedFiscalRegE110> listRegE110 = spedFiscRegE110Repo.
				findByDataInicioApuracaoGreaterThanEqualAndDataFimApuracaoLessThanEqualAndLoja(paramBuscaSped.getDataInicioSped(), paramBuscaSped.getDataFimSped(), loja);
		if (listRegE110 != null) {
			return listRegE110;
		}
		return new HashSet<>();
	}


}
