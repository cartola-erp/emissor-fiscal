package net.cartola.emissorfiscal.model.sped.fiscal.difal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.loja.Loja;

/**
 * @date 6 de jul. de 2021
 * @author robson.costa
 */
@Service
public class SpedFiscalRegE310Service {

	@Autowired
	private SpedFiscalRegE310Repository spedFiscReg310Repo;
	
	@Autowired
	private SpedFiscalRegE316Service spedFiscRegE316Service;
	
	public List<SpedFiscalRegE310> save(List<SpedFiscalRegE310> listSpedFiscRegE310) {
		return spedFiscReg310Repo.saveAll(listSpedFiscRegE310);
	}
	
	
	public Optional<SpedFiscalRegE310> save(SpedFiscalRegE310 spedFiscRegE310) {
		/**
		 * TODO 
		 * Faça as validações que tiver que ser feita;
		 * 
		 * Ex.: Procurar se já existe algum registro E110 para o periodo, caso sim os Registro E111, ficaram linkados com 
		 * esse registro E110 já existente, caso não salve um no REGE110
		 * 
		 */
		return Optional.ofNullable(spedFiscReg310Repo.saveAndFlush(spedFiscRegE310));
	}


	public Set<SpedFiscalRegE310> findRegE310ByPeriodoAndLoja(LocalDate dataInicio, LocalDate dataFim, Loja loja) {
		Set<SpedFiscalRegE310> listREgE310 = spedFiscReg310Repo.findByDataInicioApuracaoGreaterThanEqualAndDataFimApuracaoLessThanEqualAndLoja(dataInicio, dataFim, loja);
		if (listREgE310 != null) {
			return listREgE310;
		}
		return new HashSet<>();
	}
	
	
//	public Optional<SpedFiscalRegE111> findRegE111ByPeriodoAndLoja(LocalDate dataInicio, LocalDate dataFim, Loja loja) {
//		return spedFiscReg310Repo.findByDataInicioSpedAndDataFimSpedAndLoja(dataInicio, dataFim, loja);
//	}
}
