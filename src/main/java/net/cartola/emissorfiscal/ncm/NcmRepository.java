package net.cartola.emissorfiscal.ncm;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NcmRepository extends JpaRepository<Ncm, Long> {

//	@Query("SELECT n FROM Ncm n WHERE n.numero LIKE %:numero%")
	@Query("SELECT n FROM Ncm n WHERE n.numero = :numero")
	List<Ncm> findNcmByNumero(int numero);
	
	Page<Ncm> findNcmByNumero(int numero, Pageable pageable);
	
	/**
	 * Verificando se já existe Uma combinação de NUMERO e EXCECAO
	 * @param numero
	 * @param excecao
	 * @return
	 */
	@Query("SELECT n FROM Ncm n WHERE n.numero = :numero AND n.excecao = :excecao")
	Optional<Ncm> findNumeroAndExcecao(int numero, int excecao);

	List<Ncm> findNcmByNumeroIn(Collection<Integer> listNumerosNcms);

}
