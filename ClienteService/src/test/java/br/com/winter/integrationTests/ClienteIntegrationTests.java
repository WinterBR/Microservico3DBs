package br.com.winter.integrationTests;

import br.com.winter.entity.Cliente;
import br.com.winter.exceptions.ClienteNaoExistenteException;
import br.com.winter.repository.IClienteRepository;
import br.com.winter.testContainers.AbstractIntegrationTest; // ← IMPORTE
import br.com.winter.usecase.BuscarClienteUsecase;
import br.com.winter.usecase.CadastrarClienteUsecase;
import br.com.winter.usecase.ExcluirClienteUsecase;
import br.com.winter.usecase.ModificarClienteUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ClienteIntegrationTests extends AbstractIntegrationTest {

    @Autowired
    private BuscarClienteUsecase buscarClienteUsecase;

    @Autowired
    private CadastrarClienteUsecase cadastrarClienteUsecase;

    @Autowired
    private ExcluirClienteUsecase excluirClienteUsecase;

    @Autowired
    private ModificarClienteUsecase modificarClienteUsecase;

    @Autowired
    private IClienteRepository clienteRepository;

    Cliente cliente1;

    @BeforeEach
    void setup() {
        cliente1 = Cliente.builder()
                .nome("Carlos Eduardo")
                .cpf("13991803432")
                .email("carlosEduard@gmail.com")
                .data(LocalDate.of(2005, 12, 5))
                .build();

        cliente1 = clienteRepository.save(cliente1);
    }

    @Test
    void deveBuscarClientePorId() {
        Cliente clienteExist = buscarClienteUsecase.buscarPorId(cliente1.getId())
                .orElseThrow(() -> new ClienteNaoExistenteException("Cliente não encontrado"));

        assertThat(clienteExist).isNotNull();
        assertThat(clienteExist.getNome()).isEqualTo("Carlos Eduardo");
    }

    @Test
    void deveModificarCliente() {
        Cliente clienteAtualizado = Cliente.builder()
                .id(cliente1.getId())
                .nome("Eduardo")
                .data(LocalDate.of(2003, 10, 3))
                .cpf(cliente1.getCpf())
                .email(cliente1.getEmail())
                .build();

        modificarClienteUsecase.modificarCliente(cliente1.getId(), clienteAtualizado);

        Cliente clienteSalvo = clienteRepository.findById(cliente1.getId())
                .orElseThrow();

        assertThat(clienteSalvo.getNome()).isEqualTo("Eduardo");
        assertThat(clienteSalvo.getData()).isEqualTo(LocalDate.of(2003, 10, 3));
    }

    @Test
    void deveDeletarCliente() {
        excluirClienteUsecase.excluirCliente(cliente1.getId());

        assertThrows(ClienteNaoExistenteException.class, () -> {
            buscarClienteUsecase.buscarPorId(cliente1.getId())
                    .orElseThrow(() -> new ClienteNaoExistenteException("Cliente não encontrado"));
        });
    }
}