package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;

/**
  * @date 8 de mar. de 2021
  * @author robson.costa
  */
@Repository
public interface TributacaoEstadualGuiaRepository  extends JpaRepository<TributacaoEstadualGuia, Long> {

	List<TributacaoEstadualGuia> findByNcm(Ncm ncm);
	
	List<TributacaoEstadualGuia> findByNcmIn(Collection<Ncm> ncm);
	
	Page<TributacaoEstadualGuia> findByNcmIn(List<Ncm> ncms, Pageable pr);

	List<TributacaoEstadualGuia> findByTipoGuiaAndEstadoOrigemAndEstadoDestinoAndProdutoOrigemInAndOperacaoAndNcmIn(
			TipoGuia tipoGuia, Estado estadoOrigem, Estado estadoDestino, Collection<ProdutoOrigem> produtoOrigens,
			Operacao operacao, Collection<Ncm> ncms);

	
	
}
