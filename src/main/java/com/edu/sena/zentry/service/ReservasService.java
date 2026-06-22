package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.ReservasDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.Reservas}.
 */
public interface ReservasService {
    /**
     * Save a reservas.
     *
     * @param reservasDTO the entity to save.
     * @return the persisted entity.
     */
    ReservasDTO save(ReservasDTO reservasDTO);

    /**
     * Updates a reservas.
     *
     * @param reservasDTO the entity to update.
     * @return the persisted entity.
     */
    ReservasDTO update(ReservasDTO reservasDTO);

    /**
     * Partially updates a reservas.
     *
     * @param reservasDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReservasDTO> partialUpdate(ReservasDTO reservasDTO);

    /**
     * Get all the reservases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReservasDTO> findAll(Pageable pageable);

    /**
     * Get all the reservases with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReservasDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" reservas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReservasDTO> findOne(String id);

    /**
     * Delete the "id" reservas.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
