package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.VinculadoInmuebleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.VinculadoInmueble}.
 */
public interface VinculadoInmuebleService {
    /**
     * Save a vinculadoInmueble.
     *
     * @param vinculadoInmuebleDTO the entity to save.
     * @return the persisted entity.
     */
    VinculadoInmuebleDTO save(VinculadoInmuebleDTO vinculadoInmuebleDTO);

    /**
     * Updates a vinculadoInmueble.
     *
     * @param vinculadoInmuebleDTO the entity to update.
     * @return the persisted entity.
     */
    VinculadoInmuebleDTO update(VinculadoInmuebleDTO vinculadoInmuebleDTO);

    /**
     * Partially updates a vinculadoInmueble.
     *
     * @param vinculadoInmuebleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VinculadoInmuebleDTO> partialUpdate(VinculadoInmuebleDTO vinculadoInmuebleDTO);

    /**
     * Get all the vinculadoInmuebles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VinculadoInmuebleDTO> findAll(Pageable pageable);

    /**
     * Get all the vinculadoInmuebles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VinculadoInmuebleDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" vinculadoInmueble.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VinculadoInmuebleDTO> findOne(String id);

    /**
     * Delete the "id" vinculadoInmueble.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
