package br.com.winter.exceptions;

import java.util.NoSuchElementException;

public class ProdutoNaoExistenteException extends NoSuchElementException {

    public ProdutoNaoExistenteException(String mensagem) {
        super(mensagem);
    }
}
