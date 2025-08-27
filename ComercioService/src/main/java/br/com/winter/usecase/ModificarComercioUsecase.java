package br.com.winter.usecase;

import br.com.winter.entity.Comercio;
import br.com.winter.exceptions.*;
import br.com.winter.repository.IComercioRepository;
import br.com.winter.validation.ComercioValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModificarComercioUsecase {

    private final IComercioRepository comercioRepository;
    private final ComercioValidation comercioValidation;

    @Autowired
    public ModificarComercioUsecase(IComercioRepository comercioRepository, ComercioValidation comercioValidation) {
        this.comercioRepository = comercioRepository;
        this.comercioValidation = comercioValidation;
    }

    public Comercio modificarComercio(Long id, @Valid Comercio comercio) throws ComercioNaoExistenteException{
        Optional<Comercio> comercioExistenteOpt = comercioRepository.findById(id);
        if(comercioExistenteOpt.isPresent()) {
            Comercio comercioExistente = comercioExistenteOpt.get();
            comercioExistente.setNome(comercio.getNome());
            comercioExistente.setTel(comercio.getTel());
            comercioExistente.setEstado(comercio.getEstado());
            if (!comercioValidation.siglaValida(comercio)) {
                throw new UFInvalidaException("A UF: " + comercio.getEstado() + " não existe");
            }
            if (!comercioValidation.validarDDD(comercio)){
                throw new DDDInvalidaException("O DDD: " + comercio.getTel().substring(0, 2) + " não existe");
            }
            if (!comercioValidation.validarDDDporUFs(comercio)) {
                throw new DDDDaUFInvalidaException("O DDD: " + comercio.getTel().substring(0, 2) + " não pertence ao estado: " + comercio.getEstado());
            }
            if (comercioRepository.findByNome(comercio.getNome())
                    .filter(c -> !c.getId().equals(comercioExistente.getId()))
                    .isPresent()) {
                throw new ComercioJaExistenteException("Nome já existe");
            }
            if (comercioRepository.findByTel(comercio.getTel())
                    .filter(c -> !c.getId().equals(comercioExistente.getId()))
                    .isPresent()) {
                throw new ComercioJaExistenteException("Telefone já existe");
            }
            return comercioRepository.save(comercioExistente);
        } else {
            throw new ComercioNaoExistenteException("O comercio com o Id: " + id + " não existe");
        }
    }
}
