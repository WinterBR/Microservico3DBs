package br.com.winter.resource;

import br.com.winter.entity.Cliente;
import br.com.winter.repository.IClienteRepository;
import br.com.winter.usecase.BuscarClienteUsecase;
import br.com.winter.usecase.CadastrarClienteUsecase;
import br.com.winter.usecase.ExcluirClienteUsecase;
import br.com.winter.usecase.ModificarClienteUsecase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteResource {

    private final BuscarClienteUsecase buscarClienteUsecase;
    private final CadastrarClienteUsecase cadastrarClienteUsecase;
    private final ExcluirClienteUsecase excluirClienteUsecase;
    private final ModificarClienteUsecase modificarClienteUsecase;
    private final IClienteRepository clienteRepository;

    @Autowired
    public ClienteResource(BuscarClienteUsecase buscarClienteUsecase, CadastrarClienteUsecase cadastrarClienteUsecase, ExcluirClienteUsecase excluirClienteUsecase, ModificarClienteUsecase modificarClienteUsecase, IClienteRepository clienteRepository) {
        this.buscarClienteUsecase = buscarClienteUsecase;
        this.cadastrarClienteUsecase = cadastrarClienteUsecase;
        this.excluirClienteUsecase = excluirClienteUsecase;
        this.modificarClienteUsecase = modificarClienteUsecase;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    @Operation(summary = "Buscar todos os Clientes")
    public ResponseEntity<Page<Cliente>> buscarTodos(Pageable pageable) {
        return ResponseEntity.ok(buscarClienteUsecase.buscarTodos(pageable)); // 200
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar Cliente via ID")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable(value = "id") Long id) {
        return buscarClienteUsecase.buscarPorId(id)
                .map(ResponseEntity::ok) //200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar Cliente via CPF")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable(value = "cpf") String cpf) {
        return buscarClienteUsecase.buscarPorCpf(cpf)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar Cliente via nome")
    public ResponseEntity<Cliente> buscarPorNome(@PathVariable(value = "nome") String nome) {
        return buscarClienteUsecase.buscarPorNome(nome)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @PostMapping
    @Operation(summary = "Cadastrar Cliente")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody @Valid Cliente cliente) {
        Cliente novoCliente = cadastrarClienteUsecase.cadastrarCliente(cliente);
        return ResponseEntity
                .created(URI.create("/cliente/id/" + novoCliente.getId())) // 201
                .body(novoCliente);
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Modificar Cliente via Id")
    public ResponseEntity<Cliente> modificarCliente(@PathVariable(value = "id") Long id, @RequestBody @Valid Cliente cliente) {
        return clienteRepository.findById(id)
                .map(c -> {
                    Cliente atualizado = modificarClienteUsecase.modificarCliente(id, cliente);
                    return ResponseEntity.ok(atualizado); // 200
                })
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Deletar Cliente via Id")
    public ResponseEntity<String> excluirCliente(@PathVariable(value = "id") Long id) {
        if (clienteRepository.existsById(id)) {
            excluirClienteUsecase.excluirCliente(id);
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }

}
