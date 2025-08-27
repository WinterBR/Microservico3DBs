package br.com.winter.exceptions;

public class IdadeIlegalException extends IllegalArgumentException{

    public IdadeIlegalException(String mensagem) {
        super(mensagem);
    }
}
