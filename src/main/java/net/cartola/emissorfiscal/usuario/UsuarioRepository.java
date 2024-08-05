package net.cartola.emissorfiscal.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *	26 de dez de 2019
 *	@author robson.costa
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findUsuarioByLogin(String login);

	Page<Usuario> findAll(Pageable pageable);

	Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}


