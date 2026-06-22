package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.VinculadoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.Vinculado}.
 */
public interface VinculadoService {
    /**
     * Save a vinculado.
     *
     * @param vinculadoDTO the entity to save.
     * @return the persisted entity.
     */
    VinculadoDTO save(VinculadoDTO vinculadoDTO);

    /**
     * Updates a vinculado.
     *
     * @param vinculadoDTO the entity to update.
     * @return the persisted entity.
     */
    VinculadoDTO update(VinculadoDTO vinculadoDTO);

    /**
     * Partially updates a vinculado.
     *
     * @param vinculadoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VinculadoDTO> partialUpdate(VinculadoDTO vinculadoDTO);

    /**
     * Get all the vinculados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VinculadoDTO> findAll(Pageable pageable);

    /**
     * Get all the vinculados with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VinculadoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" vinculado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VinculadoDTO> findOne(String id);

    /**
     * Delete the "id" vinculado.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
