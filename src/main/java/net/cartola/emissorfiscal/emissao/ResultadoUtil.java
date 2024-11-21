package net.cartola.emissorfiscal.emissao;

import autogeral.emissorfiscal.vo.InvoiceModel;

import java.util.HashMap;
import java.util.Map;

/*
 *  O INTUITO DESSA CLASSE E ARMAZENAR TANTO O SUCESSO QUANDO POSSIVEIS ERROS
 *  NA HORA DO PREENCHIMENTO DA INVOICE ( NFCE )
 */
public class ResultadoUtil {

    private InvoiceModel invoice;
    private Map<String, Map<String, String>> erros;

    public ResultadoUtil(){
        this.erros = new HashMap<>();
    }

    public ResultadoUtil(InvoiceModel invoice, Map<String, Map<String, String>> erros){
        this.invoice = invoice;
        this.erros = erros;
    }

    public InvoiceModel getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceModel invoice) {
        this.invoice = invoice;
    }

    public Map<String, Map<String, String>> getErros() {
        return erros;
    }

    public void setErros(Map<String, Map<String, String>> erros) {
        this.erros = erros;
    }
}
