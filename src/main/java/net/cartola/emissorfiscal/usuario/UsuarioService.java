package net.cartola.emissorfiscal.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public Optional<Usuario> edit(Optional<Usuario> opOldUser, Usuario userToUpdate) {
		if (opOldUser.isPresent()) {
			Usuario oldUser = opOldUser.get();
			oldUser.setLogin(userToUpdate.getLogin());
			oldUser.setNome(userToUpdate.getNome());
			oldUser.setEmail(userToUpdate.getEmail());
			oldUser.setSenha(userToUpdate.getSenha());
			// Deleting all old profiles
			usuarioPerfilRepository.deleteInBatch(oldUser.getPerfis());
			// Setting the "oldUser" (object) for the new profiles
			List<UsuarioPerfil> listPerfisToUpdate = userToUpdate.getPerfis();
			if (!listPerfisToUpdate.isEmpty()) {
				listPerfisToUpdate.forEach(perfil -> {
					perfil.setUsuario(oldUser);
				});
			}
			// Saving the new profiles for the user
			List<UsuarioPerfil> listPerfisUpdated = usuarioPerfilRepository.saveAll(listPerfisToUpdate);
			oldUser.setPerfis(listPerfisUpdated);
			// Updating the user information
			Optional<Usuario> opUserUpdated = Optional.ofNullable(usuarioRepository.saveAndFlush(oldUser));
			return opUserUpdated;
		}
		return Optional.empty();
	}
	
}


