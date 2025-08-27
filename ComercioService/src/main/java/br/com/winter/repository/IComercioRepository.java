package br.com.winter.repository;

import br.com.winter.entity.Comercio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IComercioRepository extends JpaRepository<Comercio, Long> {
    Optional<Comercio> findByNome(String nome);
    Optional<Comercio> findByTel(String tel);
    Optional<Comercio> findByEstado(String estado);
}
