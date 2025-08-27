package br.com.winter.usecase;

import br.com.winter.entity.Comercio;
import br.com.winter.exceptions.ComercioNaoExistenteException;
import br.com.winter.repository.IComercioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcluirComercioUsecase {

    private final IComercioRepository comercioRepository;

    @Autowired
    public ExcluirComercioUsecase(IComercioRepository comercioRepository) {
        this.comercioRepository = comercioRepository;
    }

    public void excluirComercio(Long id) {
        try{
            comercioRepository.deleteById(id);
        } catch (ComercioNaoExistenteException e) {
            throw new ComercioNaoExistenteException("O comércio de Id: " + id + " não existe");
        }
    }
}
