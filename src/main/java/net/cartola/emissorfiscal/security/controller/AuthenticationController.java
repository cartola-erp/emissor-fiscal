package net.cartola.emissorfiscal.security.controller;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import net.cartola.emissorfiscal.security.config.JwtTokenUtil;
import net.cartola.emissorfiscal.security.dto.UsuarioDTO;
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
	
	private static final Logger LOG = Logger.getLogger(AuthenticationController.class.getName());
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UsuarioService userService;

	@PostMapping(value = "/obter-token")
	public ResponseEntity<String> register(@RequestBody Usuario usuario) throws AuthenticationException {
		LOG.log(Level.INFO, "Usuário {0} obtendo token", usuario.getLogin());
//		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha()));
		final Optional<Usuario> user = userService.findByLogin(usuario.getLogin());
		if (user.isPresent()) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha()));
			Optional<UserDetails> opUserDetails = Optional.of(userService.loadUserByUsername(user.get().getLogin()));
			if(opUserDetails.isPresent()) {
				final String token = jwtTokenUtil.generateToken(user.get(), opUserDetails.get());
				return ResponseEntity.ok(token);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(value = "/renova-token") 
	public ResponseEntity<String> validateToken(@RequestBody UsuarioDTO userDto) {
		LOG.log(Level.INFO, "Renovando o token para o usuario: {0} ", userDto.getLogin());
		Optional<UserDetails> opUserDetails = Optional.of(userService.loadUserByUsername(userDto.getLogin()));
		if(!opUserDetails.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Boolean tokenExpired = null;
		try {
			tokenExpired = jwtTokenUtil.validateToken(userDto.getToken(), opUserDetails.get());
		} catch (ExpiredJwtException ex) {
			tokenExpired = true;
		}
		if (!tokenExpired) {
			return ResponseEntity.ok(userDto.getToken());
		} 
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getLogin(), userDto.getSenha()));
		final Optional<Usuario> user = userService.findByLogin(userDto.getLogin());
		if (user.isPresent()) {
			final String token = jwtTokenUtil.generateToken(user.get(), opUserDetails.get());
			return ResponseEntity.ok(token);
		} else {
			LOG.log(Level.INFO, "Usuário: {0} , não encontrado para renovar o token", userDto.getLogin());
			return ResponseEntity.notFound().build();
		}
	}
	
	
}
