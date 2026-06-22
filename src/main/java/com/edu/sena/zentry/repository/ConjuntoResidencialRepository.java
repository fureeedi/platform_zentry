package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.ConjuntoResidencial;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ConjuntoResidencial entity.
 */
@Repository
public interface ConjuntoResidencialRepository extends MongoRepository<ConjuntoResidencial, String> {}
