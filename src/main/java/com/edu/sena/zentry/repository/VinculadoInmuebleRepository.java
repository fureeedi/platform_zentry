package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.VinculadoInmueble;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the VinculadoInmueble entity.
 */
@Repository
public interface VinculadoInmuebleRepository extends MongoRepository<VinculadoInmueble, String> {
    @Query("{}")
    Page<VinculadoInmueble> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<VinculadoInmueble> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<VinculadoInmueble> findOneWithEagerRelationships(String id);
}
