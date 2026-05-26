package br.com.filasolidaria.repository;

import br.com.filasolidaria.model.PessoaFila;
import br.com.filasolidaria.model.StatusAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository isola o acesso ao banco de dados.
 * O Spring Data JPA implementa os metodos automaticamente pelo nome.
 */
public interface PessoaFilaRepository extends JpaRepository<PessoaFila, Long> {

    List<PessoaFila> findAllByOrderByDataEntradaAsc();

    List<PessoaFila> findByStatusOrderByDataEntradaAsc(StatusAtendimento status);

    Optional<PessoaFila> findFirstByStatusOrderByDataEntradaAsc(StatusAtendimento status);
}
