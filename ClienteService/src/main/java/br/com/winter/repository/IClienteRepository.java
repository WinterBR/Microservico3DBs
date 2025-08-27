package br.com.winter.repository;

import br.com.winter.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNome(String nome);
    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByEmail(String email);
}
