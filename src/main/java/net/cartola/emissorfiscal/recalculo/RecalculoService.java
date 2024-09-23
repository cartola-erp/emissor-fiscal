package net.cartola.emissorfiscal.recalculo;

import io.micrometer.core.instrument.util.JsonUtils;
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
                    throw new CalculaImpostoException("Não foram encontradas tributações estaduais para os seguintes NCMS: "
                            + String.join(", ", itensSemTributacao) + " " + operacaoDesc +
                            "| VERIFICAR / CADASTRAR no EMISSOR FISCAL");
                }

                return Optional.of(documentoFiscalParaCalcularImpostoIcms);
            } else {
                throw new CalculaImpostoException("Não existe nenhuma tributação estadual cadastrada para a operação: " + operacaoDesc + " para os NCMS da nota " +
                        " NECESSARIO CADASTRAR A TRIBUTAÇÃO NO EMISSOR FISCAL");
            }
        }
        throw new CalculaImpostoException("Erro: itens sem ncms preenchidos / informações faltantes para realizar o recalculo");
    }
    public Optional<DocumentoFiscal> calcularImpostoFederal(DocumentoFiscal documentoFiscalCalculado){
        System.out.println("Chegamos aqui no documento para recalcular imposto federal" + documentoFiscalCalculado);

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
                        item.setCofinsAliquota(tributacao.getCofinsAliquota().multiply((new BigDecimal("100"))));
                        item.setCofinsBase(tributacao.getCofinsBase().multiply(new BigDecimal(100)));
                        item.setCofinsCst(tributacao.getCofinsCst());
                        item.setIpiAliquota(tributacao.getIpiAliquota());
                        item.setIpiBase(tributacao.getIpiBase());
                        item.setIpiCst(tributacao.getIpiCst());
                        item.setPisAliquota(tributacao.getPisAliquota().multiply(new BigDecimal("100"))); // Divisão por 100
                        item.setPisBase(tributacao.getPisBase().multiply(new BigDecimal("100")));
                        item.setPisCst(tributacao.getPisCst());
                    }else{
                        itensSemTributacao.add(item.getClasseFiscal());
                    }
                }
                if (!itensSemTributacao.isEmpty()) {
                    throw new CalculaImpostoException("Não foram encontradas tributações federal para os seguintes NCMS: "
                            + String.join(", ", itensSemTributacao ) + " " + operacaoDesc +
                            "| VERIFICAR / CADASTRAR no EMISSOR FISCAL");
                }

                return Optional.of(documentoFiscalCalculado);
            } else {
                throw new CalculaImpostoException(" Não existe nenhuma tributação federal cadastrada com a operação: " + operacaoDesc + " para os NCMS da nota. |" +
                        " NECESSARIO CADASTRAR A TRIBUTAÇÃO NO EMISSOR FISCAL");
            }
        }
        throw new CalculaImpostoException("Erro: itens sem ncms preenchidos / informações faltantes para realizar o recalculo");
    }

    // *-------- ESSE METODO IRA LIDAR COM OPERAÇOES ONDE AS TRIBUTAÇOES SÃO FIXAS, ANULAM A DO NCM ------* //
    private Optional<DocumentoFiscal> docFixoTributacao(DocumentoFiscal documentoTribuFixa){
        System.out.println("teste");
        return Optional.of(documentoTribuFixa);
    }

    public static class CalculaImpostoException extends RuntimeException {
        public CalculaImpostoException(String mensagem){
            super(mensagem);
        };
    }
}
