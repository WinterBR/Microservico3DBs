package br.com.winter.exceptions;

import java.util.NoSuchElementException;

public class ClienteNaoExistenteException extends NoSuchElementException {

    public ClienteNaoExistenteException(String mensagem) {
        super(mensagem);
    }
}
