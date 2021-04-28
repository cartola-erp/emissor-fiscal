package net.cartola.emissorfiscal.produto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 27 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface ProdutoAlteradoSpedRepository extends JpaRepository<ProdutoAlteradoSped, Long> {

	/**
	 * Buscando Todos os Produtos, alterado cujo estejam na Collection de CodigosErp E
	 * a dataUsadaSpedInicio == Ao PeriodoSped OU == null
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @param object
	 * @param setCodigoProdutoErp
	 * @return
	 */
	List<ProdutoAlteradoSped> findByDataUsadaSpedInicioBetweenOrDataUsadaSpedInicioAndProdutoCodigoErpIn(
			LocalDate dataInicio, LocalDate dataFim, Object object, Collection<Integer> setCodigoProdutoErp);

}
