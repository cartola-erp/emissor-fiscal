package net.cartola.emissorfiscal.danfe;

import net.sf.jasperreports.engine.JRException;
import org.xml.sax.SAXException;

import br.com.swconsultoria.impressao.model.Impressao;
import br.com.swconsultoria.impressao.service.ImpressaoService;
import br.com.swconsultoria.impressao.util.ImpressaoUtil;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TesteImpressaoNFCe {

    public static void main(String[] args) {
        try {
            //Faça a leitura do Arquivo
            String xml = ImpressaoUtil.leArquivo("./src/test/resources/xmls/nfce35241105437537000137650010000000101000000108.xml");

            String urlConsulta = "www.nfce.fazenda.sp.gov.br/NFCeConsultaPublica/Paginas/ConsultaQRCode.aspx";

            Impressao impressao = ImpressaoUtil.impressaoPadraoNFCe(xml, urlConsulta);

            //Faz a impressão em pdf File
            impressaoPdfArquivo(impressao);
            System.out.println("Impressão Pdf Arquivo OK");
        } catch (Exception e) {
            //Trate seus erros aqui
            e.printStackTrace();
        }
    }

    private static void impressaoPdfArquivo(Impressao impressao) throws IOException, JRException, ParserConfigurationException, SAXException {
        ImpressaoService.impressaoPdfArquivo(impressao, "./src/test/resources/xmls/nfce35241105437537000137650010000000101000000108.pdf");
    }
}
