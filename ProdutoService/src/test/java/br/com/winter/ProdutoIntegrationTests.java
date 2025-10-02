package br.com.winter;

import br.com.winter.entity.Produto;
import br.com.winter.exceptions.ProdutoNaoExistenteException;
import br.com.winter.repository.IProdutoRepository;
import br.com.winter.usecase.BuscarProdutoUsecase;
import br.com.winter.usecase.CadastrarProdutoUsecase;
import br.com.winter.usecase.ExcluirProdutoUsecase;
import br.com.winter.usecase.ModificarProdutoUsecase;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProdutoIntegrationTests {

    @Autowired
    private BuscarProdutoUsecase buscarProdutoUsecase;

    @Autowired
    private CadastrarProdutoUsecase cadastrarProdutoUsecase;

    @Autowired
    private ExcluirProdutoUsecase excluirProdutoUsecase;

    @Autowired
    private ModificarProdutoUsecase modificarProdutoUsecase;

    @Autowired
    private IProdutoRepository produtoRepository;

    Produto produto1;

    @BeforeEach
    void setup() {
        produto1 = Produto.builder()
                .nome("Chocolate ao leite")
                .marca("Nestlé")
                .codigo("A01")
                .valor(6.45)
                .build();

        produto1 = produtoRepository.save(produto1);
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto produtoExist = buscarProdutoUsecase.buscarPorId(produto1.getId())
                .orElseThrow(() -> new ProdutoNaoExistenteException("Produto não encontrado"));

        assertThat(produtoExist).isNotNull();
        assertThat(produtoExist.getNome()).isEqualTo("Chocolate ao leite");
        assertThat(produtoExist.getMarca()).isEqualTo("Nestlé");
    }

    @Test
    void deveCadastrarNovoProduto() {
        Produto novo = Produto.builder()
                .nome("Biscoito Recheado")
                .marca("Bauducco")
                .codigo("B02")
                .valor(4.50)
                .build();

        Produto salvo = cadastrarProdutoUsecase.cadastrarProduto(novo);

        assertThat(salvo.getId()).isNotNull();
        assertThat(produtoRepository.findById(salvo.getId())).isPresent();
    }

    @Test
    void deveModificarProduto() {
        Produto produtoAtualizado = Produto.builder()
                .id(produto1.getId())
                .nome("Chocolate branco")
                .marca("Nestlé")
                .codigo("A01")
                .valor(7.00)
                .build();

        modificarProdutoUsecase.modificarProduto(produto1.getId(), produtoAtualizado);

        Produto salvo = produtoRepository.findById(produto1.getId())
                .orElseThrow();

        assertThat(salvo.getNome()).isEqualTo("Chocolate branco");
        assertThat(salvo.getValor()).isEqualTo(7.00);
    }

    @Test
    void deveDeletarProduto() {
        excluirProdutoUsecase.excluirProduto(produto1.getId());

        Optional<Produto> deleted = produtoRepository.findById(produto1.getId());
        assertThat(deleted).isEmpty();
    }

    @Test
    void buscarProdutoInexistenteDeveLancarExcecao() {
        assertThrows(ProdutoNaoExistenteException.class, () -> {
            buscarProdutoUsecase.buscarPorId(new ObjectId())
                    .orElseThrow(() -> new ProdutoNaoExistenteException("Produto não encontrado"));
        });
    }
}
