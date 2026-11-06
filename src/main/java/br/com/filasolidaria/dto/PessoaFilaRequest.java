package br.com.filasolidaria.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Accepts the fields a user can provide when joining the queue.
 */
public class PessoaFilaRequest {

    @NotBlank(message = "Name is required.")
    private String nome;

    @NotBlank(message = "Phone number is required.")
    private String telefone;

    @NotBlank(message = "Service type is required.")
    private String tipoAtendimento;

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
}
