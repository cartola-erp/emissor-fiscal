package net.cartola.emissorfiscal.emissao;

import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigNfceService {

    public static ConfiguracoesNfe iniciaConfiguracoes() throws CertificadoException, IOException {

        //File file = new File("/Users/wesleymendonca/.DBF/dist/20250220A1.pfx");
        File file = new File("C:/DBF/dist/A1260217.pfx");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String senha = "V552289";

        Certificado certificado = CertificadoService.certificadoPfxBytes(bytes, senha);
        ConfiguracoesNfe config = ConfiguracoesNfe.criarConfiguracoes(EstadosEnum.SP, AmbienteEnum.HOMOLOGACAO, certificado, "S:\\projects\\Java_NFe\\schemas");
        config.setEncode("UTF-8");

        return config;
    }
}
