package net.cartola.emissorfiscal.emissao;

import autogeral.emissorfiscal.vo.InvoiceModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */

@Service
public class EmissaoPrenchimentoDadosFiscaisService {

    /**
     * Esse metodo deve prencher todos dados da tributacao para a nota fiscal passada como argumento
     * o mapa de retorno, deve informar o status do processamento e os erros, caso existam (do map) :
     *
     */

    public Map<String, String> preencherDadosFiscais(InvoiceModel invoce){

        Map<String, String> resultado = new HashMap<>();
        return resultado;
    }
}
