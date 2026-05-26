package br.com.filasolidaria.service;

import br.com.filasolidaria.dto.PessoaFilaRequest;
import br.com.filasolidaria.model.PessoaFila;
import br.com.filasolidaria.model.StatusAtendimento;
import br.com.filasolidaria.repository.PessoaFilaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service concentra as regras de negocio da fila.
 */
@Service
public class PessoaFilaService {

    private final PessoaFilaRepository repository;

    public PessoaFilaService(PessoaFilaRepository repository) {
        this.repository = repository;
    }

    public PessoaFila cadastrar(PessoaFilaRequest request) {
        PessoaFila pessoa = new PessoaFila(
                request.getNome().trim(),
                request.getTelefone().trim(),
                request.getTipoAtendimento().trim()
        );

        return repository.save(pessoa);
    }

    public List<PessoaFila> listarTodosEmOrdemDeChegada() {
        return repository.findAllByOrderByDataEntradaAsc();
    }

    public List<PessoaFila> listarAguardando() {
        return repository.findByStatusOrderByDataEntradaAsc(StatusAtendimento.AGUARDANDO);
    }

    public PessoaFila chamarProximaPessoa() {
        PessoaFila proximaPessoa = repository
                .findFirstByStatusOrderByDataEntradaAsc(StatusAtendimento.AGUARDANDO)
                .orElseThrow(() -> new IllegalStateException("Nao ha pessoas aguardando na fila."));

        proximaPessoa.setStatus(StatusAtendimento.CHAMADO);
        return repository.save(proximaPessoa);
    }

    public PessoaFila marcarComoAtendido(Long id) {
        PessoaFila pessoa = buscarPorId(id);

        if (pessoa.getStatus() != StatusAtendimento.CHAMADO) {
            throw new IllegalStateException("Apenas uma pessoa com status CHAMADO pode ser marcada como ATENDIDO.");
        }

        pessoa.setStatus(StatusAtendimento.ATENDIDO);
        return repository.save(pessoa);
    }

    public void remover(Long id) {
        PessoaFila pessoa = buscarPorId(id);
        repository.delete(pessoa);
    }

    private PessoaFila buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa nao encontrada na fila."));
    }
}
