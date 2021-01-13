package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.contador.Contador;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0005;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0005Service implements MontaGrupoRegistroSimples<Reg0005, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0005Service.class.getName());

	@Override
	public Reg0005 montarGrupoDeRegistroSimples(MovimentacoesMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro 0005");
		Loja loja = movimentosIcmsIpi.getLoja();
		Contador contador = movimentosIcmsIpi.getContador();
		
		Reg0005 reg0005 = new Reg0005();
		reg0005.setFantasia(loja.getNomeFantasia());
		reg0005.setCep(Long.getLong(loja.getCep().replace("-", "")));
		reg0005.setEnd(loja.getEndereco());
		reg0005.setNum(loja.getEnderecoNumero());
		reg0005.setCompl(loja.getEnderecoComplemento());
		reg0005.setBairro(loja.getBairro());
		reg0005.setFone(loja.getTelefone());
		reg0005.setFax("");
		reg0005.setEmail(contador.getEmail());			// Pegar da loja ou contador??
		
		LOG.log(Level.INFO, "Registro 0005, terminado. REG 0005: {0} " ,reg0005);
		return reg0005;
	}

}
