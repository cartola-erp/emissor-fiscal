package net.cartola.emissorfiscal.emissao;

import net.cartola.emissorfiscal.documento.Documento;
import org.springframework.stereotype.Service;

@Service
public class Emissao {

        public void emitir(Documento documento) {
            System.out.println("Emitindo nota fiscal...");
            documento.getEmitente();
        }

}
