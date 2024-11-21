package net.cartola.emissorfiscal.emissao;


import autogeral.emissorfiscal.vo.*;
import net.cartola.emissorfiscal.recalculo.RecalculoService;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EmissaoPrenchimentoDadosFiscaisService {

    @Autowired
    RecalculoService recalculoService;

    /**
     * Esse metodo deve prencher todos dados da tributacao para a nota fiscal passada como argumento
     * o mapa de retorno, deve informaXr o status do processamento e os erros, caso existam (do map) :
     */

    public ResultadoUtil preencherDadosFiscais(InvoiceModel invoice) {
        ResultadoUtil resultado = new ResultadoUtil();

        HashMap<String, Map<String, String>> erros = new HashMap<>();
        Map<String, String> detalhesErros = new HashMap<>(); // Mapa para armazenar erros específicos
        Map<String, String> detalhesSucesso = new HashMap<>(); // Mapa para armazenar mensagens de sucesso

        List<ItemModel> ncmsDosItensDaInvoice = invoice.getItems();
        for (ItemModel itemNcm : ncmsDosItensDaInvoice) {
            String ncm = itemNcm.getNcm();
            if (ncm == null || ncm.trim().isEmpty()) {
                detalhesErros.put("Ncm", "Ha itens sem ncm cadastrado");
            }
        }

        if (PreencheTributacaoEstadualInvoice(ncmsDosItensDaInvoice).isEmpty()) {
            detalhesErros.put("Tributacao", "Nao encontrada tributacao estadual | Verificar no emissor Fiscal");
        }

        if (PreencheTributacaoFederalInvoice(ncmsDosItensDaInvoice).isEmpty()) {
            detalhesErros.put("Tributacao", "Nao encontrada tributacao federal para o ncm: | Verificar no emissor Fiscal");
        }

        if(!detalhesErros.isEmpty()){
            erros.put("Erros: ", detalhesErros);
            resultado.setErros(erros);
            return resultado;
        }

        detalhesSucesso.put("Concluido: " , "Nao foram encontrados erros");
        erros.put("Sucesso ao realizar o preenchimento dos dados fiscais: ", detalhesSucesso);
        resultado.setInvoice(invoice);
        resultado.setErros(erros);
        return resultado;
    }

    public List<TributacaoEstadual> PreencheTributacaoEstadualInvoice(List<ItemModel> itensDaInvoice) {

        List<TributacaoEstadual> tributacaoEstadual = recalculoService.TributacaoEstadualForNfce(itensDaInvoice);
        if(!tributacaoEstadual.isEmpty()){
            for(TributacaoEstadual tributacaoEstadualSet : tributacaoEstadual){
                for(ItemModel itemCalculado: itensDaInvoice){
                    TaxModel imposto = new TaxModel();
                    IcmsModel icms = new IcmsModel();

                    icms.setCst(String.valueOf(tributacaoEstadualSet.getIcmsCst()));
                    icms.setOrigin(itemCalculado.getAdditionalInformation());
                    icms.setBaseTax(tributacaoEstadualSet.getIcmsBase().doubleValue());
                    icms.setRate(tributacaoEstadualSet.getIcmsAliquota().doubleValue());

                    // Se necessario refazer esse calculo futuramente
                    icms.setAmount(itemCalculado.getTotalAmount() * icms.getRate()); // referente a tag vICMS
                    imposto.setIcms(icms);
                    imposto.setCfop(String.valueOf(tributacaoEstadualSet.getCfop()));
                    itemCalculado.setTax(imposto);
                }
            }
        }
        return tributacaoEstadual;
    }

    public List<TributacaoFederal> PreencheTributacaoFederalInvoice(List<ItemModel> itensDaInvoice) {
        List<TributacaoFederal> tributacaoFederal = recalculoService.TributacaoFederalForNfce(itensDaInvoice);
        if(!tributacaoFederal.isEmpty()){
            for(TributacaoFederal tributacaoFederalSet : tributacaoFederal){
                for(ItemModel itemCalculado: itensDaInvoice){
                    TaxModel imposto = new TaxModel();
                    PisModel pis = new PisModel();

                    pis.setCst(String.valueOf(tributacaoFederalSet.getPisCst()));
                    pis.setBaseTax(tributacaoFederalSet.getPisBase().doubleValue());
                    pis.setRate(tributacaoFederalSet.getPisAliquota().doubleValue());

                    // Se necessario refazer esse calculo futuramente
                    pis.setAmount(itemCalculado.getTotalAmount() * pis.getRate()); // referente a tag vICMS
                    imposto.setPis(pis);
                    itemCalculado.setTax(imposto);

                    CofinsModel cofins = new CofinsModel();

                    cofins.setCst(String.valueOf(tributacaoFederalSet.getCofinsCst()));
                    cofins.setBaseTax(tributacaoFederalSet.getCofinsBase().doubleValue());
                    cofins.setRate(tributacaoFederalSet.getCofinsAliquota().doubleValue()); //aliq

                    // Se necessario refazer esse calculo futuramente
                    cofins.setAmount(itemCalculado.getTotalAmount() * cofins.getRate()); // referente a tag vICMS
                    imposto.setCofins(cofins);
                    itemCalculado.setTax(imposto);
                }
            }
        }
        return tributacaoFederal;
    }
}