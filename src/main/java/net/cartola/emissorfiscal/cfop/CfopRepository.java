package net.cartola.emissorfiscal.cfop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CfopRepository extends JpaRepository<Cfop, Long> {
	


//	@Query("SELECT n FROM Ncm n WHERE n.numero LIKE %:numero%")
	@Query("SELECT c FROM Cfop c WHERE c.numero = :numero")
	List<Cfop> findNcmByNumero(int numero);
	
	
	/**
	 * Verificando se já existe Uma combinação de NUMERO e EXCECAO
	 * @param numero
	 * @param excecao
	 * @return
	 */
//	@Query("SELECT c FROM Cfop c WHERE c.numero = :numero AND c.excecao = :excecao")
//	Optional<Cfop> findNumeroAndExcecao(int numero, int excecao);
	
}
