package br.com.winter.exceptions;

public class ProdutoJaExistenteException extends IllegalArgumentException{

    public ProdutoJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
