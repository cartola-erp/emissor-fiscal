package net.cartola.emissorfiscal.pessoa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Optional<Pessoa> findPessoaByCnpj(Long cnpj);

}
