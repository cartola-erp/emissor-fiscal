package net.cartola.emissorfiscal.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 27 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface ProdutoAlteradoSpedRepository extends JpaRepository<ProdutoAlteradoSped, Long> {

}
