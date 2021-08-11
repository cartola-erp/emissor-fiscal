package net.cartola.emissorfiscal.produto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	 * 	@Query(value = " SELECT * FROM prod_alte_sped WHERE data_usada_sped_inicio BETWEEN :dataInicio AND :dataFim "
	 *	+ "	OR data_usada_sped_inicio = :orDtSpedInicio AND prod_cod_erp IN (:listCodigoProdutoErp) ;" , nativeQuery = true)
	 * 		
	 * @param dataInicio
	 * @param dataFim
	 * @param object
	 * @param listCodigoProdutoErp
	 * @return
	 */
	@Query(value = " SELECT * FROM prod_alte_sped WHERE data_usada_sped_inicio BETWEEN :dataInicio AND :dataFim "
			+ "	OR data_usada_sped_inicio = :orDtSpedInicio AND prod_cod_erp IN (:listCodigoProdutoErp) ;" , nativeQuery = true)
	List<ProdutoAlteradoSped> findByDataUsadaSpedInicioBetweenOrDataUsadaSpedInicioAndProdutoCodigoErpIn(
			LocalDate dataInicio, LocalDate dataFim, Object orDtSpedInicio, Collection<Integer> listCodigoProdutoErp);

	
}
