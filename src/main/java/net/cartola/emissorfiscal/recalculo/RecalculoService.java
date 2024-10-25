package net.cartola.emissorfiscal.recalculo;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import net.cartola.emissorfiscal.documento.*;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecalculoService {

    @Autowired
    DocumentoFiscalItemRepository documentoFiscalItemRepository;

    @Autowired
    RecalculoRepository recalculoRepository;
    Long ifOperation;

    public Optional<DocumentoFiscal> documentoFiscalExiste(DocumentoFiscal docParaRecalculo) {

        ifOperation = docParaRecalculo.getOperacao().getId();
        Optional<DocumentoFiscal> docComImpostoEstadualCalculado = calcularImpostoEstadual(docParaRecalculo);
        calcularImpostoFederal(docComImpostoEstadualCalculado.get());

         if(docComImpostoEstadualCalculado.isPresent()){

             DocumentoFiscal docComOprimeiroImpostoCalculado = docComImpostoEstadualCalculado.get();
             return Optional.of(docComOprimeiroImpostoCalculado);
         }
         throw new CalculaImpostoException("Erro: itens sem ncms preenchidos / informações faltantes para realizar o recalculo");
    }

    /*
    public Optional<DocumentoFiscal> documentoFiscalNaoExiste(DocumentoFiscal documentoFiscalNaoCadastrado){
        documentoFiscalExiste(documentoFiscalNaoCadastrado);
        return Optional.of(documentoFiscalNaoCadastrado);
    }
    */

    public Optional<DocumentoFiscal> calcularImpostoEstadual(DocumentoFiscal documentoFiscalParaCalcularImpostoIcms){

        Long operacaoId = documentoFiscalParaCalcularImpostoIcms.getOperacao().getId();
        String operacaoDesc = documentoFiscalParaCalcularImpostoIcms.getOperacao().getDescricao();

        List<DocumentoFiscalItem> itens = documentoFiscalParaCalcularImpostoIcms.getItens();
        boolean todosItensTemNcmPreenchido = true;
        List<String> ncms = new ArrayList<>();

        for (DocumentoFiscalItem item : itens) {
            if (item.getClasseFiscal() == null || item.getClasseFiscal().isEmpty()) {
                todosItensTemNcmPreenchido = false;
                System.out.println("Item sem NCM preenchido: " + item);
                break;
            } else {
                ncms.add(item.getClasseFiscal());
            }
        }

        if (todosItensTemNcmPreenchido && operacaoId != null) {
            List<Integer> ncmList = ncms.stream()
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            List<TributacaoEstadual> tributacoes = recalculoRepository.findImpostoEstadualByNcmAndOperacao(ncmList, operacaoId);

            if (!tributacoes.isEmpty()) {
                System.out.println("Tributações encontradas: " + tributacoes);

                //Verifica se tem tributaçoes duplicadas
                Map<Integer, TributacaoEstadual> tributacaoMap = tributacoes.stream()
                        .collect(Collectors.toMap(
                                t -> t.getNcm().getNumero(),
                                t -> t,
                                (existing, replacement) -> existing
                        ));

                List<String> itensSemTributacao = new ArrayList<>();
                for (DocumentoFiscalItem item : itens) {
                    int itemNcm = Integer.parseInt(item.getClasseFiscal());

                    // Encontre a tributação correspondente ao NCM do item
                    // Se necessario preciso adicionar depois o campo para verificar finalidades
                    TributacaoEstadual tributacao = tributacoes.stream()
                            .filter(t -> t.getNcm().getNumero() == itemNcm)
                            .findFirst()
                            .orElse(null);

                    if (tributacao != null) {
                        // Atualize os campos do item com os valores da tributação
                        item.setIcmsCst(tributacao.getIcmsCst());
                        item.setIcmsBase(tributacao.getIcmsBase());
                        item.setIcmsAliquota(tributacao.getIcmsAliquota());
                        item.setIcmsIva(tributacao.getIcmsIva());
                        item.setIcmsAliquotaDestino(tributacao.getIcmsAliquotaDestino());
                        item.setIcmsFcpAliquota(tributacao.getFcpAliquota());
                        item.setIcmsStAliquota(tributacao.getIcmsStAliquota());
                        item.setIcmsCest(tributacao.getIcmsCst());
                        item.setCfop(tributacao.getCfop());
                        item.setCodigoAnp(tributacao.getCodigoAnp());
                    }else {
                        itensSemTributacao.add(item.getClasseFiscal());
                    }
                }
                if (!itensSemTributacao.isEmpty()) {
                    throw new CalculaImpostoException(
                            "Não foram encontradas as TRIBUTAÇOES ESTADUAIS para os NCMS( " + String.join(", ", itensSemTributacao) + " )."
                                    + " | VERIFICAR / CADASTRAR no EMISSOR FISCAL com a OPERAÇÃO descrita na nota.");
                }

                return Optional.of(documentoFiscalParaCalcularImpostoIcms);
            } else {
                throw new CalculaImpostoException("Nenhum NCM da nota, possui TRIBUTAÇÃO ESTADUAL cadastrada. Favor conferir no EMISSOR FISCAL.");
            }
        }
        throw new CalculaImpostoException("Erro: itens sem ncms preenchidos / informações faltantes para realizar o recalculo");
    }
    public Optional<DocumentoFiscal> calcularImpostoFederal(DocumentoFiscal documentoFiscalCalculado){

        Long operacaoId = documentoFiscalCalculado.getOperacao().getId();
        String operacaoDesc = documentoFiscalCalculado.getOperacao().getDescricao();

        List<DocumentoFiscalItem> itens = documentoFiscalCalculado.getItens();
        boolean todosItensTemNcmPreenchido = true;
        List<String> ncms = new ArrayList<>();

        for (DocumentoFiscalItem item : itens) {
            if (item.getClasseFiscal() == null || item.getClasseFiscal().isEmpty()) {
                todosItensTemNcmPreenchido = false;
                System.out.println("Item sem NCM preenchido: " + item);
                break;
            } else {
                ncms.add(item.getClasseFiscal());
            }
        }

        if (todosItensTemNcmPreenchido && operacaoId != null) {
            List<Integer> ncmList = ncms.stream()
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            List<TributacaoFederal> tributacoes = recalculoRepository.findImpostoFederalByNcmAndOperacao(ncmList, operacaoId);

            if (!tributacoes.isEmpty()) {
                System.out.println("Tributações encontradas: " + tributacoes);

                //Verifica se tem tributaçoes duplicadas
                Map<Integer, TributacaoFederal> tributacaoMap = tributacoes.stream()
                        .collect(Collectors.toMap(
                                t -> t.getNcm().getNumero(),
                                t -> t,
                                (existing, replacement) -> existing
                        ));

                List<String> itensSemTributacao = new ArrayList<>();
                for (DocumentoFiscalItem item : itens) {
                    int itemNcm = Integer.parseInt(item.getClasseFiscal());

                    // Encontre a tributação correspondente ao NCM do item
                    // Se necessario preciso adicionar depois o campo para verificar finalidades
                    TributacaoFederal tributacao = tributacoes.stream()
                            .filter(t -> t.getNcm().getNumero() == itemNcm)
                            .findFirst()
                            .orElse(null);

                    if (tributacao != null) {
                        // Atualize os campos do item com os valores da tributação
                        item.setCofinsAliquota(tributacao.getCofinsAliquota());
                        item.setCofinsBase(tributacao.getCofinsBase());
                        item.setCofinsCst(tributacao.getCofinsCst());
                        item.setIpiAliquota(tributacao.getIpiAliquota());
                        item.setIpiBase(tributacao.getIpiBase());
                        item.setIpiCst(tributacao.getIpiCst());
                        item.setPisAliquota(tributacao.getPisAliquota());
                        item.setPisBase(tributacao.getPisBase());
                        item.setPisCst(tributacao.getPisCst());
                    }else{
                        itensSemTributacao.add(item.getClasseFiscal());
                    }
                }
                if (!itensSemTributacao.isEmpty()) {
                    throw new CalculaImpostoException(
                            "Não foram encontradas TRIBUTAÇOES FEDERAIS para os NCMS( " +String.join(", ", itensSemTributacao ) + " )"
                                    + " | VERIFICAR / CADASTRAR no EMISSOR FISCAL com a OPERAÇÃO descrita na nota.");
                }

                return Optional.of(documentoFiscalCalculado);
            } else {
                throw new CalculaImpostoException("Nenhum NCM da nota, possui TRIBUTAÇÃO FEDERAL cadastrada. Favor conferir no EMISSOR FISCAL.");
            }
        }
        throw new CalculaImpostoException("Erro: itens sem ncms preenchidos / informações faltantes para realizar o recalculo");
    }

    public static class CalculaImpostoException extends RuntimeException {
        public CalculaImpostoException(String mensagem){
            super(mensagem);
        };
    }
}
