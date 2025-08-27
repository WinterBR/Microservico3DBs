package br.com.winter.usecase;

import br.com.winter.entity.Comercio;
import br.com.winter.exceptions.ComercioNaoExistenteException;
import br.com.winter.repository.IComercioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuscarComercioUsecase {
    private final IComercioRepository comercioRepository;

    @Autowired
    public BuscarComercioUsecase(IComercioRepository comercioRepository) {
        this.comercioRepository = comercioRepository;
    }

    public Page<Comercio> buscarTodos(Pageable pageable) {
        return comercioRepository.findAll(pageable);
    }

    public Optional<Comercio> buscarPorId(Long id) {
        if (comercioRepository.findById(id).isPresent()) {
            return comercioRepository.findById(id);
        } else {
            throw new ComercioNaoExistenteException("O comércio de Id: " + id + " não existe");
        }
    }

    public Optional<Comercio> buscarPorNome(String nome) {
        if (comercioRepository.findByNome(nome).isPresent()) {
            return comercioRepository.findByNome(nome);
        } else {
            throw new ComercioNaoExistenteException("O comércio de Nome: " + nome + " não existe");
        }
    }

    public Optional<Comercio> buscarPorTel(String tel) {
        if (comercioRepository.findByTel(tel).isPresent()) {
            return comercioRepository.findByTel(tel);
        } else {
            throw new ComercioNaoExistenteException("O comércio de Telefone: " + tel + " não existe");
        }
    }

    public Optional<Comercio> buscarPorEstado(String estado) {
        if (comercioRepository.findByEstado(estado).isPresent()) {
            return comercioRepository.findByEstado(estado);
        } else {
            throw new ComercioNaoExistenteException("O comércio de Estado: " + estado + " não existe");
        }
    }
}
