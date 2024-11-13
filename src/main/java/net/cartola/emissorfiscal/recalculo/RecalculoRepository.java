package net.cartola.emissorfiscal.recalculo;

import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.documento.FinalidadeEmissao;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecalculoRepository extends JpaRepository<TributacaoEstadual, Long> {

    @Query("SELECT t FROM TributacaoEstadual t LEFT JOIN t.ncm n WHERE t.operacao.id = :operacaoId AND n.numero IN :ncmList and t.finalidade in :finalidade and t.estadoOrigem.sigla = :origem and t.estadoDestino.sigla = :destino")
    List<TributacaoEstadual> findImpostoEstadualByNcmAndOperacao(List ncmList, Long operacaoId, List finalidade, EstadoSigla origem, EstadoSigla destino);

    @Query("SELECT t FROM TributacaoFederal t LEFT JOIN t.ncm n WHERE t.operacao.id = :operacaoId AND n.numero IN :ncmList and t.finalidade in :finalidade ")
    List<TributacaoFederal> findImpostoFederalByNcmAndOperacao(List ncmList, Long operacaoId, List finalidade);
}

