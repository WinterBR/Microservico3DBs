package br.com.winter;

import br.com.winter.entity.Cliente;
import br.com.winter.exceptions.ClienteNaoExistenteException;
import br.com.winter.repository.IClienteRepository;
import br.com.winter.usecase.BuscarClienteUsecase;
import br.com.winter.usecase.CadastrarClienteUsecase;
import br.com.winter.usecase.ExcluirClienteUsecase;
import br.com.winter.usecase.ModificarClienteUsecase;
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
class ClienteServiceTests {

	@Autowired
	private BuscarClienteUsecase buscarClienteUsecase;

	@Autowired
	private CadastrarClienteUsecase cadastrarClienteUsecase;

	@Autowired
	private ExcluirClienteUsecase excluirClienteUsecase;

	@Autowired
	private ModificarClienteUsecase modificarClienteUsecase;

	@MockitoBean
	private IClienteRepository clienteRepository;

	Cliente cliente1;

	@BeforeEach
	void setup() {
		cliente1 = Cliente.builder()
				.id(1L)
				.nome("Carlos Eduardo")
				.cpf("13991803432")
				.email("carlosEduard@gmail.com")
				.data(LocalDate.of(2005, 12, 5))
				.build();

		when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente1));
		when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente1);
	}

	@Test
	void buscarClienteTest() {
		Cliente clienteExist = buscarClienteUsecase.buscarPorId(1L)
				.orElseThrow(() -> new ClienteNaoExistenteException("Cliente não encontrado"));
		assertThat(clienteExist).isNotNull();
		assertThat(clienteExist.getNome()).isEqualTo("Carlos Eduardo");
	}

	@Test
	void modificarClienteTest() {
		Cliente clienteAtualizado = Cliente.builder()
				.id(1L)
				.nome("Eduardo")
				.data(LocalDate.of(2003, 10, 3))
				.build();

		when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);

		modificarClienteUsecase.modificarCliente(1L, clienteAtualizado);

		verify(clienteRepository, times(1)).save(any(Cliente.class));
	}

	@Test
	void deletarClienteTest() {
		doNothing().when(clienteRepository).deleteById(1L);

		excluirClienteUsecase.excluirCliente(1L);

		verify(clienteRepository, times(1)).deleteById(1L);
	}
}
