package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.ServicioConjuntoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.ServicioConjunto}.
 */
public interface ServicioConjuntoService {
    /**
     * Save a servicioConjunto.
     *
     * @param servicioConjuntoDTO the entity to save.
     * @return the persisted entity.
     */
    ServicioConjuntoDTO save(ServicioConjuntoDTO servicioConjuntoDTO);

    /**
     * Updates a servicioConjunto.
     *
     * @param servicioConjuntoDTO the entity to update.
     * @return the persisted entity.
     */
    ServicioConjuntoDTO update(ServicioConjuntoDTO servicioConjuntoDTO);

    /**
     * Partially updates a servicioConjunto.
     *
     * @param servicioConjuntoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServicioConjuntoDTO> partialUpdate(ServicioConjuntoDTO servicioConjuntoDTO);

    /**
     * Get all the servicioConjuntos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServicioConjuntoDTO> findAll(Pageable pageable);

    /**
     * Get all the servicioConjuntos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServicioConjuntoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" servicioConjunto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServicioConjuntoDTO> findOne(String id);

    /**
     * Delete the "id" servicioConjunto.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
