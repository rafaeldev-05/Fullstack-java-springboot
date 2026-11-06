package br.com.filasolidaria.repository;

import br.com.filasolidaria.model.PessoaFila;
import br.com.filasolidaria.model.StatusAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Isolates database access.
 * Spring Data JPA derives queries from method names.
 */
public interface PessoaFilaRepository extends JpaRepository<PessoaFila, Long> {

    List<PessoaFila> findAllByOrderByDataEntradaAsc();

    List<PessoaFila> findByStatusOrderByDataEntradaAsc(StatusAtendimento status);

    Optional<PessoaFila> findFirstByStatusOrderByDataEntradaAsc(StatusAtendimento status);
}
