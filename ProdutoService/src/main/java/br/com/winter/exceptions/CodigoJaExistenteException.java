package br.com.winter.exceptions;

public class CodigoJaExistenteException extends IllegalArgumentException{

    public CodigoJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
