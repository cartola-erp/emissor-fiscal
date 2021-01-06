package net.cartola.emissorfiscal.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 6 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface PessoaEnderecoRepository extends JpaRepository<PessoaEndereco, Long>{
	
	
	
}
