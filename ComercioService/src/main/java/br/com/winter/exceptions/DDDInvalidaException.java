package br.com.winter.exceptions;

public class DDDInvalidaException extends IllegalArgumentException{

    public DDDInvalidaException(String mensagem) {
        super(mensagem);
    }
}
