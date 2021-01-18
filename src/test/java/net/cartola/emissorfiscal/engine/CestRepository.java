package net.cartola.emissorfiscal.engine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 15 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface CestRepository extends JpaRepository<CestModel, Integer> {

}
