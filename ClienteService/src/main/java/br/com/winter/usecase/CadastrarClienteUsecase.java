package br.com.winter.usecase;

import br.com.winter.entity.Cliente;
import br.com.winter.exceptions.ClienteJaExistenteException;
import br.com.winter.exceptions.IdadeIlegalException;
import br.com.winter.repository.IClienteRepository;
import br.com.winter.validation.ClienteValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastrarClienteUsecase {

    private final IClienteRepository clienteRepository;
    private final ClienteValidation dataValidation;

    @Autowired
    public CadastrarClienteUsecase(IClienteRepository clienteRepository, ClienteValidation dataValidation) {
        this.clienteRepository = clienteRepository;
        this.dataValidation = dataValidation;
    }

    public Cliente cadastrarCliente(@Valid Cliente cliente) throws IdadeIlegalException {
        if (clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
            throw new ClienteJaExistenteException("O CPF já existe");
        }
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new ClienteJaExistenteException("O Email já existe");
        } else {
            if(!dataValidation.maiorDeIdade(cliente)) {
                throw new IdadeIlegalException("O cliente foi registrado como nascido em: " + cliente.getData() + " fazendo dele menor de idade.");
            }
            return clienteRepository.save(cliente);
        }
    }
}
