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
class ComercioIntegrationTests {

    @Autowired
    private BuscarComercioUsecase buscarComercioUsecase;

    @Autowired
    private CadastrarComercioUsecase cadastrarComercioUsecase;

    @Autowired
    private ExcluirComercioUsecase excluirComercioUsecase;

    @Autowired
    private ModificarComercioUsecase modificarComercioUsecase;

    @Autowired
    private IComercioRepository comercioRepository;

    Comercio comercio1;

    @BeforeEach
    void setup() {
        comercio1 = Comercio.builder()
                .nome("Mercado Leo")
                .tel("48999826911")
                .estado("SC")
                .build();

        comercio1 = comercioRepository.save(comercio1);
    }

    @Test
    void deveBuscarComercioPorId() {
        Comercio comercioExist = buscarComercioUsecase.buscarPorId(comercio1.getId())
                .orElseThrow(() -> new ComercioNaoExistenteException("Comercio não encontrado"));

        assertThat(comercioExist).isNotNull();
        assertThat(comercioExist.getNome()).isEqualTo("Mercado Leo");
    }

    @Test
    void deveCadastrarNovoComercio() {
        Comercio novo = Comercio.builder()
                .nome("Padaria Central")
                .tel("41988887777")
                .estado("PR")
                .build();

        Comercio salvo = cadastrarComercioUsecase.cadastrarComercio(novo);

        assertThat(salvo.getId()).isNotNull();
        assertThat(comercioRepository.findById(salvo.getId())).isPresent();
    }

    @Test
    void deveModificarComercio() {
        Comercio comercioAtualizado = Comercio.builder()
                .id(comercio1.getId())
                .nome("Leo's Bar")
                .tel("49222884633")
                .estado("SC")
                .build();

        modificarComercioUsecase.modificarComercio(comercio1.getId(), comercioAtualizado);

        Comercio salvo = comercioRepository.findById(comercio1.getId())
                .orElseThrow();

        assertThat(salvo.getNome()).isEqualTo("Leo's Bar");
        assertThat(salvo.getEstado()).isEqualTo("SC");
    }

    @Test
    void deveDeletarComercio() {
        excluirComercioUsecase.excluirComercio(comercio1.getId());

        Optional<Comercio> deleted = comercioRepository.findById(comercio1.getId());
        assertThat(deleted).isEmpty();
    }

    @Test
    void buscarComercioInexistenteDeveLancarExcecao() {
        assertThrows(ComercioNaoExistenteException.class, () -> {
            buscarComercioUsecase.buscarPorId(999L)
                    .orElseThrow(() -> new ComercioNaoExistenteException("Comercio não encontrado"));
        });
    }
}
