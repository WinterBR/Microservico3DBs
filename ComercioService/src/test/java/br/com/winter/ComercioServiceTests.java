package br.com.winter;

import br.com.winter.entity.Comercio;
import br.com.winter.exceptions.ComercioNaoExistenteException;
import br.com.winter.repository.IComercioRepository;
import br.com.winter.usecase.BuscarComercioUsecase;
import br.com.winter.usecase.CadastrarComercioUsecase;
import br.com.winter.usecase.ExcluirComercioUsecase;
import br.com.winter.usecase.ModificarComercioUsecase;
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
class ComercioServiceTests {

	@Autowired
	private BuscarComercioUsecase buscarComercioUsecase;

	@Autowired
	private CadastrarComercioUsecase cadastrarComercioUsecase;

	@Autowired
	private ExcluirComercioUsecase excluirComercioUsecase;

	@Autowired
	private ModificarComercioUsecase modificarComercioUsecase;

	@MockitoBean
	private IComercioRepository comercioRepository;

	Comercio comercio1;

	@BeforeEach
	void setup() {
		comercio1 = Comercio.builder()
				.id(1L)
				.nome("Mercado Leo")
				.tel("48999826911")
				.estado("SC")
				.build();

		when(comercioRepository.findById(1L)).thenReturn(Optional.of(comercio1));
		when(comercioRepository.save(Mockito.any(Comercio.class))).thenReturn(comercio1);
	}

	@Test
	void buscarComercioTest() {
		Comercio comercioExist = buscarComercioUsecase.buscarPorId(1L)
				.orElseThrow(() -> new ComercioNaoExistenteException("Comercio não encontrado"));
		assertThat(comercioExist).isNotNull();
		assertThat(comercioExist.getNome()).isEqualTo("Mercado Leo");
	}

	@Test
	void modificarComercioTest() {
		Comercio comercioAtualizado = Comercio.builder()
				.id(1L)
				.nome("Leo's Bar")
				.tel("49222884633")
				.build();

		when(comercioRepository.save(any(Comercio.class))).thenReturn(comercioAtualizado);

		modificarComercioUsecase.modificarComercio(1L, comercioAtualizado);

		verify(comercioRepository, times(1)).save(any(Comercio.class));
	}

	@Test
	void deletarComercioTest() {
		doNothing().when(comercioRepository).deleteById(1L);

		excluirComercioUsecase.excluirComercio(1L);

		verify(comercioRepository, times(1)).deleteById(1L);
	}
}
