package br.com.winter.usecase;

import br.com.winter.entity.Produto;
import br.com.winter.exceptions.ProdutoNaoExistenteException;
import br.com.winter.repository.IProdutoRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuscarProdutoUsecase {

    private final IProdutoRepository produtoRepository;

    public BuscarProdutoUsecase(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Page<Produto> buscarTodos(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Optional<Produto> buscarPorId(ObjectId id) {
        if(produtoRepository.findById(id).isPresent()) {
            return produtoRepository.findById(id);
        } else {
            throw new ProdutoNaoExistenteException("O produto de Id: " + id + " não existe");
        }
    }

    public Optional<Produto> buscarPorCodigo(String codigo) {
        if(produtoRepository.findByCodigo(codigo).isPresent()) {
            return produtoRepository.findByCodigo(codigo);
        } else {
            throw new ProdutoNaoExistenteException("O produto de Código: " + codigo + " não existe");
        }
    }

    public Optional<Produto> buscarPorMarca(String marca) {
        if (produtoRepository.findByMarca(marca).isPresent()) {
            return produtoRepository.findByMarca(marca);
        } else {
            throw new ProdutoNaoExistenteException("O produto de Marca: " + marca + " não existe");
        }
    }
}
