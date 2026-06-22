package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.Reservas;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Reservas entity.
 */
@Repository
public interface ReservasRepository extends MongoRepository<Reservas, String> {
    @Query("{}")
    Page<Reservas> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Reservas> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Reservas> findOneWithEagerRelationships(String id);
}
