package net.cartola.emissorfiscal.emissao;


import autogeral.emissorfiscal.vo.*;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.recalculo.RecalculoService;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

@Service
public class EmissaoPrenchimentoDadosFiscaisService {

    private static final NumberFormat CEST_FORMATTER = NumberFormat.getIntegerInstance();

    static {
        CEST_FORMATTER.setMinimumIntegerDigits(7);
        CEST_FORMATTER.setMaximumIntegerDigits(7);
        CEST_FORMATTER.setGroupingUsed(false);
    }

    @Autowired
    RecalculoService recalculoService;

    /**
     * Esse metodo deve prencher todos dados da tributacao para a nota fiscal passada como argumento
     * o mapa de retorno, deve informaXr o status do processamento e os erros, caso existam (do map) :
     */

    public ResultadoUtil preencherDadosFiscais(InvoiceModel invoice) {

        Map<String, Map<String, List<String>>> result = new HashMap<>();

        List<ItemModel> ncmsDosItensDaInvoice = invoice.getItems();
        ResultadoUtil resultado = new ResultadoUtil();
        List<String> itensSemNcm = new ArrayList<>();
        Map<String, List<String>> detalhesErros = new HashMap<>();

        if(ncmsDosItensDaInvoice == null || ncmsDosItensDaInvoice.isEmpty()){
            detalhesErros.put("erro", Collections.singletonList("A nota esta sem itens cadastrados"));
            result.put("itensNotaEstaVazio", detalhesErros);
            resultado.setErros(result);
            return resultado;
        }

        for (ItemModel itemNcm : ncmsDosItensDaInvoice) {
            String ncm = itemNcm.getNcm();
            if (ncm == null || ncm.trim().isEmpty()) {
                itensSemNcm.add(String.valueOf(itemNcm.getDescription()));
                detalhesErros.put("ItensInvalidosSemNcm", itensSemNcm);
                result.put("falha", detalhesErros);
                resultado.setErros(result);
                return resultado;
            }
        }

        preencheTributacaoInvoice(ncmsDosItensDaInvoice, detalhesErros, invoice);

        if (!detalhesErros.isEmpty()) {
            result.put("ErroNoPreenchimentoDaTributacao", detalhesErros);
            resultado.setErros(result);
            return resultado;
        }

        Map<String, List<String>> detalhesSucesso = new HashMap<>();
        List<String> mensagemSucesso = new ArrayList<>();
        mensagemSucesso.add("NenhumErroEncontrado");
        detalhesSucesso.put("Concluido", mensagemSucesso);
        result.put("SucessoAoRealizarOPreenchimentoDosDadosFiscais: ", detalhesSucesso);

        resultado.setInvoice(invoice);
        resultado.setErros(result);
        return resultado;
    }

