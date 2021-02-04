package net.cartola.emissorfiscal.pessoa;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 27 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface PessoaAlteradoSpedRepository extends JpaRepository<PessoaAlteradoSped, Long> { 

	/*
	 * Procura a pessoa juridica alterada numa determinada data
	 */
	Optional<PessoaAlteradoSped> findByCnpjAntInOrCnpjNovoInAndDtAlteracaoCadastro(Collection<String> cnpjAnt, Collection<String> cnpjNovo, LocalDate dtAlteracaoCadastro);
	
	/*
	 * Procura a Pessoa Física alterada numa determinada data
	 */
	Optional<PessoaAlteradoSped> findByCpfAntInOrCpfNovoInAndDtAlteracaoCadastro(Collection<String> cpfAnt, Collection<String> cpfNovo, LocalDate dtAlteracaoCadastro);
	
}
