package br.com.winter.exceptions;

public class ClienteJaExistenteException extends IllegalArgumentException{

    public ClienteJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
