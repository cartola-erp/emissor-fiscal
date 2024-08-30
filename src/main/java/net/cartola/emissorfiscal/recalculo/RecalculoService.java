package net.cartola.emissorfiscal.recalculo;

import io.micrometer.core.instrument.util.JsonUtils;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import net.cartola.emissorfiscal.documento.CompraDto;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalApiController;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
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
    RecalculoRepository recalculoRepository;
    Long ifOperation;

    public Optional<DocumentoFiscal> documentoFiscalExiste(DocumentoFiscal docParaRecalculo) {

        System.out.println("Chegamos aqui no documento existente: " + docParaRecalculo);
        ifOperation = docParaRecalculo.getOperacao().getId();

        if(ifOperation == 71  || ifOperation == 34 || ifOperation == 16 || ifOperation == 19){

             Optional<DocumentoFiscal> docTribuFixas = TributacoesFixas(docParaRecalculo);
             return (docTribuFixas);
         }
         Optional<DocumentoFiscal> docComImpostoEstadualCalculado = calcularImpostoEstadual(docParaRecalculo);
         calcularImpostoFederal(docComImpostoEstadualCalculado.get());

         if(docComImpostoEstadualCalculado.isPresent()){

             DocumentoFiscal docComOprimeiroImpostoCalculado = docComImpostoEstadualCalculado.get();
             return Optional.of(docComOprimeiroImpostoCalculado);
         }
         throw new CalculaImpostoException("Erro: itens sem ncms preenchidos / informações faltantes para realizar o recalculo");
    }


    public Optional<DocumentoFiscal> documentoFiscalNaoExiste(DocumentoFiscal documentoFiscalNaoCadastrado){
        System.out.println("Chegamos aqui no documento fiscal não existente");
        documentoFiscalExiste(documentoFiscalNaoCadastrado);
        return Optional.of(documentoFiscalNaoCadastrado);
    }

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
                        throw new CalculaImpostoException("Não foi encontrado tributação estadual com a operação: " + operacaoDesc + " para o NCM: " + item.getClasseFiscal() +
                                "| VERIFICAR / CADASTRAR no EMISSOR FISCAL");
                    }
                }


                return Optional.of(documentoFiscalParaCalcularImpostoIcms);
            } else {
                throw new CalculaImpostoException("Nenhuma tributação estadual cadastrada com a operação: " + operacaoDesc + " para os NCMs da nota |" +
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
                        throw new CalculaImpostoException("Não foi encontrado tributação federal com a operação: " + operacaoDesc + " para o NCM: " + item.getClasseFiscal() +
                                "| VERIFICAR / CADASTRAR no EMISSOR FISCAL");
                    }
                }

                return Optional.of(documentoFiscalCalculado);
            } else {
                throw new CalculaImpostoException(" Nenhuma tributação federal cadastrada com a operação: " + operacaoDesc + " para os NCMs da nota. |" +
                        " NECESSARIO CADASTRAR A TRIBUTAÇÃO NO EMISSOR FISCAL");
            }
        }
        throw new CalculaImpostoException("Erro: itens sem ncms preenchidos / informações faltantes para realizar o recalculo");
    }

    // *-------- ESSE METODO IRA LIDAR COM OPERAÇOES ONDE AS TRIBUTAÇOES SÃO FIXAS, ANULAM A DO NCM ------* //
    private Optional<DocumentoFiscal>TributacoesFixas(DocumentoFiscal docComTributacaoFixa){
        System.out.println("Chegamos aqui nos documentos com tributacões fixas" + docComTributacaoFixa);

        List<DocumentoFiscalItem> itensDoDoc = docComTributacaoFixa.getItens();

        for(DocumentoFiscalItem item : itensDoDoc){

            //PIS ST
            if(ifOperation == 71){
                item.setPisCst(98); // **
            }else if(ifOperation == 16 || ifOperation == 19){
                item.setPisCst(70);
            }

            item.setPisAliquota(BigDecimal.valueOf(0.0));
            item.setPisBase(BigDecimal.valueOf(0.0));
            item.setPisValor(BigDecimal.valueOf(0.0));

            // CFOPS
            if(ifOperation == 71) {
                item.setCfop(1908); // *
            } else if (ifOperation == 34 && item.getIcmsCst() == 0) {
                item.setCfop(1556);
            }else if (ifOperation == 34 && item.getIcmsCst() == 60 ){
                item.setCfop(1407);
            } else if (ifOperation == 16 || ifOperation == 19 && item.getIcmsCst() == 0) {
                item.setIcmsAliquota(BigDecimal.valueOf(18.00));
                item.setCfop(1949);
            }else if (ifOperation == 16 || ifOperation == 19 && item.getIcmsCst() == 60){
                item.setIcmsAliquota(BigDecimal.valueOf(0.0));
                item.setCfop(1949);
            }

            /* IPI NÃO É UTILIZADO //
            item.setIpiBase(BigDecimal.valueOf(0.0));
            item.setIpiAliquota(BigDecimal.valueOf(0.0));
            item.setIpiValor(BigDecimal.valueOf(0.0));
            item.setIpiCst(0);
            */

            // COFINS ST
            if(ifOperation == 71){
                item.setCofinsCst(98); // *
            }else if (ifOperation == 16 || ifOperation == 19){
                item.setCofinsCst(70);
            }
            item.setCofinsBase(BigDecimal.valueOf(0.0));
            item.setCofinsAliquota(BigDecimal.valueOf(0.0));
            item.setCofinsValor(BigDecimal.valueOf(0.0));

            /* DEFINIR ESSAS TRIBUTAÇÃO APENAS SE PRECISAR
            item.setIcmsCest(0);
            item.setCodigoAnp(0);
            item.setIcmsFcpAliquota(BigDecimal.valueOf(0.0));
            item.setIcmsCst(41); // *
            item.setIcmsReducaoBaseAliquota(BigDecimal.valueOf(0.0));

            item.setIcmsIva(BigDecimal.valueOf(0.0));
            item.setIcmsValor(BigDecimal.valueOf(0.0));
            item.setIcmsStBase(BigDecimal.valueOf(0.0));
            item.setIcmsStBaseRetido(BigDecimal.valueOf(0));
             */
        }

        return Optional.of(docComTributacaoFixa);
    }

    public class CalculaImpostoException extends RuntimeException {
        public CalculaImpostoException(String mensagem){
            super(mensagem);
        };
    }
}
