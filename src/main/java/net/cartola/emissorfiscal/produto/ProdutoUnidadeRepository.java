package net.cartola.emissorfiscal.produto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 20 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface ProdutoUnidadeRepository extends JpaRepository<ProdutoUnidade, Long> {
	
	Optional<ProdutoUnidade> findProdutoUnidadeBySigla(String sigla);
	
	List<ProdutoUnidade> findProdutoUnidadeBySiglaIn(Collection<String> listSiglas);
	
}
