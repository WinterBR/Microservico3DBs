package br.com.winter.exceptions;

public class ComercioJaExistenteException extends IllegalArgumentException{

    public ComercioJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
