package net.cartola.emissorfiscal.tributacao.federal;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;

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

	List<TributacaoFederal> findByOperacaoAndRegimeTributarioAndFinalidadeInAndNcmIn(Operacao operacao, RegimeTributario regimeTributario, 
			Collection<Finalidade> finalidade, Collection<Ncm> ncms);

}
