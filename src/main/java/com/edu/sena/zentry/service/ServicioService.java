package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.ServicioDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.Servicio}.
 */
public interface ServicioService {
    /**
     * Save a servicio.
     *
     * @param servicioDTO the entity to save.
     * @return the persisted entity.
     */
    ServicioDTO save(ServicioDTO servicioDTO);

    /**
     * Updates a servicio.
     *
     * @param servicioDTO the entity to update.
     * @return the persisted entity.
     */
    ServicioDTO update(ServicioDTO servicioDTO);

    /**
     * Partially updates a servicio.
     *
     * @param servicioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServicioDTO> partialUpdate(ServicioDTO servicioDTO);

    /**
     * Get all the servicios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServicioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" servicio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServicioDTO> findOne(String id);

    /**
     * Delete the "id" servicio.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
