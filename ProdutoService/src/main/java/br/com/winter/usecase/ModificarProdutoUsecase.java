package br.com.winter.usecase;

import br.com.winter.entity.Produto;
import br.com.winter.exceptions.ProdutoJaExistenteException;
import br.com.winter.exceptions.ProdutoNaoExistenteException;
import br.com.winter.repository.IProdutoRepository;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModificarProdutoUsecase {

    private final IProdutoRepository produtoRepository;

    public ModificarProdutoUsecase(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto modificarProduto(ObjectId id, @Valid Produto produto) throws ProdutoNaoExistenteException{
        Optional<Produto> produtoExistenteOpt = produtoRepository.findById(id);
        if(produtoExistenteOpt.isPresent()) {
            Produto produtoExistente = produtoExistenteOpt.get();
            produtoExistente.setNome(produto.getNome());
            produtoExistente.setMarca(produto.getMarca());
            produtoExistente.setValor(produto.getValor());
            if (produtoRepository.findByCodigo(produto.getCodigo())
                    .filter(c -> !c.getId().equals(produtoExistente.getId()))
                    .isPresent()) {
                throw new ProdutoJaExistenteException("Nome já existe");
            }
            return produtoRepository.save(produtoExistente);
        } else {
            throw new ProdutoNaoExistenteException("O produto com o Id: " + id + " não existe");
        }
    }
}
