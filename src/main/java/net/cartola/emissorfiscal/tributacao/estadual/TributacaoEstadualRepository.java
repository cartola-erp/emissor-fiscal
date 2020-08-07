package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
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

}
