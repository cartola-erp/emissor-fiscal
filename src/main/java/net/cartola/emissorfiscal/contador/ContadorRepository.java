package net.cartola.emissorfiscal.contador;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cartola.emissorfiscal.loja.Loja;

/**
 * @date 13 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface ContadorRepository extends JpaRepository<Contador, Long> {

	Optional<Contador> findContadorByCnpj(String cnpj);

	Optional<Contador> findContadorByCpf(String cpf);
	
	Optional<Contador> findContadorByCrc(String cnpj);
	
	
}
