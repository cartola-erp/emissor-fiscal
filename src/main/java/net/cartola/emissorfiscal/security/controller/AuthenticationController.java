package net.cartola.emissorfiscal.security.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.security.config.JwtTokenUtil;
import net.cartola.emissorfiscal.usuario.Usuario;
import net.cartola.emissorfiscal.usuario.UsuarioService;

/**
 * 26 de dez de 2019
 * 
 * @author robson.costa
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/autenticacao")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UsuarioService userService;

//	public ResponseEntity<String> register(@RequestBody UsuarioDTO loginUser) throws AuthenticationException {
	@RequestMapping(value = "/obter-token", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody Usuario usuario) throws AuthenticationException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha()));
		final Optional<Usuario> user = userService.findByLogin(usuario.getLogin());
		if (user.isPresent()) {
			final String token = jwtTokenUtil.generateToken(user.get());
			return ResponseEntity.ok(token);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
