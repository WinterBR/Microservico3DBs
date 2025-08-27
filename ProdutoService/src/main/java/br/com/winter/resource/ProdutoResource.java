package br.com.winter.resource;

import br.com.winter.entity.Produto;
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
    public ResponseEntity<Page<Produto>> buscarTodos(Pageable pageable) {
        return ResponseEntity.ok(buscarProdutoUsecase.buscarTodos(pageable)); // 200
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar Produto via ID")
    public ResponseEntity<Produto> buscarPorId(@PathVariable(value = "id") String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            return buscarProdutoUsecase.buscarPorId(objectId)
                    .map(ResponseEntity::ok) // 200
                    .orElse(ResponseEntity.notFound().build()); // 404
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar Produto via código")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable(value = "codigo") String codigo) {
        return buscarProdutoUsecase.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/marca/{marca}")
    @Operation(summary = "Buscar Produto via marca")
    public ResponseEntity<Produto> buscarPorMarca(@PathVariable(value = "marca") String marca) {
        return buscarProdutoUsecase.buscarPorMarca(marca)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @PostMapping
    @Operation(summary = "Cadastrar Produto")
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody @Valid Produto produto) {
        Produto novoProduto = cadastrarProdutoUsecase.cadastrarProduto(produto);
        return ResponseEntity
                .created(URI.create("/produto/id/" + novoProduto.getId())) // 201
                .body(novoProduto);
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Modificar Produto via ID")
    public ResponseEntity<Produto> modificarProduto(@PathVariable(value = "id") String id,
                                                    @RequestBody @Valid Produto produto) {
        try {
            ObjectId objectId = new ObjectId(id);
            return buscarProdutoUsecase.buscarPorId(objectId)
                    .map(p -> {
                        Produto atualizado = modificarProdutoUsecase.modificarProduto(objectId, produto);
                        return ResponseEntity.ok(atualizado); // 200
                    })
                    .orElse(ResponseEntity.notFound().build()); // 404
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400
        }
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Excluir Produto via ID")
    public ResponseEntity<Void> excluirProduto(@PathVariable(value = "id") String id) {
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
