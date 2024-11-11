package net.cartola.emissorfiscal.emissao;

import autogeral.emissorfiscal.vo.InvoiceModel;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualService;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederalService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmissaoPrenchimentoDadosFiscaisServiceTest {

    @Autowired
    EmissaoPrenchimentoDadosFiscaisService emissaoPrenchimentoDadosFiscaisService;

    @Autowired
    TributacaoEstadualService tributacaoEstadualService;

    @Autowired
    TributacaoFederalService tributacaoFederalService;

    @Autowired
    NcmService ncmService;

    @Test
    public void preenchimentoDaNfce(){
        InvoiceModel a = new InvoiceModel();

       // a.setId("1"); // Possivel chave da Nota
            
        emissaoPrenchimentoDadosFiscaisService.preencherDadosFiscais(a);
    }


    /**
     *  @Test
     *     public void testaSeProdutoExiste(){
     *     }
     */


    @Test
    public void testaSeNcmExiste(){
        InvoiceModel a = new InvoiceModel();
        String ncm = a.getItems().get(1).getNcm();
        String tipi = a.getItems().get(1).getExTipi();

        int ncmParse = Integer.parseInt(ncm);
        int tipiParse = Integer.parseInt(tipi);

        Optional<Ncm> retorno = ncmService.findNcmByNumeroAndExcecao(ncmParse, tipiParse);
        if(!retorno.isPresent()){
            fail("Nao foi encontrado o ncm em questao");
        }

    }

    @Test
    public void testExistTributacaoFederal(){
        InvoiceModel a = new InvoiceModel();
        String ncm = a.getItems().get(1).getNcm();

        int ncmInt = Integer.parseInt(ncm);
        Ncm ncmt = new Ncm();
        ncmt.setAtivo(true);
        ncmt.setDescricao(a.getItems().get(1).getDescription());
        ncmt.setNumero(ncmInt);

        List<TributacaoFederal> tributacao =  tributacaoFederalService.findTributacaoFederalByNcm(ncmt);

        if(ncm.isEmpty() || ncmInt < 0){
            fail("NCM não existente: " + ncm);
        }

        if(tributacao.isEmpty()){
            fail("Nao foi encontrada a tributacao para o ncm: " + ncm);
        }

        if(!tributacao.isEmpty() && ncmInt > 0 ){
            System.out.println("Foi encontrada tributacao estadual para o ncm.");
        }
    }

    @Test
    public void testExistTributacaoEstadual() {
        InvoiceModel a = new InvoiceModel();
        String ncm = a.getItems().get(1).getNcm();

        int ncmInt = Integer.parseInt(ncm);
        Ncm ncmt = new Ncm();
        ncmt.setAtivo(true);
        ncmt.setDescricao(a.getItems().get(1).getDescription());
        ncmt.setNumero(ncmInt);

        List<TributacaoEstadual> tributacao = tributacaoEstadualService.findTributacaoEstadualByNcm(ncmt);

        if (ncm.isEmpty() || ncmInt < 0) {
            fail("NCM não existente: " + ncm);
        }

        if (tributacao.isEmpty()) {
            fail("Nao foi encontrada a tributacao para o ncm: " + ncm);
        }

        if (!tributacao.isEmpty() && ncmInt > 0) {
            System.out.println("Foi encontrada tributacao estadual para o ncm.");
        }
    }
}