    public void preencheTributacaoInvoice(List<ItemModel> itensDaInvoice, Map<String, List<String>> erros, InvoiceModel invoice) {

        if (invoice.getUfOrigem() == null || invoice.getUfOrigem().isEmpty()) {
            erros.put("UfOrigem", Collections.singletonList("Uf de origem da nota não pode estar nulo / vazio"));
        }

        if (invoice.getBuyer() == null) {
            erros.put("Cliente", Collections.singletonList("Os dados do cliente nao podem estar nulo / vazio"));
        } else if (invoice.getBuyer().getAddress() == null) {
            erros.put("EnderecoCliente", Collections.singletonList("O endereco do cliente nao pode estar nulo / vazio "));
        } else if (invoice.getBuyer().getAddress().getAdditionalInformation() == null || invoice.getBuyer().getAddress().getAdditionalInformation().isEmpty()) {
            erros.put("UfDestino", Collections.singletonList("Uf de destino da nota não pode estar nulo / vazio"));
        }

        if (invoice.getOperationType() == null || invoice.getOperationType().isEmpty()) {
            erros.put("IdDaOperacao", Collections.singletonList("Id da operação não pode estar nulo / vazio"));
        }

        if (!erros.isEmpty()) return; // Encerrar caso existam erros de validação.

        EstadoSigla estadoOrigem, estadoDestino;
        long operacaoid;
        try {
            estadoOrigem = EstadoSigla.valueOf(invoice.getUfOrigem());
            estadoDestino = EstadoSigla.valueOf(invoice.getBuyer().getAddress().getAdditionalInformation());
            operacaoid = Long.parseLong(invoice.getOperationType());
        } catch (IllegalArgumentException e) {
            erros.put("DadosInvalidos", Collections.singletonList("Uf ou operação inválidos"));
            return;
        }

        // Buscar tributações
        List<TributacaoEstadual> tributacaoEstadual = recalculoService
                .TributacaoEstadualForNfce(itensDaInvoice, estadoOrigem, estadoDestino, operacaoid);

        List<TributacaoFederal> tributacaoFederal = recalculoService
                .TributacaoFederalForNfce(itensDaInvoice, operacaoid);

        // Verificar itens sem tributação
        List<String> ncmsSemTributacaoEstadual = new ArrayList<>();
        List<String> ncmsSemTributacaoFederal = new ArrayList<>();

        for (ItemModel item : itensDaInvoice) {
            int ncm = Integer.parseInt(item.getNcm());

            boolean temTributacaoEstadual = tributacaoEstadual.stream()
                    .anyMatch(t -> t.getNcm().getNumero() == ncm);

            boolean temTributacaoFederal = tributacaoFederal.stream()
                    .anyMatch(t -> t.getNcm().getNumero() == ncm);

            if (!temTributacaoEstadual) ncmsSemTributacaoEstadual.add(String.valueOf(ncm));
            if (!temTributacaoFederal) ncmsSemTributacaoFederal.add(String.valueOf(ncm));
        }

        if (!ncmsSemTributacaoEstadual.isEmpty() || !ncmsSemTributacaoFederal.isEmpty()) {
            erros.put("ncmsSemTributacaoEstadual", ncmsSemTributacaoEstadual);
            erros.put("ncmsSemTributacaoFederal", ncmsSemTributacaoFederal);
            return;
        }

        // Preencher impostos nos itens
        for (ItemModel item : itensDaInvoice) {
            int ncm = Integer.parseInt(item.getNcm());

            TributacaoEstadual tributacao = tributacaoEstadual.stream()
                    .filter(t -> t.getNcm().getNumero() == ncm)
                    .findFirst()
                    .orElse(null);

            TributacaoFederal tributacaoFederal1 = tributacaoFederal.stream()
                    .filter(t -> t.getNcm().getNumero() == ncm)
                    .findFirst()
                    .orElse(null);

            if (tributacao != null) {
                // Preencher ICMS
                TaxModel imposto = new TaxModel();
                IcmsModel icms = new IcmsModel();

                BigDecimal icmsBase = BigDecimal.valueOf(item.getTotalAmount());
                BigDecimal icmsAliquota = tributacao.getIcmsAliquota().multiply(BigDecimal.valueOf(100));
                BigDecimal icmsAmount = icmsBase.multiply(icmsAliquota.divide(BigDecimal.valueOf(100)));

                icms.setCst(String.format("%02d",tributacao.getIcmsCst()));
                icms.setBaseTax(icmsBase.doubleValue());
                icms.setRate(icmsAliquota.doubleValue());
                icms.setAmount(icmsAmount.doubleValue());

                if (tributacao.getCest() != null && tributacao.getCest() != 0) {
                    item.setCest(CEST_FORMATTER.format(tributacao.getCest()));
                }

                imposto.setCfop(String.valueOf(tributacao.getCfop()));
                imposto.setIcms(icms);
                item.setTax(imposto);
            }

            if (tributacaoFederal1 != null) {
                // Preencher PIS/COFINS
                PisModel pis = new PisModel();
                CofinsModel cofins = new CofinsModel();

                // PIS
                BigDecimal pisBase = BigDecimal.valueOf(item.getTotalAmount());
                BigDecimal pisAliquota = tributacaoFederal1.getPisAliquota().multiply(BigDecimal.valueOf(100));
                BigDecimal pisAmount = pisBase.multiply(pisAliquota.divide(BigDecimal.valueOf(100)));

                pis.setCst(String.format("%02d", tributacaoFederal1.getPisCst()));
                pis.setBaseTax(pisBase.doubleValue());
                pis.setRate(pisAliquota.doubleValue());
                pis.setAmount(pisAmount.doubleValue());

                // COFINS
                BigDecimal cofinsBase = BigDecimal.valueOf(item.getTotalAmount());
                BigDecimal cofinsAliquota = tributacaoFederal1.getCofinsAliquota().multiply(BigDecimal.valueOf(100));
                BigDecimal cofinsAmount = cofinsBase.multiply(cofinsAliquota.divide(BigDecimal.valueOf(100)));

                cofins.setCst(String.format("%02d",tributacaoFederal1.getCofinsCst()));
                cofins.setBaseTax(cofinsBase.doubleValue());
                cofins.setRate(cofinsAliquota.doubleValue());
                cofins.setAmount(cofinsAmount.doubleValue());

                if (item.getTax() == null) {
                    item.setTax(new TaxModel());
                }
                item.getTax().setPis(pis);
                item.getTax().setCofins(cofins);
            }
            preencheCalculosRefAoTotalsDaNota(itensDaInvoice, invoice);
        }
    }

