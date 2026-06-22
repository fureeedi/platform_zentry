package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.Anuncios;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Anuncios entity.
 */
@Repository
public interface AnunciosRepository extends MongoRepository<Anuncios, String> {
    @Query("{}")
    Page<Anuncios> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Anuncios> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Anuncios> findOneWithEagerRelationships(String id);
}
