package br.com.winter.exceptions;

public class UFInvalidaException extends IllegalArgumentException{

    public UFInvalidaException(String mensagem) {
        super(mensagem);
    }
}
