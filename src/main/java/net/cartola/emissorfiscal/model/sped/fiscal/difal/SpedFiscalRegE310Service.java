package net.cartola.emissorfiscal.model.sped.fiscal.difal;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
//	public Optional<SpedFiscalRegE111> findRegE111ByPeriodoAndLoja(LocalDate dataInicio, LocalDate dataFim, Loja loja) {
//		return spedFiscReg310Repo.findByDataInicioSpedAndDataFimSpedAndLoja(dataInicio, dataFim, loja);
//	}
}
