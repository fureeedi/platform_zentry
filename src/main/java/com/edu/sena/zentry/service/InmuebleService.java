package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.InmuebleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.Inmueble}.
 */
public interface InmuebleService {
    /**
     * Save a inmueble.
     *
     * @param inmuebleDTO the entity to save.
     * @return the persisted entity.
     */
    InmuebleDTO save(InmuebleDTO inmuebleDTO);

    /**
     * Updates a inmueble.
     *
     * @param inmuebleDTO the entity to update.
     * @return the persisted entity.
     */
    InmuebleDTO update(InmuebleDTO inmuebleDTO);

    /**
     * Partially updates a inmueble.
     *
     * @param inmuebleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InmuebleDTO> partialUpdate(InmuebleDTO inmuebleDTO);

    /**
     * Get all the inmuebles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InmuebleDTO> findAll(Pageable pageable);

    /**
     * Get all the inmuebles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InmuebleDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" inmueble.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InmuebleDTO> findOne(String id);

    /**
     * Delete the "id" inmueble.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
