package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;

public interface TributacaoEstadualRepository extends JpaRepository<TributacaoEstadual, Long> {
	
//	List<TributacaoEstadual> findByNcm(List<Ncm> ncms);
	List<TributacaoEstadual> findByNcm(Ncm ncm);
	
	List<TributacaoEstadual> findByNcmIn(Collection<Ncm> ncm);

	List<TributacaoEstadual> findByOperacaoAndNcmIn(Operacao operacao, Collection<Ncm> ncms);

}
