package net.cartola.emissorfiscal;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.cartola.emissorfiscal.usuario.Perfil;
import net.cartola.emissorfiscal.usuario.Usuario;
import net.cartola.emissorfiscal.usuario.UsuarioPerfil;
import net.cartola.emissorfiscal.usuario.UsuarioService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	@Profile({"test"})
	CommandLineRunner initDev(UsuarioService usuarioService,
			BCryptPasswordEncoder bcryptEncoder) {

		return args -> {
			Optional<Usuario> op = usuarioService.findByLogin("contador");
			if (!op.isPresent()) {
				UsuarioPerfil upa = new UsuarioPerfil();
				upa.setPerfil(Perfil.ROLE_CONTADOR);

				UsuarioPerfil upu = new UsuarioPerfil();
				upu.setPerfil(Perfil.ROLE_ESCRITURADOR);
				Usuario u = new Usuario();
				u.setLogin("contador");
				u.setNome("Contador");
				u.setSenha(bcryptEncoder.encode("root"));
				u.setPerfis(Arrays.asList(upa, upu));
				usuarioService.save(u);
				System.out.printf("Usuario criado : %s", u);
			}
		};
	}

	
}
