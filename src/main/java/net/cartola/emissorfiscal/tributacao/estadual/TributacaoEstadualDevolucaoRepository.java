package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cartola.emissorfiscal.operacao.Operacao;

/**
 * @date 30 de set. de 2021
 * @author robson.costa
 */
@Repository
public interface TributacaoEstadualDevolucaoRepository extends JpaRepository<TributacaoEstadualDevolucao, Long> {
	
	Set<TributacaoEstadualDevolucao> findByOperacao(Operacao operacao);
	
}
