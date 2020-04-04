package net.cartola.emissorfiscal.usuario;

import java.util.Optional;

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
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/criar-usuario")
	public ResponseEntity<Response<Usuario>> criarUsuario(@RequestBody Usuario usuario) {
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		Optional<Usuario> opUser = usuarioService.findByLogin(usuario.getLogin());
		Response<Usuario> response;

		if(opUser.isPresent()) {
			response = ApiUtil.criaResponseDeErro("O usuário: " +opUser.get().getLogin()+ " já está cadastrado no EMISSOR-FISCAL");
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Usuario> opUsuario = usuarioService.save(usuario);
		if (opUsuario.isPresent()) {
			return ResponseEntity.ok().build();
		} else {
			response = ApiUtil.criaResponseDeErro("Ocorreu algum ERRO, ao tentar cadastrar o usuário no EMISSOR-FISCAL! Por favor, contate o setor de TI!");
			return ResponseEntity.badRequest().body(response);
		}
	}	

	@PutMapping("/alterar-usuario")
	public ResponseEntity<Response<Usuario>> edit (@RequestBody Usuario usuario) {
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		Optional<Usuario> opUser = usuarioService.findByLogin(usuario.getLogin());
		Response<Usuario> response;
		Optional<Usuario> opUserUpdated;
		if(opUser.isPresent()) {
			opUserUpdated =  usuarioService.edit(opUser, usuario);
		} else {
			response = ApiUtil.criaResponseDeErro("O usuário: " +usuario.getLogin()+ " NÃO está cadastrado no EMISSOR-FISCAL");
			return ResponseEntity.badRequest().body(response);
		}
		if (opUserUpdated.isPresent()) {
			return ResponseEntity.ok().build();
		} else {
			response = ApiUtil.criaResponseDeErro("Ocorreu algum ERRO, ao tentar ATUALIZAR o usuário no EMISSOR-FISCAL! Por favor, contate o setor de TI!");
			return ResponseEntity.badRequest().body(response);
		}
	
	}
}


