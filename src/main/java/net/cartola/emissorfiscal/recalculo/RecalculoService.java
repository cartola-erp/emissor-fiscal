package net.cartola.emissorfiscal.recalculo;

import br.com.autogeral.emissorfiscal.vo.ItemModel;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecalculoService {

    @Autowired
    RecalculoRepository recalculoRepository;
    Long ifOperation;

    public Optional<DocumentoFiscal> documentoFiscalExiste(DocumentoFiscal docParaRecalculo) {

        ifOperation = docParaRecalculo.getOperacao().getId();
        Optional<DocumentoFiscal> docComImpostoEstadualCalculado = calcularImpostoEstadual(docParaRecalculo);
        calcularImpostoFederal(docComImpostoEstadualCalculado.get());

        if (docComImpostoEstadualCalculado.isPresent()) {

            DocumentoFiscal docComOprimeiroImpostoCalculado = docComImpostoEstadualCalculado.get();
            return Optional.of(docComOprimeiroImpostoCalculado);
        }
        throw new CalculaImpostoException("Erro: itens sem ncms preenchidos / informações faltantes para realizar o recalculo");
    }

    public Optional<DocumentoFiscal> calcularImpostoEstadual(DocumentoFiscal documentoFiscalParaCalcularImpostoIcms) {

        Long operacaoId = documentoFiscalParaCalcularImpostoIcms.getOperacao().getId();
        EstadoSigla estadoOrigem = documentoFiscalParaCalcularImpostoIcms.getEmitente().getEndereco().getUf();
        EstadoSigla estadoDestino = documentoFiscalParaCalcularImpostoIcms.getDestinatario().getEndereco().getUf();

        List<DocumentoFiscalItem> itens = documentoFiscalParaCalcularImpostoIcms.getItens();
        boolean todosItensTemNcmPreenchido = true;
        List<String> ncms = new ArrayList<>();
        List<Finalidade> finalidade = new ArrayList<>();

        for (DocumentoFiscalItem item : itens) {
            if (item.getClasseFiscal() == null || item.getClasseFiscal().isEmpty()) {
                todosItensTemNcmPreenchido = false;
                System.out.println("Item sem NCM preenchido: " + item);
                break;
            } else {
                ncms.add(item.getClasseFiscal());
                finalidade.add(item.getFinalidadeEmpresa());
            }
        }

        if (todosItensTemNcmPreenchido && operacaoId != null) {
            List<Integer> ncmList = ncms.stream()
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            List<TributacaoEstadual> tributacoes = recalculoRepository.findImpostoEstadualByNcmAndOperacao(ncmList, operacaoId, finalidade, estadoOrigem, estadoDestino);

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
                    } else {
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

    public Optional<DocumentoFiscal> calcularImpostoFederal(DocumentoFiscal documentoFiscalCalculado) {

        Long operacaoId = documentoFiscalCalculado.getOperacao().getId();


        List<DocumentoFiscalItem> itens = documentoFiscalCalculado.getItens();
        boolean todosItensTemNcmPreenchido = true;
        List<String> ncms = new ArrayList<>();
        List<Finalidade> finalidade = new ArrayList<>();

        for (DocumentoFiscalItem item : itens) {
            if (item.getClasseFiscal() == null || item.getClasseFiscal().isEmpty()) {
                todosItensTemNcmPreenchido = false;
                System.out.println("Item sem NCM preenchido: " + item);
                break;
            } else {
                ncms.add(item.getClasseFiscal());
                finalidade.add(item.getFinalidadeEmpresa());
            }
        }

        if (todosItensTemNcmPreenchido && operacaoId != null) {
            List<Integer> ncmList = ncms.stream()
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            List<TributacaoFederal> tributacoes = recalculoRepository.findImpostoFederalByNcmAndOperacao(ncmList, operacaoId, finalidade);

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
                    } else {
                        itensSemTributacao.add(item.getClasseFiscal());
                    }
                }
                if (!itensSemTributacao.isEmpty()) {
                    throw new CalculaImpostoException(
                            "Não foram encontradas TRIBUTAÇOES FEDERAIS para os NCMS( " + String.join(", ", itensSemTributacao) + " )"
                                    + " | VERIFICAR / CADASTRAR no EMISSOR FISCAL com a OPERAÇÃO descrita na nota.");
                }

                return Optional.of(documentoFiscalCalculado);
            } else {
                throw new CalculaImpostoException("Nenhum NCM da nota, possui TRIBUTAÇÃO FEDERAL cadastrada. Favor conferir no EMISSOR FISCAL.");
            }
        }
        throw new CalculaImpostoException("Erro: itens sem ncms preenchidos / informações faltantes para realizar o recalculo");
    }

    public List<TributacaoEstadual> carregarTributacaoEstadualForNfce(List<ItemModel> itens, EstadoSigla siglaOrigem, EstadoSigla siglaDestino, Long operacao) {
        List<Integer> listaNcm = new ArrayList<>();

        for (ItemModel itemNcm : itens) {
            listaNcm.add(Integer.parseInt(itemNcm.getNcm()));
        }

        List<Finalidade> finalidade = new ArrayList<>();
        finalidade.add(Finalidade.CONSUMO);

        return recalculoRepository.findImpostoEstadualByNcmAndOperacao(listaNcm, operacao, finalidade, siglaOrigem, siglaDestino);
    }

    public List<TributacaoFederal> carregarTributacaoFederalForNfce(List<ItemModel> itens, Long operacao) {

        List<Integer> listaNcm = new ArrayList<>();

        for (ItemModel itemNcm : itens) {
            listaNcm.add(Integer.parseInt(itemNcm.getNcm()));
        }

        List<Finalidade> finalidade = new ArrayList<>();
        finalidade.add(Finalidade.CONSUMO);

        return recalculoRepository.findImpostoFederalByNcmAndOperacao(listaNcm, operacao, finalidade);
    }

    public static class CalculaImpostoException extends RuntimeException {
        public CalculaImpostoException(String mensagem) {
            super(mensagem);
        }
    }
}
