package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.ServicioConjunto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ServicioConjunto entity.
 */
@Repository
public interface ServicioConjuntoRepository extends MongoRepository<ServicioConjunto, String> {
    @Query("{}")
    Page<ServicioConjunto> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<ServicioConjunto> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<ServicioConjunto> findOneWithEagerRelationships(String id);
}
