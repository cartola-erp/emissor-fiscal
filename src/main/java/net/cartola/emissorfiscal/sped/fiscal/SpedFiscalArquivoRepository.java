package net.cartola.emissorfiscal.sped.fiscal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 21 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface SpedFiscalArquivoRepository extends JpaRepository<SpedFiscalArquivo, Long> {

}
