package br.com.murilo.locadora.model.exception;

public class ReservaInvalidaException extends RuntimeException{

    public ReservaInvalidaException(String message) {
        super(message);
    }
}
