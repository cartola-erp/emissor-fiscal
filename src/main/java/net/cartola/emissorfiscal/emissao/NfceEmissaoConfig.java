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

public class NfceEmissaoConfig {

    @Value("${certificado.pfx.path}")
    private static String certificadoPfxPath;

    @Value("${certificado.senha}")
    private static String certificadoSenha;

    public static ConfiguracoesNfe iniciaConfiguracoes() throws CertificadoException, IOException {

        File file = new File(certificadoPfxPath);
        byte[] bytes = Files.readAllBytes(file.toPath());
        String senha = certificadoSenha;

        Certificado certificado = CertificadoService.certificadoPfxBytes(bytes, senha);
        ConfiguracoesNfe config = ConfiguracoesNfe.criarConfiguracoes(EstadosEnum.SP, AmbienteEnum.HOMOLOGACAO, certificado, "/Users/wesleymendonca/Documents/schemas");
        config.setEncode("UTF-8");

        return config;
    }
}
