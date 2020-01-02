package net.cartola.emissorfiscal.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *	26 de dez de 2019
 *	@author robson.costa
 */
@Repository
public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Long> {

}


