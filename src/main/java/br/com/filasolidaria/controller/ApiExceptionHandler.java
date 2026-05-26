package br.com.filasolidaria.controller;

import br.com.filasolidaria.dto.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Trata erros da API e devolve mensagens amigaveis para o frontend.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErroDeValidacao(MethodArgumentNotValidException exception) {
        List<String> mensagens = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(new ErroResponse(mensagens));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponse> tratarArgumentoInvalido(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(List.of(exception.getMessage())));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErroResponse> tratarEstadoInvalido(IllegalStateException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ErroResponse(List.of(exception.getMessage())));
    }
}
