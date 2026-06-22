package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.ConjuntoResidencial}.
 */
public interface ConjuntoResidencialService {
    /**
     * Save a conjuntoResidencial.
     *
     * @param conjuntoResidencialDTO the entity to save.
     * @return the persisted entity.
     */
    ConjuntoResidencialDTO save(ConjuntoResidencialDTO conjuntoResidencialDTO);

    /**
     * Updates a conjuntoResidencial.
     *
     * @param conjuntoResidencialDTO the entity to update.
     * @return the persisted entity.
     */
    ConjuntoResidencialDTO update(ConjuntoResidencialDTO conjuntoResidencialDTO);

    /**
     * Partially updates a conjuntoResidencial.
     *
     * @param conjuntoResidencialDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConjuntoResidencialDTO> partialUpdate(ConjuntoResidencialDTO conjuntoResidencialDTO);

    /**
     * Get all the conjuntoResidencials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConjuntoResidencialDTO> findAll(Pageable pageable);

    /**
     * Get the "id" conjuntoResidencial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConjuntoResidencialDTO> findOne(String id);

    /**
     * Delete the "id" conjuntoResidencial.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
