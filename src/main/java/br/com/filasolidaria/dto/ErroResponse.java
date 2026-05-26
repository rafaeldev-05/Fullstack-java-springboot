package br.com.filasolidaria.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO simples para padronizar respostas de erro da API.
 */
public class ErroResponse {

    private LocalDateTime dataHora;
    private List<String> mensagens;

    public ErroResponse(List<String> mensagens) {
        this.dataHora = LocalDateTime.now();
        this.mensagens = mensagens;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public List<String> getMensagens() {
        return mensagens;
    }
}
