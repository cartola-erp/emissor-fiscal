package net.cartola.emissorfiscal.model.sped.fiscal.icms.propria;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 5 de jul. de 2021
 * @author robson.costa
 */
@Service
public class SpedFiscalRegE111Service {
	
	
	/**
	 * Ao tentar salvar um REG E111, tem que ser verificado se tem algum E110, para o mesmo periodo do E111;
	 * 	-> CASO SIM
	 * 		-> O E111, fica "linkado", com o E110
	 *	-> CASO NÂO 
	 *		-> É Criado um novo E110
	 *
	 *	-> OBS: 
	 *		-> Um Reg E111, Não pode existir sem um E110, mas um E110 pode existir sem um E111
	 */
	
	@Autowired
	private SpedFiscalRegE111Repository spedFiscRegE111Repo;
	
	public List<SpedFiscalRegE111> save(List<SpedFiscalRegE111> listSpedFiscRegE111) {
		return spedFiscRegE111Repo.saveAll(listSpedFiscRegE111);
	}
	
	
	public Optional<SpedFiscalRegE111> save(SpedFiscalRegE111 spedFiscRegE111) {
		/**
		 * TODO 
		 * Faça as validações que tiver que ser feita
		 */
		return Optional.ofNullable(spedFiscRegE111Repo.saveAndFlush(spedFiscRegE111));
	}
	
	
	public Optional<SpedFiscalRegE111> findRegE111ByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		return spedFiscRegE111Repo.findByDataInicioAndDataFim(dataInicio, dataFim);
	}
	
	
	
}
