package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AdministradorConjunto entity.
 */
@Repository
public interface AdministradorConjuntoRepository extends MongoRepository<AdministradorConjunto, String> {
    @Query("{}")
    Page<AdministradorConjunto> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<AdministradorConjunto> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<AdministradorConjunto> findOneWithEagerRelationships(String id);

    Optional<AdministradorConjunto> findOneByUser(User user);
}
