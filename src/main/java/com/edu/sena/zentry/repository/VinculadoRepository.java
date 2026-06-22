package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.Vinculado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Vinculado entity.
 */
@Repository
public interface VinculadoRepository extends MongoRepository<Vinculado, String> {
    @Query("{}")
    Page<Vinculado> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Vinculado> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Vinculado> findOneWithEagerRelationships(String id);
}
