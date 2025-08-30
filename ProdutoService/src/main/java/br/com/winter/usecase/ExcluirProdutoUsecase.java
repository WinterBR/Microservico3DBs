package br.com.winter.usecase;

import br.com.winter.exceptions.ProdutoNaoExistenteException;
import br.com.winter.repository.IProdutoRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExcluirProdutoUsecase {

    private final IProdutoRepository produtoRepository;

    public ExcluirProdutoUsecase(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public void excluirProduto(ObjectId id) {
        try {
            produtoRepository.deleteById(id);
        } catch (ProdutoNaoExistenteException e) {
            throw new ProdutoNaoExistenteException("O produto de Id: " + id + " não existe");
        }
    }
}
