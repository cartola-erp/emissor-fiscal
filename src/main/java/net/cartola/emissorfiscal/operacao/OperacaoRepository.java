package net.cartola.emissorfiscal.operacao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {
	


	@Query("SELECT o FROM Operacao o WHERE o.descricao LIKE %:descricaoOperacao%")
	List<Operacao> findOperacaoByParteDaDescricao (String descricaoOperacao);
	
	
	/**
	 * Pesquisa se a operação informada já existe
	 * @param descricaoOperacao
	 * @return
	 */
//	@Query("SELECT o FROM Operacao o WHERE o.descricao = :descricaoOperacao")
	Optional<Operacao> findOperacaoByDescricaoIgnoreCase(String descricao);


	List<Operacao> findByIdIn(Set<Long> operacaoIds);

	
}
