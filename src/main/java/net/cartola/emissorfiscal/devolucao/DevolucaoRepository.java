package net.cartola.emissorfiscal.devolucao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.operacao.Operacao;

/**
 * @date 17 de set. de 2021
 * @author robson.costa
 */
@Repository
public interface DevolucaoRepository extends JpaRepository<Devolucao, Long> {

	Optional<Devolucao> findByDocumentoAndLoja(int nfeNumeroErp, Loja loja);

	/**
	 * Irá Buscar as <b>DEVOLUÇÕES<b> pelos parâmetros abaixo: 
	 * 
	 * @param documento
	 * @param lojaCnpj
	 * @param emitenteCnpj
	 * @param operacao
	 * @return
	 */
	Optional<Devolucao> findByDocumentoAndLojaCnpjAndEmitenteCnpjAndOperacao(int documento, String lojaCnpj, String emitenteCnpj, Operacao operacao);
	
	
}
