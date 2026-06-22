package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.TipoDocumento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TipoDocumento entity.
 */
@Repository
public interface TipoDocumentoRepository extends MongoRepository<TipoDocumento, String> {}
