package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.AdministradorConjuntoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.AdministradorConjunto}.
 */
public interface AdministradorConjuntoService {
    /**
     * Save a administradorConjunto.
     *
     * @param administradorConjuntoDTO the entity to save.
     * @return the persisted entity.
     */
    AdministradorConjuntoDTO save(AdministradorConjuntoDTO administradorConjuntoDTO);

    /**
     * Updates a administradorConjunto.
     *
     * @param administradorConjuntoDTO the entity to update.
     * @return the persisted entity.
     */
    AdministradorConjuntoDTO update(AdministradorConjuntoDTO administradorConjuntoDTO);

    /**
     * Partially updates a administradorConjunto.
     *
     * @param administradorConjuntoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdministradorConjuntoDTO> partialUpdate(AdministradorConjuntoDTO administradorConjuntoDTO);

    /**
     * Get all the administradorConjuntos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdministradorConjuntoDTO> findAll(Pageable pageable);

    /**
     * Get all the administradorConjuntos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdministradorConjuntoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" administradorConjunto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdministradorConjuntoDTO> findOne(String id);

    /**
     * Delete the "id" administradorConjunto.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
