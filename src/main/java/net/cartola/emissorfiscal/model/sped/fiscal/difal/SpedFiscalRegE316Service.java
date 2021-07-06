package net.cartola.emissorfiscal.model.sped.fiscal.difal;

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
public class SpedFiscalRegE316Service {
	
	
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
	private SpedFiscalRegE316Repository spedFiscRegE316Repo;
	
	public List<SpedFiscalRegE316> save(List<SpedFiscalRegE316> listSpedFiscRegE316) {
		return spedFiscRegE316Repo.saveAll(listSpedFiscRegE316);
	}
	
	
	public Optional<SpedFiscalRegE316> save(SpedFiscalRegE316 spedFiscRegE316) {
		/**
		 * TODO 
		 * Faça as validações que tiver que ser feita
		 */
		return Optional.ofNullable(spedFiscRegE316Repo.saveAndFlush(spedFiscRegE316));
	}
	
	
	public Optional<SpedFiscalRegE316> findRegE111ByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		return spedFiscRegE316Repo.findByDataInicioSpedAndDataFimSped(dataInicio, dataFim);
	}
	
}
