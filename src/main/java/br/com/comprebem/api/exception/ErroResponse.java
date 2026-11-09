package br.com.comprebem.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

// Corpo padrão de todas as respostas de erro da API.
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroResponse(int status, String mensagem, Map<String, String> campos) {
}
