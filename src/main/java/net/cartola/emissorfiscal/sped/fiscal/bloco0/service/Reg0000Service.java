package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0000AberturaArquivoDigital;
import net.cartola.emissorfiscal.sped.fiscal.enums.FinalidadeDoArquivo;
import net.cartola.emissorfiscal.sped.fiscal.enums.TipoDeAtividade;
import net.cartola.emissorfiscal.sped.fiscal.enums.VersaoDoLayout;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class Reg0000Service implements MontaGrupoRegistroSimples<Reg0000AberturaArquivoDigital, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0000Service.class.getName());
	
	@Override
	public Reg0000AberturaArquivoDigital montarGrupoDeRegistroSimples(MovimentacoesMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando a abertura do arquivo digital (Reg0000) ");
		Reg0000AberturaArquivoDigital reg0000 = new Reg0000AberturaArquivoDigital();
		Loja loja = movimentosIcmsIpi.getLoja();
//		PessoaEndereco enderecoLoja = loja.getEndereco();
		
		reg0000.setCodVer(VersaoDoLayout.V_015);
		reg0000.setCodFin(FinalidadeDoArquivo.REMESSA_ARQUIVO_ORIGINAL);
		reg0000.setDtIni(movimentosIcmsIpi.getDataInicio());
		reg0000.setDtFin(movimentosIcmsIpi.getDataFim());
		reg0000.setNome(loja.getNome());
		reg0000.setCnpj(loja.getCnpj());
		reg0000.setUf(loja.getUf().toString());			// Colocar a UF no OBJ -> LOJA
		reg0000.setIe(loja.getIe().toString());
		reg0000.setCodMun(loja.getIbgeCodigo());
		reg0000.setIm(loja.getIm() != null ? loja.getIm() : "");
		reg0000.setSuframa("");
		reg0000.setIndPerfil(loja.getSpedFiscalPerfil());			// Colocar essa info, na tabela PESSOA, para CADA LOJA, ou o ideal é usar a tabela loja msm?
		reg0000.setIndAtiv(TipoDeAtividade.OUTROS);
		LOG.log(Level.INFO, "Abertura do arquivo digital (Reg0000), criada: {0} ", reg0000);
		return reg0000;
	}

	
	
}
