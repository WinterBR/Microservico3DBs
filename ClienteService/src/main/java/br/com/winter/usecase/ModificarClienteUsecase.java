package br.com.winter.usecase;

import br.com.winter.entity.Cliente;
import br.com.winter.exceptions.ClienteJaExistenteException;
import br.com.winter.exceptions.ClienteNaoExistenteException;
import br.com.winter.exceptions.IdadeIlegalException;
import br.com.winter.repository.IClienteRepository;
import br.com.winter.validation.ClienteValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ModificarClienteUsecase {

    private final IClienteRepository clienteRepository;
    private final ClienteValidation dataValidation;

    @Autowired
    public ModificarClienteUsecase(IClienteRepository clienteRepository, ClienteValidation dataValidation) {
        this.clienteRepository = clienteRepository;
        this.dataValidation = dataValidation;
    }

    @Transactional
    public Cliente modificarCliente(Long id, @Valid Cliente cliente) throws ClienteNaoExistenteException, IdadeIlegalException {
        Optional<Cliente> clienteExistenteOpt = clienteRepository.findById(id);
        if (clienteExistenteOpt.isEmpty()) {
            throw new ClienteNaoExistenteException("O cliente com o Id: " + id + " não existe");
        }
        Cliente clienteExistente = clienteExistenteOpt.get();
        if (cliente.getNome() != null) {
            clienteExistente.setNome(cliente.getNome());
        }
        if (cliente.getData() != null) {
            clienteExistente.setData(cliente.getData());
        }
        if (cliente.getEmail() != null) {
            clienteExistente.setEmail(cliente.getEmail());
        }
        if (cliente.getCpf() != null) {
            clienteExistente.setCpf(cliente.getCpf());
        }
        if (!dataValidation.maiorDeIdade(clienteExistente)) {
            throw new IdadeIlegalException("O cliente foi registrado como nascido em: "
                    + clienteExistente.getData() + " fazendo dele menor de idade.");
        }
        if (cliente.getCpf() != null &&
                clienteRepository.findByCpf(clienteExistente.getCpf())
                        .filter(c -> !c.getId().equals(clienteExistente.getId()))
                        .isPresent()) {
            throw new ClienteJaExistenteException("CPF já existe");
        }
        if (cliente.getEmail() != null &&
                clienteRepository.findByEmail(clienteExistente.getEmail())
                        .filter(c -> !c.getId().equals(clienteExistente.getId()))
                        .isPresent()) {
            throw new ClienteJaExistenteException("Email já existe");
        }

        return clienteRepository.save(clienteExistente);
    }
}
