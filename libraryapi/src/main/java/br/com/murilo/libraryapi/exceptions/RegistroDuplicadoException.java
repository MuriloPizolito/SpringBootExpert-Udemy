package br.com.murilo.libraryapi.exceptions;

public class RegistroDuplicadoException extends RuntimeException{

    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
