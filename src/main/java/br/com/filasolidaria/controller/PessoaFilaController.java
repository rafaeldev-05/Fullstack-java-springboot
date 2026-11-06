package br.com.filasolidaria.controller;

import br.com.filasolidaria.dto.PessoaFilaRequest;
import br.com.filasolidaria.model.PessoaFila;
import br.com.filasolidaria.service.PessoaFilaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Exposes the REST API used by the frontend.
 */
@RestController
@RequestMapping("/api/fila")
public class PessoaFilaController {

    private final PessoaFilaService service;

    public PessoaFilaController(PessoaFilaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PessoaFila> cadastrar(@Valid @RequestBody PessoaFilaRequest request) {
        PessoaFila pessoaCriada = service.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
    }

    @GetMapping
    public List<PessoaFila> listarTodos() {
        return service.listarTodosEmOrdemDeChegada();
    }

    @GetMapping("/aguardando")
    public List<PessoaFila> listarAguardando() {
        return service.listarAguardando();
    }

    @PatchMapping("/chamar-proximo")
    public PessoaFila chamarProximo() {
        return service.chamarProximaPessoa();
    }

    @PatchMapping("/{id}/atendido")
    public PessoaFila marcarComoAtendido(@PathVariable Long id) {
        return service.marcarComoAtendido(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
