package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.Inmueble;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Inmueble entity.
 */
@Repository
public interface InmuebleRepository extends MongoRepository<Inmueble, String> {
    @Query("{}")
    Page<Inmueble> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Inmueble> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Inmueble> findOneWithEagerRelationships(String id);
}
