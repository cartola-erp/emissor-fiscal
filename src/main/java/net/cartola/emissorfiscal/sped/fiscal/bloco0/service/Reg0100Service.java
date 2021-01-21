package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.contador.Contador;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0100;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0100Service implements MontaGrupoRegistroSimples<Reg0100, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0100Service.class.getName());

	@Override
	public Reg0100 montarGrupoDeRegistroSimples(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0100 ");
		Reg0100 reg0100 = new Reg0100();
		Contador contador = movimentosIcmsIpi.getContador();
		
		reg0100.setNome(contador.getNome());
		reg0100.setCpf(contador.getCpf());
		reg0100.setCrc(contador.getCrc().toString());
		reg0100.setCnpj(contador.getCnpj());
		reg0100.setCep(contador.getCep());
		reg0100.setEnd(contador.getEndereco());
		reg0100.setNum(contador.getNumImovel());
		reg0100.setCompl(contador.getComplementoEndereco());
		reg0100.setBairro(contador.getBairroDoImovel());
		reg0100.setFone(contador.getTelefone());
		reg0100.setFax(contador.getNumFax());
		reg0100.setEmail(contador.getEmail());
		reg0100.setCodMun(contador.getCodMunicipio());
		
		LOG.log(Level.INFO, "Registro 0100, terminado. REG 0100: {0} " ,reg0100);
		return reg0100;
	}

}
