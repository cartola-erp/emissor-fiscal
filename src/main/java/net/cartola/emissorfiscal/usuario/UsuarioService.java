package net.cartola.emissorfiscal.usuario;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.cartola.emissorfiscal.security.dto.UsuarioDTO;

/**
 *	26 de dez de 2019
 *	@author robson.costa
 */
@Service(value = "userService")
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioPerfilRepository usuarioPerfilRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO convertToDto(Usuario usuario) {
		UsuarioDTO usuarioDto = modelMapper.map(usuario, UsuarioDTO.class);
		return usuarioDto;
	}
	
	public Usuario convertToEntity(UsuarioDTO usuarioDTO) throws ParseException {
		Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
		if (usuarioDTO.getId() != null ) {
			Usuario oldUsuario = findByLogin(usuarioDTO.getLogin()).get();
			usuario.setId(oldUsuario.getId());
		}
		return usuario;
	}
	
	/**
	 * Busca e retorna um usuário dado um login.
	 * 
	 * @param email
	 * @return Optional<Usuario>
	 */
	public Optional<Usuario> findByLogin(String login) {
//		Optional<Usuario> opUsuario = usuarioRepository.findByLogin(login);
//		if (opUsuario.isPresent()) {
//			Usuario usuario = opUsuario.get();
//			List<UsuarioPerfil> perfis = usuarioPerfilRepository.findByUsuario(usuario);
//			usuario.setPerfis(perfis);
//		}
//		return opUsuario;
		
//		Optional<Usuario> opUsuario = usuarioRepository.findUsuarioByLogin(login);
		return usuarioRepository.findUsuarioByLogin(login);
	}

	/**
	 * Busca e retorna um usuário dado um e-mail.
	 * 
	 * @param email
	 * @return Optional<Usuario>
	 */
//	public Optional<Usuario> buscarPorEmail(String email) {
//		Optional<Usuario> opUsuario = usuarioRepository.findByEmail(email);
//		if (opUsuario.isPresent()) {
//			Usuario usuario = opUsuario.get();
//			List<UsuarioPerfil> perfis = usuarioPerfilRepository.findByUsuario(usuario);
//			usuario.setPerfis(perfis);
//		}
//		return opUsuario;
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optional = findByLogin(username);
		if (optional.isPresent()) {
			Usuario usuario = optional.get();
			List<SimpleGrantedAuthority> perfis = getAuthority(usuario);
			return new org.springframework.security.core.userdetails.User(usuario.getLogin(), usuario.getSenha(),
					perfis);
		} else {
			throw new UsernameNotFoundException("Não encontrou o usuario para o login: " + username);
		}
	}

	private List<SimpleGrantedAuthority> getAuthority(Usuario usuario) {
		List<SimpleGrantedAuthority> list = new ArrayList<>();
		usuario.getPerfis().forEach(perfil -> {
			list.add(new SimpleGrantedAuthority(perfil.getPerfil().name()));
		});
		return list;
	}

	@Transactional
	public Optional<Usuario> save(Usuario usuario) {
		if (usuario.getPerfis() == null || usuario.getPerfis().isEmpty()) {
			return Optional.empty();
		} else {
			Optional<Usuario> opUsuario = Optional.ofNullable(usuarioRepository.saveAndFlush(usuario));
			usuario.getPerfis().forEach(perfil -> {
				perfil.setUsuario(usuario);
				usuarioPerfilRepository.save(perfil);
			});
			return opUsuario;
		}
	}
	
}


