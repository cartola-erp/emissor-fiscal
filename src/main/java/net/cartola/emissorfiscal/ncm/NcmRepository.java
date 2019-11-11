package net.cartola.emissorfiscal.ncm;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NcmRepository extends JpaRepository<Ncm, Long> {
	


//	@Query("SELECT n FROM Ncm n WHERE n.numero LIKE %:numero%")
	@Query("SELECT n FROM Ncm n WHERE n.numero = :numero")
	Optional<Ncm> findNcmByNumero(int numero);
	
	
	/**
	 * Verificando se já existe Uma combinação de NUMERO e EXCECAO
	 * @param numero
	 * @param excecao
	 * @return
	 */
	@Query("SELECT n FROM Ncm n WHERE n.numero = :numero AND n.excecao = :excecao")
	Optional<Ncm> findNumeroAndExcecao(int numero, int excecao);
}
