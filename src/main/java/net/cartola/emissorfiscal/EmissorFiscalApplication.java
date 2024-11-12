package net.cartola.emissorfiscal;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Optional;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.cartola.emissorfiscal.contador.Contador;
import net.cartola.emissorfiscal.contador.ContadorService;
import net.cartola.emissorfiscal.usuario.Perfil;
import net.cartola.emissorfiscal.usuario.Usuario;
import net.cartola.emissorfiscal.usuario.UsuarioPerfil;
import net.cartola.emissorfiscal.usuario.UsuarioService;

@SpringBootApplication
public class EmissorFiscalApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmissorFiscalApplication.class, args);
	}
	
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.ofOffset("GMT", ZoneOffset.of("-3"))));
	}
	
	@Bean
	@Profile({"dev", "test" })
	CommandLineRunner initDev(UsuarioService usuarioService, ContadorService contadorService, BCryptPasswordEncoder bcryptEncoder) {

		return args -> {
			
			Optional<Usuario> opUserErpjWs = usuarioService.findByLogin("erpj-ws");
			if (!opUserErpjWs.isPresent()) {
				UsuarioPerfil upa = new UsuarioPerfil();
				upa.setPerfil(Perfil.API_ACESS);

				UsuarioPerfil upu = new UsuarioPerfil();
				upu.setPerfil(Perfil.API_ACESS);
				Usuario u = new Usuario();
				u.setLogin("erpj-ws");
				u.setNome("erpj-ws");
				u.setSenha(bcryptEncoder.encode("erpj-ws-homologacao")); 
				u.setPerfis(Arrays.asList(upa, upu)); 
				usuarioService.save(u);
				System.out.printf("Usuario criado : %s", u);
			}
			
			Optional<Usuario> op = usuarioService.findByLogin("contador");
			if (!op.isPresent()) {
				UsuarioPerfil upa = new UsuarioPerfil();
				upa.setPerfil(Perfil.CONTADOR);

				Usuario u = new Usuario();
				u.setLogin("contador");
				u.setNome("Contador");
				u.setSenha(bcryptEncoder.encode("root"));
				u.setPerfis(Arrays.asList(upa));
				usuarioService.save(u);
				System.out.printf("Usuario criado : %s", u);
			}
			
			Optional<Usuario> opUserEscriturador = usuarioService.findByLogin("escriturador");
			if (!opUserEscriturador.isPresent()) {
				UsuarioPerfil upa = new UsuarioPerfil();
				upa.setPerfil(Perfil.ESCRITURADOR);

				Usuario u = new Usuario();
				u.setLogin("escriturador");
				u.setNome("escriturador");
				u.setSenha(bcryptEncoder.encode("root"));
				u.setPerfis(Arrays.asList(upa));
				usuarioService.save(u);
				System.out.printf("Usuario criado : %s", u);
			}
			
			
			Optional<Usuario> opUser = usuarioService.findByLogin("robson.costa");
			if (!opUser.isPresent()) {
				UsuarioPerfil upa = new UsuarioPerfil();
				upa.setPerfil(Perfil.ADMIN);

				Usuario u = new Usuario();
				u.setLogin("robson.costa");
				u.setNome("Robson"); 
				u.setSenha(bcryptEncoder.encode("root"));
				u.setPerfis(Arrays.asList(upa));
				usuarioService.save(u);
				System.out.printf("Usuario criado : %s", u);
			}
			
		
			
			String crc = "RJ000292898/1-7";
			Optional<Contador> opContador = contadorService.findByCrc(crc);
			if (!opContador.isPresent()) {
				Contador contador = new Contador();
				contador.setNome("SILVIO SANTOS VEM AI");
				contador.setCpf("07379981056");
				contador.setCrc(crc);
//				contador.setCrc(0002057670L);
//				contador 
				contador.setCep(12333444L);
				contador.setEndereco("Rua Onde Judas Perdeu as Botas");
				contador.setNumImovel(314);
				contador.setComplementoEndereco("");
				contador.setBairroDoImovel("Jd Paquitas da Mara");
				contador.setTelefone("11999991111");
				contador.setNumFax("");
				contador.setEmail("email@email.com");
				contador.setCodMunicipio(3523909L);
				contadorService.save(contador);
			}
		};
	}

	
}
