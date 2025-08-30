package br.com.winter.usecase;

import br.com.winter.entity.Produto;
import br.com.winter.exceptions.ProdutoJaExistenteException;
import br.com.winter.exceptions.ProdutoNaoExistenteException;
import br.com.winter.repository.IProdutoRepository;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ModificarProdutoUsecase {

    private final IProdutoRepository produtoRepository;

    public ModificarProdutoUsecase(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto modificarProduto(ObjectId id, @Valid Produto produto) throws ProdutoNaoExistenteException {
        Optional<Produto> produtoExistenteOpt = produtoRepository.findById(id);

        if (produtoExistenteOpt.isEmpty()) {
            throw new ProdutoNaoExistenteException("O produto com o Id: " + id + " não existe");
        }

        Produto produtoExistente = getProduto(produto, produtoExistenteOpt);

        if (produto.getCodigo() != null &&
                produtoRepository.findByCodigo(produtoExistente.getCodigo())
                        .filter(c -> !c.getId().equals(produtoExistente.getId()))
                        .isPresent()) {
            throw new ProdutoJaExistenteException("Código já existe");
        }

        return produtoRepository.save(produtoExistente);
    }

    private static Produto getProduto(Produto produto, Optional<Produto> produtoExistenteOpt) {
        Produto produtoExistente = produtoExistenteOpt.get();

        if (produto.getNome() != null) {
            produtoExistente.setNome(produto.getNome());
        }
        if (produto.getMarca() != null) {
            produtoExistente.setMarca(produto.getMarca());
        }
        if (produto.getValor() != null) {
            produtoExistente.setValor(produto.getValor());
        }
        if (produto.getCodigo() != null) {
            produtoExistente.setCodigo(produto.getCodigo());
        }
        return produtoExistente;
    }
}
