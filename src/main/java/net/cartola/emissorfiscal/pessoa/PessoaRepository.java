package net.cartola.emissorfiscal.pessoa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Optional<Pessoa> findPessoaByCnpj(String cnpj);
	
	Optional<Pessoa> findPessoaByCnpjOrCpf(String cnpj, String cpf);
}
