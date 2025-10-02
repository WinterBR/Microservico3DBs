package br.com.winter.controller;

import br.com.winter.DTO.ClienteRequestDTO;
import br.com.winter.DTO.ClienteResponseDTO;
import br.com.winter.entity.Cliente;
import br.com.winter.mapper.ClienteMapper;
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
public class ClienteController {

    private final BuscarClienteUsecase buscarClienteUsecase;
    private final CadastrarClienteUsecase cadastrarClienteUsecase;
    private final ExcluirClienteUsecase excluirClienteUsecase;
    private final ModificarClienteUsecase modificarClienteUsecase;
    private final IClienteRepository clienteRepository;

    @Autowired
    public ClienteController(
            BuscarClienteUsecase buscarClienteUsecase,
            CadastrarClienteUsecase cadastrarClienteUsecase,
            ExcluirClienteUsecase excluirClienteUsecase,
            ModificarClienteUsecase modificarClienteUsecase,
            IClienteRepository clienteRepository) {
        this.buscarClienteUsecase = buscarClienteUsecase;
        this.cadastrarClienteUsecase = cadastrarClienteUsecase;
        this.excluirClienteUsecase = excluirClienteUsecase;
        this.modificarClienteUsecase = modificarClienteUsecase;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    @Operation(summary = "Buscar todos os Clientes")
    public ResponseEntity<Page<ClienteResponseDTO>> buscarTodos(Pageable pageable) {
        Page<ClienteResponseDTO> page = buscarClienteUsecase.buscarTodos(pageable)
                .map(ClienteMapper::toDTO);
        return ResponseEntity.ok(page); // 200
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar Cliente via ID")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return buscarClienteUsecase.buscarPorId(id)
                .map(ClienteMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar Cliente via CPF")
    public ResponseEntity<ClienteResponseDTO> buscarPorCpf(@PathVariable String cpf) {
        return buscarClienteUsecase.buscarPorCpf(cpf)
                .map(ClienteMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar Cliente via nome")
    public ResponseEntity<ClienteResponseDTO> buscarPorNome(@PathVariable String nome) {
        return buscarClienteUsecase.buscarPorNome(nome)
                .map(ClienteMapper::toDTO)
                .map(ResponseEntity::ok) // 200
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @PostMapping
    @Operation(summary = "Cadastrar Cliente")
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO dto) {
        Cliente novoCliente = cadastrarClienteUsecase.cadastrarCliente(ClienteMapper.toEntity(dto));
        return ResponseEntity
                .created(URI.create("/cliente/id/" + novoCliente.getId())) // 201
                .body(ClienteMapper.toDTO(novoCliente));
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Modificar Cliente via Id")
    public ResponseEntity<ClienteResponseDTO> modificarCliente(
            @PathVariable Long id,
            @RequestBody @Valid ClienteRequestDTO dto) {
        return clienteRepository.findById(id)
                .map(c -> {
                    Cliente atualizado = modificarClienteUsecase.modificarCliente(id, ClienteMapper.toEntity(dto));
                    return ResponseEntity.ok(ClienteMapper.toDTO(atualizado)); // 200
                })
                .orElse(ResponseEntity.notFound().build()); // 404
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Deletar Cliente via Id")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            excluirClienteUsecase.excluirCliente(id);
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
