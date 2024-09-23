package net.cartola.emissorfiscal.recalculo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.cartola.emissorfiscal.api.controller.AuthorizationTestHelper;
import net.cartola.emissorfiscal.controller.DocumentoEmissaoControllerTest;
import net.cartola.emissorfiscal.documento.*;
import net.cartola.emissorfiscal.operacao.Operacao;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class RecalculoFiscalTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthorizationTestHelper<?> authHelper;

    @Autowired
    private RecalculoService recalculoService;

    /*
        @Test
        public void verificaSeRecalcula(){

            DocumentoFiscalApiController a = new DocumentoFiscalApiController();

            a.recalculo();
        }
    */
    @Test
    public void documentoFiscal() {

        DocumentoFiscal documentoTeste = new DocumentoFiscal();
        List<DocumentoFiscalItem> itemDoc = new ArrayList<>();
        DocumentoFiscalItem item = new DocumentoFiscalItem();

        Operacao operacao = new Operacao();
        operacao.setId(37L);
        operacao.setDescricao("TRANSFERENCIA DE MERCADORIA(ENTRADA) - ESTADUAL");
        operacao.setDevolucao(false);
        operacao.setRemessaParaFornecedor(false);
        operacao.setInterestatual(false);


        documentoTeste.setTipoOperacao(IndicadorDeOperacao.valueOf("ENTRADA"));
        documentoTeste.setOperacao(operacao);
        documentoTeste.setItens(itemDoc);

        item.setEan("7898031545002");
        item.setClasseFiscal(String.valueOf(87083090));
        item.setDescricaoEmpresa("MASSA PLASTICA");

        item.setIcmsCst(0);
        item.setIcmsAliquota(BigDecimal.valueOf(0.0));
        item.setPisCst(0);
        item.setPisAliquota(BigDecimal.valueOf(0.00));
        item.setCofinsCst(0);
        item.setCofinsAliquota(BigDecimal.valueOf(0.00));
        item.setCodigoAnp(0);
        item.setFinalidade(Finalidade.valueOf("COMERCIALIZACAO"));

        itemDoc.add(item);

        RecalculoService r = new RecalculoService();
        if (r == null) {
            throw new IllegalStateException("RecalculoService não foi inicializado.");
        }

        System.out.println("Itens do documento: " + documentoTeste.getItens());
        DocumentoFiscalApiController a = new DocumentoFiscalApiController();

        Optional<DocumentoFiscal> documentoRecalculado = Optional.of(documentoTeste);
        r.calcularImpostoEstadual(documentoRecalculado.get());

        System.out.println("Esse é o documento calculado: " + documentoRecalculado);
    }
}