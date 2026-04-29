package br.com.murilo.libraryapi.controller.common;

import br.com.murilo.libraryapi.controller.dto.ErroCampo;
import br.com.murilo.libraryapi.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // captura exceptions e dá uma resposta rest
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // faz ele capturar o erro
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //sempre vai retornar esse código de resposta
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldError = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldError
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                listaErros);
    }



}
