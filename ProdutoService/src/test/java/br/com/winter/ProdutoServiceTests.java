package br.com.winter;

import br.com.winter.entity.Produto;
import br.com.winter.exceptions.ProdutoNaoExistenteException;
import br.com.winter.repository.IProdutoRepository;
import br.com.winter.usecase.BuscarProdutoUsecase;
import br.com.winter.usecase.CadastrarProdutoUsecase;
import br.com.winter.usecase.ExcluirProdutoUsecase;
import br.com.winter.usecase.ModificarProdutoUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProdutoServiceTests {

	@Autowired
	private BuscarProdutoUsecase buscarProdutoUsecase;

	@Autowired
	private CadastrarProdutoUsecase cadastrarProdutoUsecase;

	@Autowired
	private ExcluirProdutoUsecase excluirProdutoUsecase;

	@Autowired
	private ModificarProdutoUsecase modificarProdutoUsecase;

	@MockitoBean
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

		when(produtoRepository.findById(produto1.getId())).thenReturn(Optional.of(produto1));
		when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(produto1);
	}

	@Test
	void buscarProdutoTest() {
		Produto produtoExist = buscarProdutoUsecase.buscarPorId(produto1.getId())
				.orElseThrow(() -> new ProdutoNaoExistenteException("Produto não encontrado"));
		assertThat(produtoExist).isNotNull();
		assertThat(produtoExist.getNome()).isEqualTo("Chocolate ao leite");
	}

	@Test
	void modificarProdutoTest() {
		Produto produtoAtualizado = Produto.builder()
				.nome("Chocolate branco")
				.valor(7.00)
				.build();

		when(produtoRepository.save(any(Produto.class))).thenReturn(produtoAtualizado);

		modificarProdutoUsecase.modificarProduto(produto1.getId(), produtoAtualizado);

		verify(produtoRepository, times(1)).save(any(Produto.class));
	}

	@Test
	void deletarProdutoTest() {
		doNothing().when(produtoRepository).deleteById(produto1.getId());

		excluirProdutoUsecase.excluirProduto(produto1.getId());

		verify(produtoRepository, times(1)).deleteById(produto1.getId());
	}
}
