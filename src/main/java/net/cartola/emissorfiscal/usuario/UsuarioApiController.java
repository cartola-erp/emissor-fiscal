package net.cartola.emissorfiscal.usuario;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.response.Response;
import net.cartola.emissorfiscal.util.ApiUtil;

/**
 *	27 de dez de 2019
 *	@author robson.costa
 */

@RestController
@RequestMapping("api/v1/usuario")
@CrossOrigin(origins = "*")
public class UsuarioApiController {
	
	private static final Logger LOG = Logger.getLogger(UsuarioApiController.class.getName());
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/criar-usuario")
	public ResponseEntity<Response<Usuario>> criarUsuario(@RequestBody Usuario usuario) {
		LOG.log(Level.INFO, "Usuário {0} autenticando", usuario.getLogin());
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		Optional<Usuario> opUser = usuarioService.findByLogin(usuario.getLogin());
		Response<Usuario> response;

		if(opUser.isPresent()) {
			LOG.log(Level.INFO, "O Usuario já existe no emissor-fiscal {0}", opUser.get());
			response = ApiUtil.criaResponseDeErro("O usuário: " +opUser.get().getLogin()+ " já está cadastrado no EMISSOR-FISCAL");
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Usuario> opUsuario = usuarioService.save(usuario);
		if (opUsuario.isPresent()) {
			LOG.log(Level.INFO, "Usuario: {0}, criado com sucesso! ", opUsuario.get().getLogin());
			return ResponseEntity.ok().build();
		} else {
			LOG.log(Level.WARNING, "Erro ao tentar criar usuário! ");
			response = ApiUtil.criaResponseDeErro("Ocorreu algum ERRO, ao tentar cadastrar o usuário no EMISSOR-FISCAL! Por favor, contate o setor de TI!");
			return ResponseEntity.badRequest().body(response);
		}
	}	

	@PutMapping("/alterar-usuario")
	public ResponseEntity<Response<Usuario>> edit (@RequestBody Usuario usuarioToUpdate) {
		LOG.log(Level.INFO, "Alterando o Usuário {0} ", usuarioToUpdate.getLogin());
		usuarioToUpdate.setSenha(bCryptPasswordEncoder.encode(usuarioToUpdate.getSenha()));
		Optional<Usuario> opUser = usuarioService.findByLogin(usuarioToUpdate.getLogin());
		Response<Usuario> response;
		Optional<Usuario> opUserUpdated = Optional.empty();
		if(opUser.isPresent()) {
			opUserUpdated =  usuarioService.edit(opUser, usuarioToUpdate);
		}
		if (opUserUpdated.isPresent()) {
			return ResponseEntity.ok().build();
		} else {
			LOG.log(Level.WARNING, "O Usuário {0}, NÃO está cadastrado no emissor-fiscal ", usuarioToUpdate.getLogin());
			response = ApiUtil.criaResponseDeErro("O usuário não existe no EMISSOR-FISCAL, para ser atualizado! Por favor, contate o setor de TI!");
			return ResponseEntity.badRequest().body(response);
		}
	
	}
}


