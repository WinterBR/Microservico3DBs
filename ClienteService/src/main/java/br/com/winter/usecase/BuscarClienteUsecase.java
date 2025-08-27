package br.com.winter.usecase;

import br.com.winter.entity.Cliente;
import br.com.winter.exceptions.ClienteNaoExistenteException;
import br.com.winter.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuscarClienteUsecase {

    private final IClienteRepository clienteRepository;

    @Autowired
    public BuscarClienteUsecase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> buscarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    public Optional<Cliente> buscarPorId(Long id) throws ClienteNaoExistenteException {
        if(clienteRepository.findById(id).isPresent()) {
            return clienteRepository.findById(id);
        } else {
            throw new ClienteNaoExistenteException("O cliente de Id: " + id + " não existe");
        }
    }

    public Optional<Cliente> buscarPorNome(String nome) throws ClienteNaoExistenteException {
        if(clienteRepository.findByNome(nome).isPresent()) {
            return clienteRepository.findByNome(nome);
        } else {
            throw new ClienteNaoExistenteException("O cliente de Nome: " + nome + " não existe");
        }
    }

    public Optional<Cliente> buscarPorCpf(String cpf) throws ClienteNaoExistenteException {
        if(clienteRepository.findByCpf(cpf).isPresent()) {
            return clienteRepository.findByCpf(cpf);
        } else {
            throw new ClienteNaoExistenteException("O cliente de CPF: " + cpf + " não existe");
        }
    }
}
