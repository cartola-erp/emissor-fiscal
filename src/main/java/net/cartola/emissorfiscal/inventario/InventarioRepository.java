package net.cartola.emissorfiscal.inventario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 19 de nov. de 2021
 * @author robson.costa
 */
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

	
}
