package net.cartola.emissorfiscal.pessoa;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Optional<Pessoa> findPessoaByCnpj(String cnpj);
	
	Optional<Pessoa> findPessoaByCnpjOrCpf(String cnpj, String cpf);

	List<Pessoa> findPessoaByIdIn(Set<Long> pessoasId);

}
