package net.cartola.emissorfiscal.inventario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 19 de nov. de 2021
 * @author robson.costa
 */
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

	List<Inventario> findByLojaId(Long lojaId);

	Optional<Inventario> findByLojaIdAndInicioAndFim(Long id, LocalDate dataInicioInventario, LocalDate dataFimInventario);

	
}