    private void preencheCalculosRefAoTotalsDaNota(List<ItemModel> itensInvoice, InvoiceModel invoice) {

        TotalsModel totals = new TotalsModel();

        BigDecimal  vBc = BigDecimal.ZERO;
        BigDecimal  vIcms = BigDecimal.ZERO;
        BigDecimal  vIcmsDeson = BigDecimal.ZERO;
        BigDecimal  vFcp = BigDecimal.ZERO;
        BigDecimal  vBcst = BigDecimal.ZERO;
        BigDecimal  vSt = BigDecimal.ZERO;
        BigDecimal  vFcpSt = BigDecimal.ZERO;
        BigDecimal  vFcpStRet = BigDecimal.ZERO;
        BigDecimal  vProd = BigDecimal.ZERO;
        BigDecimal  vFrete = BigDecimal.ZERO;
        BigDecimal  vSeg = BigDecimal.ZERO;
        BigDecimal  vDesc = BigDecimal.ZERO;
        BigDecimal  vII = BigDecimal.ZERO;
        BigDecimal  vIpi = BigDecimal.ZERO;
        BigDecimal  vIpiDevol = BigDecimal.ZERO;
        BigDecimal  vPis = BigDecimal.ZERO;
        BigDecimal  vCofins = BigDecimal.ZERO;
        BigDecimal  vOutro = BigDecimal.ZERO;
        BigDecimal  vTotTrib = BigDecimal.ZERO;
        BigDecimal  vNf = BigDecimal.ZERO;

        for (ItemModel item : itensInvoice) {
            vBc = vBc.add(BigDecimal.valueOf(item.getTax().getIcms().getBaseTax()));
            vIcms = vIcms.add(BigDecimal.valueOf(item.getTax().getIcms().getAmount()));
            vProd = vProd.add(BigDecimal.valueOf(item.getTotalAmount()));
            vPis = vPis.add(BigDecimal.valueOf(item.getTax().getPis().getAmount()));
            vCofins = vCofins.add(BigDecimal.valueOf(item.getTax().getCofins().getAmount()));
            vNf = vNf.add(BigDecimal.valueOf(item.getTotalAmount() + item.getFreightAmount() + item.getOthersAmount() - item.getDiscountAmount()));
            vTotTrib = vTotTrib.add(BigDecimal.valueOf(item.getTotalAmount()));

            //A outras somas na nota ?
            if (item.getOthersAmount() > 0) {
                vOutro = vOutro.add(BigDecimal.valueOf(item.getOthersAmount()));
            }

            if(item.getTax().getIcms().getCst().equals("60")){
                vBc = BigDecimal.ZERO;
            }

            //O item tem desconto ? se tiver iremos setar
            if (item.getDiscountAmount() > 0) {
                vDesc = vDesc.add(BigDecimal.valueOf(item.getDiscountAmount()));
            }

            //O item tem valor de frete ?
            if (item.getFreightAmount() > 0) {
                vFrete = vFrete.add(BigDecimal.valueOf(item.getFreightAmount()));
            }
        }

        totals.setvBc(stringUtil(formatBigDecimal(vBc)));
        totals.setvIpiDevol(stringUtil(formatBigDecimal(vIpiDevol)));
        totals.setvFcpSt(stringUtil(formatBigDecimal(vFcpSt)));
        totals.setvFcpStRet(stringUtil(formatBigDecimal(vFcpStRet)));
        totals.setvFcp(stringUtil(formatBigDecimal(vFcp)));
        totals.setvSt(stringUtil(formatBigDecimal(vSt)));
        totals.setvBcst(stringUtil(formatBigDecimal(vBcst)));
        totals.setvIcmsDeson(stringUtil(formatBigDecimal(vIcmsDeson)));
        totals.setvSeg(stringUtil(formatBigDecimal(vSeg)));
        totals.setvIpi(stringUtil(formatBigDecimal(vIpi)));
        totals.setvII(stringUtil(formatBigDecimal(vII)));
        totals.setvOutro(stringUtil(formatBigDecimal(vOutro)));
        totals.setvTotTrib(stringUtil(formatBigDecimal(vTotTrib)));
        totals.setvNf(stringUtil(formatBigDecimal(vNf)));
        totals.setvProd(stringUtil(formatBigDecimal(vProd)));
        totals.setvPis(stringUtil(formatBigDecimal(vPis)));
        totals.setvCofins(stringUtil(formatBigDecimal(vCofins)));
        totals.setvIcms(stringUtil(formatBigDecimal(vIcms)));
        totals.setvDesc(stringUtil(formatBigDecimal(vDesc)));
        totals.setvFrete(stringUtil(formatBigDecimal(vFrete)));

        invoice.setTotals(totals);
    }

    private String formatBigDecimal(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).toString();
    }

    private String stringUtil(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            valor = "0.00";
        }
        return valor;
    }
}