package net.cartola.emissorfiscal.devolucao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 17 de set. de 2021
 * @author robson.costa
 */
@Repository
public interface DevolucaoRepository extends JpaRepository<Devolucao, Long> {
	
	
}
