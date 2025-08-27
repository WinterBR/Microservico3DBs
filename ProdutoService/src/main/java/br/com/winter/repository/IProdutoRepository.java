package br.com.winter.repository;

import br.com.winter.entity.Produto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProdutoRepository extends MongoRepository<Produto, Long> {
    void deleteById(ObjectId id);
    Optional<Produto> findById(ObjectId id);
    Optional<Produto> findByMarca(String codigo);
    Optional<Produto> findByCodigo(String codigo);
}
