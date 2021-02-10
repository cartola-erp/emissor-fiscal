package net.cartola.emissorfiscal.loja;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 21 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {

	Optional<Loja> findLojaByCnpj(String cnpj);
	
}
