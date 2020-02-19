package net.cartola.emissorfiscal.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.security.dto.UsuarioDTO;

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
//	public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody Usuario usuario) {
	public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		Optional<Usuario> opUsuario = usuarioService.save(usuario);
		if (opUsuario.isPresent()) {
//			return ResponseEntity.ok().body(usuarioService.convertToDto(opUsuario.get()));
			return ResponseEntity.ok().body(opUsuario.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

}


