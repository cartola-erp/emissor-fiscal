package net.cartola.emissorfiscal.tributacao.federal;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 6 de nov de 2019
 * 
 * @author gregory.feijon
 */
public interface TributacaoFederalRepository extends JpaRepository<TributacaoFederal, Long> {

	List<TributacaoFederal> findByNcm(Ncm ncm);

	List<TributacaoFederal> findByNcmIn(Collection<Ncm> ncms);

	List<TributacaoFederal> findByOperacao(Operacao operacao);

	List<TributacaoFederal> findByOperacaoIn(Collection<Operacao> operacoes);

	Page<TributacaoFederal> findByNcmIn(Collection<Ncm> ncms, Pageable pr);

	Set<TributacaoFederal> findByOperacaoAndRegimeTributarioAndFinalidadeInAndNcmIn(Operacao operacao, RegimeTributario regimeTributario, 
			Collection<Finalidade> finalidade, Collection<Ncm> ncms);

	@Query("SELECT t FROM TributacaoFederal t WHERE t.id = :tribuId")
	TributacaoFederal findByIdTribu(@Param("tribuId")Long id);
}
