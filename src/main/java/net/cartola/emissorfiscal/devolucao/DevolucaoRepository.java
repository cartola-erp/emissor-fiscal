package net.cartola.emissorfiscal.devolucao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cartola.emissorfiscal.loja.Loja;

/**
 * @date 17 de set. de 2021
 * @author robson.costa
 */
@Repository
public interface DevolucaoRepository extends JpaRepository<Devolucao, Long> {

	Optional<Devolucao> findByDocumentoAndLoja(int nfeNumeroErp, Loja loja);
	
	
}
