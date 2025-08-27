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

    public Cliente modificarCliente(Long id, @Valid Cliente cliente) throws ClienteNaoExistenteException, IdadeIlegalException {
        Optional<Cliente> clienteExistenteOpt = clienteRepository.findById(id);
        if(clienteExistenteOpt.isPresent()) {
            Cliente clienteExistente = clienteExistenteOpt.get();
            clienteExistente.setNome(cliente.getNome());
            clienteExistente.setData(cliente.getData());
            clienteExistente.setEmail(cliente.getEmail());
            if(dataValidation.maiorDeIdade(clienteExistente)) {
                if (clienteRepository.findByCpf(cliente.getCpf())
                        .filter(c -> !c.getId().equals(clienteExistente.getId()))
                        .isPresent()) {
                    throw new ClienteJaExistenteException("CPF já existe");
                }
                if (clienteRepository.findByEmail(cliente.getEmail())
                        .filter(c -> !c.getId().equals(clienteExistente.getId()))
                        .isPresent()) {
                    throw new ClienteJaExistenteException("Email já existe");
                }
                return clienteRepository.save(clienteExistente);
            } else {
                throw new IdadeIlegalException("O cliente foi registrado como nascido em: " + cliente.getData() + " fazendo dele menor de idade.");
            }
        } else {
            throw new ClienteNaoExistenteException("O cliente com o Id: " + id + " não existe");
        }
    }
}
