package net.cartola.emissorfiscal.contador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 13 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface ContadorRepository extends JpaRepository<Contador, Long> {

}
