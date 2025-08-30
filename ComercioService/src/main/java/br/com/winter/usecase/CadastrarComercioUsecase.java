package br.com.winter.usecase;

import br.com.winter.entity.Comercio;
import br.com.winter.exceptions.ComercioJaExistenteException;
import br.com.winter.exceptions.DDDDaUFInvalidaException;
import br.com.winter.exceptions.DDDInvalidaException;
import br.com.winter.exceptions.UFInvalidaException;
import br.com.winter.repository.IComercioRepository;
import br.com.winter.validation.ComercioValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarComercioUsecase {

    private final IComercioRepository comercioRepository;
    private final ComercioValidation comercioValidation;

    @Autowired
    public CadastrarComercioUsecase(IComercioRepository comercioRepository, ComercioValidation comercioValidation) {
        this.comercioRepository = comercioRepository;
        this.comercioValidation = comercioValidation;
    }

    @Transactional
    public Comercio cadastrarComercio(@Valid Comercio comercio) {
        if (comercioRepository.findByNome(comercio.getNome()).isPresent()) {
            throw new ComercioJaExistenteException("O Nome já existe");
        }
        if (comercioRepository.findByTel(comercio.getTel()).isPresent()){
            throw new ComercioJaExistenteException("O Telefone já existe");
        }
        if (!comercioValidation.siglaValida(comercio)) {
            throw new UFInvalidaException("A UF: " + comercio.getEstado() + " não existe");
        }
        if (!comercioValidation.validarDDD(comercio)){
            throw new DDDInvalidaException("O DDD: " + comercio.getTel().substring(0, 2) + " não existe");
        }
        if (!comercioValidation.validarDDDporUFs(comercio)) {
            throw new DDDDaUFInvalidaException("O DDD: " + comercio.getTel().substring(0, 2) + " não pertence ao estado: " + comercio.getEstado());
        }
        return comercioRepository.save(comercio);
    }
}
