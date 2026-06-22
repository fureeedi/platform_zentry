package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.FacturaDePago;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the FacturaDePago entity.
 */
@Repository
public interface FacturaDePagoRepository extends MongoRepository<FacturaDePago, String> {
    @Query("{}")
    Page<FacturaDePago> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<FacturaDePago> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<FacturaDePago> findOneWithEagerRelationships(String id);
}
