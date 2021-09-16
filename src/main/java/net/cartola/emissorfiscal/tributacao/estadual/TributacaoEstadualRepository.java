package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;

public interface TributacaoEstadualRepository extends JpaRepository<TributacaoEstadual, Long> {
	
//	List<TributacaoEstadual> findByNcm(List<Ncm> ncms);
	List<TributacaoEstadual> findByNcm(Ncm ncm);
	
	List<TributacaoEstadual> findByNcmIn(Collection<Ncm> ncm);
	
	Page<TributacaoEstadual> findByNcmIn(List<Ncm> ncms, Pageable pr);
	
	List<TributacaoEstadual> findByOperacaoAndEstadoOrigemAndEstadoDestinoAndRegimeTributarioAndFinalidadeInAndNcmIn(Operacao operacao, 
			Estado estadoOrigem, Estado estadoDestino,
			RegimeTributario regimeTributario,
			Collection<Finalidade> finalidade, Collection<Ncm> ncms);

	
	/**
	 * Irá buscar a tributação interestadual
	 * 
	 * @param regimeTributario
	 * @param estaOrigId
	 * @param estaDestId
	 * @return Set<TributacaoEstadual>, poderá ser vazio, ou conter 2 tributações interestaduais. (1 para produtos NACIONAIS e outra para os IMPORTADOS)
	 */
	@Query(value = "SELECT t FROM TributacaoEstadual t INNER JOIN t.operacao o INNER JOIN t.estadoOrigem origem INNER JOIN t.estadoDestino d "
				+ " WHERE o.id = 3 AND t.regimeTributario = :regimeTributario  AND origem.sigla = :ufOrigem AND d.sigla = :ufDestino GROUP BY t.produtoImportado")
	Set<TributacaoEstadual> findByRegimeTributarioAndOrigemSiglaAndDestinoSigla(RegimeTributario regimeTributario, EstadoSigla ufOrigem, EstadoSigla ufDestino);
	
	
	
}
