package br.com.filasolidaria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

/**
 * JPA entity for a person registered in the virtual queue.
 * Fields are private to preserve encapsulation.
 */
@Entity
public class PessoaFila {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String tipoAtendimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAtendimento status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataEntrada;

    public PessoaFila() {
    }

    public PessoaFila(String nome, String telefone, String tipoAtendimento) {
        this.nome = nome;
        this.telefone = telefone;
        this.tipoAtendimento = tipoAtendimento;
        this.status = StatusAtendimento.AGUARDANDO;
    }

    /**
     * Sets defaults before persisting the entry.
     */
    @PrePersist
    public void antesDeSalvar() {
        if (status == null) {
            status = StatusAtendimento.AGUARDANDO;
        }

        if (dataEntrada == null) {
            dataEntrada = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public StatusAtendimento getStatus() {
        return status;
    }

    public void setStatus(StatusAtendimento status) {
        this.status = status;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }
}
