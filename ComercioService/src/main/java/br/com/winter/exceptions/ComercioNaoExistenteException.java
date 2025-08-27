package br.com.winter.exceptions;

import java.util.NoSuchElementException;

public class ComercioNaoExistenteException extends NoSuchElementException {

    public ComercioNaoExistenteException(String mensagem) {
        super(mensagem);
    }
}
