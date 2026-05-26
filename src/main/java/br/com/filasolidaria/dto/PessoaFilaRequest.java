package br.com.filasolidaria.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada: recebe apenas os dados que o usuario pode informar.
 */
public class PessoaFilaRequest {

    @NotBlank(message = "Nome nao pode ser vazio.")
    private String nome;

    @NotBlank(message = "Telefone nao pode ser vazio.")
    private String telefone;

    @NotBlank(message = "Tipo de atendimento nao pode ser vazio.")
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
