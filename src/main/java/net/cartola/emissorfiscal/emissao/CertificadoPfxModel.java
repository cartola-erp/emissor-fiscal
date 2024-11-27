package net.cartola.emissorfiscal.emissao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "certificado")
public class CertificadoPfxModel {

    private String pfxPath;
    private String senha;
    private String tokenSefaz;
    private String cscSefaz;

    public String getTokenSefaz() {
        return tokenSefaz;
    }

    public void setTokenSefaz(String tokenSefaz) {
        this.tokenSefaz = tokenSefaz;
    }

    public String getCscSefaz() {
        return cscSefaz;
    }

    public void setCscSefaz(String cscSefaz) {
        this.cscSefaz = cscSefaz;
    }

    public String getPfxPath() {
        return pfxPath;
    }

    public void setPfxPath(String pfxPath) {
        this.pfxPath = pfxPath;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
