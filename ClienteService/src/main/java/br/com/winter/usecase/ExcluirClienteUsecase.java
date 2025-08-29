package br.com.winter.usecase;

import br.com.winter.exceptions.ClienteNaoExistenteException;
import br.com.winter.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExcluirClienteUsecase {

    private final IClienteRepository clienteRepository;

    @Autowired
    public ExcluirClienteUsecase(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public void excluirCliente(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (ClienteNaoExistenteException e) {
            throw new ClienteNaoExistenteException("O cliente de Id: " + id + " não existe");
        }

    }
}
