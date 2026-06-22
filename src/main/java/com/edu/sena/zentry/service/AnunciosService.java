package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.AnunciosDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.Anuncios}.
 */
public interface AnunciosService {
    /**
     * Save a anuncios.
     *
     * @param anunciosDTO the entity to save.
     * @return the persisted entity.
     */
    AnunciosDTO save(AnunciosDTO anunciosDTO);

    /**
     * Updates a anuncios.
     *
     * @param anunciosDTO the entity to update.
     * @return the persisted entity.
     */
    AnunciosDTO update(AnunciosDTO anunciosDTO);

    /**
     * Partially updates a anuncios.
     *
     * @param anunciosDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnunciosDTO> partialUpdate(AnunciosDTO anunciosDTO);

    /**
     * Get all the anuncioses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnunciosDTO> findAll(Pageable pageable);

    /**
     * Get all the anuncioses with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnunciosDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" anuncios.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnunciosDTO> findOne(String id);

    /**
     * Delete the "id" anuncios.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
