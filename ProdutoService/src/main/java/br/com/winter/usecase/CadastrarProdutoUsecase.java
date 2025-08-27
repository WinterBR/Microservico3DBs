package br.com.winter.usecase;

import br.com.winter.entity.Produto;
import br.com.winter.exceptions.ProdutoJaExistenteException;
import br.com.winter.repository.IProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CadastrarProdutoUsecase {

    private final IProdutoRepository produtoRepository;

    public CadastrarProdutoUsecase(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrarProduto(@Valid Produto produto) {
        if (produtoRepository.findByMarca(produto.getMarca()).isPresent()) {
            throw new ProdutoJaExistenteException("A Marca: " + produto.getMarca() + " já existe");
        }
        if (produtoRepository.findByCodigo(produto.getCodigo()).isPresent()) {
            throw new ProdutoJaExistenteException("O Código: " + produto.getCodigo() + " já existe");
        }
        return produtoRepository.save(produto);
    }
}
