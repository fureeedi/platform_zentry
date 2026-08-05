package com.edu.sena.zentry.repository;

import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.Servicio;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Servicio entity.
 */
@Repository
public interface ServicioRepository extends MongoRepository<Servicio, String> {
    List<Servicio> findByAdministradorConjunto(AdministradorConjunto administradorConjunto);
}
