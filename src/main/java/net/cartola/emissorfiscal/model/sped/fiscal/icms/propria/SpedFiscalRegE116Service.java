package net.cartola.emissorfiscal.model.sped.fiscal.icms.propria;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 6 de jul. de 2021
 * @author robson.costa
 */
@Service
public class SpedFiscalRegE116Service {
	
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
	private SpedFiscalRegE116Repository spedFiscRegE116Repo;
	
	public List<SpedFiscalRegE116> save(List<SpedFiscalRegE116> listSpedFiscRegE116) {
		return spedFiscRegE116Repo.saveAll(listSpedFiscRegE116);
	}
	
	
	public Optional<SpedFiscalRegE116> save(SpedFiscalRegE116 spedFiscRegE116) {
		/**
		 * TODO 
		 * Faça as validações que tiver que ser feita
		 */
		return Optional.ofNullable(spedFiscRegE116Repo.saveAndFlush(spedFiscRegE116));
	}
	
	
	public Optional<SpedFiscalRegE116> findRegE116ByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		return spedFiscRegE116Repo.findByDataInicioSpedAndDataFimSped(dataInicio, dataFim);
	}
	
	
}
