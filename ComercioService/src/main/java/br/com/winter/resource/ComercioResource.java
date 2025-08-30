package br.com.winter.resource;

import br.com.winter.DTO.ComercioRequestDTO;
import br.com.winter.DTO.ComercioResponseDTO;
import br.com.winter.entity.Comercio;
import br.com.winter.mapper.ComercioMapper;
import br.com.winter.repository.IComercioRepository;
import br.com.winter.usecase.BuscarComercioUsecase;
import br.com.winter.usecase.CadastrarComercioUsecase;
import br.com.winter.usecase.ExcluirComercioUsecase;
import br.com.winter.usecase.ModificarComercioUsecase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/comercio")
public class ComercioResource {

    private final BuscarComercioUsecase buscarComercioUsecase;
    private final CadastrarComercioUsecase cadastrarComercioUsecase;
    private final ExcluirComercioUsecase excluirComercioUsecase;
    private final ModificarComercioUsecase modificarComercioUsecase;
    private final IComercioRepository comercioRepository;

    @Autowired
    public ComercioResource(BuscarComercioUsecase buscarComercioUsecase,
                            CadastrarComercioUsecase cadastrarComercioUsecase,
                            ExcluirComercioUsecase excluirComercioUsecase,
                            ModificarComercioUsecase modificarComercioUsecase,
                            IComercioRepository comercioRepository) {
        this.buscarComercioUsecase = buscarComercioUsecase;
        this.cadastrarComercioUsecase = cadastrarComercioUsecase;
        this.excluirComercioUsecase = excluirComercioUsecase;
        this.modificarComercioUsecase = modificarComercioUsecase;
        this.comercioRepository = comercioRepository;
    }

    @GetMapping
    @Operation(summary = "Buscar todos os Comércios")
    public ResponseEntity<Page<ComercioResponseDTO>> buscarTodos(Pageable pageable) {
        Page<ComercioResponseDTO> page = buscarComercioUsecase.buscarTodos(pageable)
                .map(ComercioMapper::toDTO);
        return ResponseEntity.ok(page); // 200
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar Comércio via ID")
    public ResponseEntity<ComercioResponseDTO> buscarPorId(@PathVariable Long id) {
        return buscarComercioUsecase.buscarPorId(id)
                .map(ComercioMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar Comércio via nome")
    public ResponseEntity<ComercioResponseDTO> buscarPorNome(@PathVariable String nome) {
        return buscarComercioUsecase.buscarPorNome(nome)
                .map(ComercioMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/tel/{tel}")
    @Operation(summary = "Buscar Comércio via telefone")
    public ResponseEntity<ComercioResponseDTO> buscarPorTel(@PathVariable String tel) {
        return buscarComercioUsecase.buscarPorTel(tel)
                .map(ComercioMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar Comércio via estado")
    public ResponseEntity<ComercioResponseDTO> buscarPorEstado(@PathVariable String estado) {
        return buscarComercioUsecase.buscarPorEstado(estado)
                .map(ComercioMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @PostMapping
    @Operation(summary = "Cadastrar Comércio")
    public ResponseEntity<ComercioResponseDTO> cadastrarComercio(@RequestBody @Valid ComercioRequestDTO dto) {
        Comercio novoComercio = cadastrarComercioUsecase.cadastrarComercio(ComercioMapper.toEntity(dto));
        return ResponseEntity
                .created(URI.create("/comercio/id/" + novoComercio.getId())) // 201
                .body(ComercioMapper.toDTO(novoComercio));
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Modificar Comércio via ID")
    public ResponseEntity<ComercioResponseDTO> modificarComercio(@PathVariable Long id,
                                                                 @RequestBody @Valid ComercioRequestDTO dto) {
        return comercioRepository.findById(id)
                .map(c -> {
                    Comercio atualizado = modificarComercioUsecase.modificarComercio(id, ComercioMapper.toEntity(dto));
                    return ResponseEntity.ok(ComercioMapper.toDTO(atualizado)); // 200
                })
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Excluir Comércio via ID")
    public ResponseEntity<Void> excluirComercio(@PathVariable Long id) {
        if (comercioRepository.existsById(id)) {
            excluirComercioUsecase.excluirComercio(id);
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
