package net.cartola.emissorfiscal.cadastro;


/**
 * Determina se ao emitir um documento fiscal</br>
 * do tipo ECF ou NFCe então, qual documento/<br>
 * deve ser informado no documento fiscal.
 * 07/01/2015 15:42:48
 * @author murilo
 */
public enum CadastroDocumentoFiscal {
    MESMO, NENHUM, OUTRO;
}
