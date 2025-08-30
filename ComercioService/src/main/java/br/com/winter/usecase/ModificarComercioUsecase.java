package br.com.winter.usecase;

import br.com.winter.entity.Comercio;
import br.com.winter.exceptions.*;
import br.com.winter.repository.IComercioRepository;
import br.com.winter.validation.ComercioValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Comercio modificarComercio(Long id, @Valid Comercio comercio) throws ComercioNaoExistenteException {
        Optional<Comercio> comercioExistenteOpt = comercioRepository.findById(id);

        if (comercioExistenteOpt.isEmpty()) {
            throw new ComercioNaoExistenteException("O comércio com o Id: " + id + " não existe");
        }

        Comercio comercioExistente = comercioExistenteOpt.get();

        if (comercio.getNome() != null) {
            comercioExistente.setNome(comercio.getNome());
        }
        if (comercio.getTel() != null) {
            comercioExistente.setTel(comercio.getTel());
        }
        if (comercio.getEstado() != null) {
            comercioExistente.setEstado(comercio.getEstado());
        }

        if (!comercioValidation.siglaValida(comercioExistente)) {
            throw new UFInvalidaException("A UF: " + comercioExistente.getEstado() + " não existe");
        }
        if (!comercioValidation.validarDDD(comercioExistente)) {
            throw new DDDInvalidaException("O DDD: " + comercioExistente.getTel().substring(0, 2) + " não existe");
        }
        if (!comercioValidation.validarDDDporUFs(comercioExistente)) {
            throw new DDDDaUFInvalidaException("O DDD: " + comercioExistente.getTel().substring(0, 2)
                    + " não pertence ao estado: " + comercioExistente.getEstado());
        }

        if (comercio.getNome() != null &&
                comercioRepository.findByNome(comercioExistente.getNome())
                        .filter(c -> !c.getId().equals(comercioExistente.getId()))
                        .isPresent()) {
            throw new ComercioJaExistenteException("Nome já existe");
        }
        if (comercio.getTel() != null &&
                comercioRepository.findByTel(comercioExistente.getTel())
                        .filter(c -> !c.getId().equals(comercioExistente.getId()))
                        .isPresent()) {
            throw new ComercioJaExistenteException("Telefone já existe");
        }

        return comercioRepository.save(comercioExistente);
    }
}
