package net.cartola.emissorfiscal.tributacao.federal;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;

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

	List<TributacaoFederal> findByOperacaoAndNcmIn(Operacao operacao, Collection<Ncm> ncms);
}
