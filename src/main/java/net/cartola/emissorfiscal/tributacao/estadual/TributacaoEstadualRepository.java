package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.cartola.emissorfiscal.ncm.Ncm;

public interface TributacaoEstadualRepository extends JpaRepository<TributacaoEstadual, Long> {
	
//	List<TributacaoEstadual> findByNcm(List<Ncm> ncms);
	List<TributacaoEstadual> findByNcm(Ncm ncm);
	
	List<TributacaoEstadual> findByNcmIn(Collection<Ncm> ncm);

}
