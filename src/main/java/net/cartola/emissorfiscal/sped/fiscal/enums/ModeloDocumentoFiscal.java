package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 02/09/2020
 * @author robson.costa
 * 
 * 4.1.1 - Tabela Documentos Fiscais do ICMS
 */
public enum ModeloDocumentoFiscal implements EnumCodificado {
	
    _1("Nota Fiscal", "01"),
    _1B("Nota Fiscal Avulsa", "1B"),
    _2("Nota Fiscal Consumidor", "02"),
    _2D("Cupom Fiscal", "2D"),	
    _2E("Cupom Fiscal Bilhete de Passagem", "02"),
    _4("Nota Fiscal de Produtor", "04"),
    _6("Nota Fiscal/Conta Energia Eletrica", "06"),
    _7("Nota Fiscal de Serviço de Transporte", "07"),
    _8("Conhecimento de Transporte Rodoviário de Cargas", "08"),
    _8B("Conhecimento de Transporte de Cargas Avulso", "8B"),
    _9("Conhecimento de Transporte Aquaviário de Cargas", "09"),
    _10("Conhecimento Aéreo", "10"),
    _11("Conhecimento de Transporte Ferroviário de Cargas", "11"),
    _13("Bilhete de Passagem Rodoviário", "13"),
    _14("Bilhete de Passagem Aquaviário", "14"),
    _15("Bilhete de Passagem e Nota de Bagagem", "15"),
    _16("Bilhete de Passagem Ferroviário", "16"),
    _18("Resumo de Movimento Diário", "18"),
    _21("Nota Fiscal de Serviço de Comunicação", "21"),
    _22("Nota Fiscal de Serviço de Telecomunicação", "22"),
    _26("Conhecimento de Transporte Multimodal de Cargas", "26"),
    _27("Nota Fiscal De Transporte Ferroviário", "27"),
    _28("Nota Fiscal/Conta de Fornecimento de Gás Canalizado", "28"),
    _29("Nota Fiscal/Conta de Fornecimento de Água Canalizada", "29"),
    _55("Nota Fiscal Eletrônica", "55"),
    _57("Conhecimento de Transporte Eletrônico", "57"),
    _59("Cupom Fiscal Eletrônico (CF-e-SAT)", "59"),
    _60("Cupom Fiscal Eletrônico (CF-e-ECF)", "60"),
    _63("Bilhete de Passagem Eletrônico – BP-e", "63"),
    _65("Nota Fiscal Eletrônica ao Consumidor Final", "65"),
    _67("Conhecimento de Transporte Eletrônico - CT-e OS", "67");
    
    private final String codigo;
    private final String descricao;
    
    private ModeloDocumentoFiscal(String descricao, String codigo) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
	
	@Override
    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
        
    @Override
    public String toString() {
        return codigo + " " + descricao;
    }
	
}
