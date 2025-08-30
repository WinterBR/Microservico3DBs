package br.com.winter.resource;

import br.com.winter.DTO.ProdutoRequestDTO;
import br.com.winter.DTO.ProdutoResponseDTO;
import br.com.winter.entity.Produto;
import br.com.winter.mapper.ProdutoMapper;
import br.com.winter.usecase.BuscarProdutoUsecase;
import br.com.winter.usecase.CadastrarProdutoUsecase;
import br.com.winter.usecase.ExcluirProdutoUsecase;
import br.com.winter.usecase.ModificarProdutoUsecase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoResource {

    private final BuscarProdutoUsecase buscarProdutoUsecase;
    private final CadastrarProdutoUsecase cadastrarProdutoUsecase;
    private final ExcluirProdutoUsecase excluirProdutoUsecase;
    private final ModificarProdutoUsecase modificarProdutoUsecase;

    @Autowired
    public ProdutoResource(BuscarProdutoUsecase buscarProdutoUsecase,
                           CadastrarProdutoUsecase cadastrarProdutoUsecase,
                           ExcluirProdutoUsecase excluirProdutoUsecase,
                           ModificarProdutoUsecase modificarProdutoUsecase) {
        this.buscarProdutoUsecase = buscarProdutoUsecase;
        this.cadastrarProdutoUsecase = cadastrarProdutoUsecase;
        this.excluirProdutoUsecase = excluirProdutoUsecase;
        this.modificarProdutoUsecase = modificarProdutoUsecase;
    }

    @GetMapping
    @Operation(summary = "Buscar todos os Produtos")
    public ResponseEntity<Page<ProdutoResponseDTO>> buscarTodos(Pageable pageable) {
        Page<ProdutoResponseDTO> page = buscarProdutoUsecase.buscarTodos(pageable)
                .map(ProdutoMapper::toDTO);
        return ResponseEntity.ok(page); // 200
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar Produto via ID")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            return buscarProdutoUsecase.buscarPorId(objectId)
                    .map(ProdutoMapper::toDTO)
                    .map(ResponseEntity::ok) // 200
                    .orElse(ResponseEntity.notFound().build()); // 404
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar Produto via código")
    public ResponseEntity<ProdutoResponseDTO> buscarPorCodigo(@PathVariable String codigo) {
        return buscarProdutoUsecase.buscarPorCodigo(codigo)
                .map(ProdutoMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/marca/{marca}")
    @Operation(summary = "Buscar Produto via marca")
    public ResponseEntity<ProdutoResponseDTO> buscarPorMarca(@PathVariable String marca) {
        return buscarProdutoUsecase.buscarPorMarca(marca)
                .map(ProdutoMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @PostMapping
    @Operation(summary = "Cadastrar Produto")
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO dto) {
        Produto novoProduto = cadastrarProdutoUsecase.cadastrarProduto(ProdutoMapper.toEntity(dto));
        return ResponseEntity
                .created(URI.create("/produto/id/" + novoProduto.getId().toHexString())) // 201
                .body(ProdutoMapper.toDTO(novoProduto));
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Modificar Produto via ID")
    public ResponseEntity<ProdutoResponseDTO> modificarProduto(@PathVariable String id,
                                                               @RequestBody @Valid ProdutoRequestDTO dto) {
        try {
            ObjectId objectId = new ObjectId(id);
            return buscarProdutoUsecase.buscarPorId(objectId)
                    .map(p -> {
                        Produto atualizado = modificarProdutoUsecase.modificarProduto(objectId, ProdutoMapper.toEntity(dto));
                        return ResponseEntity.ok(ProdutoMapper.toDTO(atualizado)); // 200
                    })
                    .orElse(ResponseEntity.notFound().build()); // 404
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Excluir Produto via ID")
    public ResponseEntity<Void> excluirProduto(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            if (buscarProdutoUsecase.buscarPorId(objectId).isPresent()) {
                excluirProdutoUsecase.excluirProduto(objectId);
                return ResponseEntity.noContent().build(); // 204
            } else {
                return ResponseEntity.notFound().build(); // 404
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400
        }
    }
}
