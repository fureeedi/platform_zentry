package com.edu.sena.zentry.service;

import com.edu.sena.zentry.service.dto.FacturaDePagoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.edu.sena.zentry.domain.FacturaDePago}.
 */
public interface FacturaDePagoService {
    /**
     * Save a facturaDePago.
     *
     * @param facturaDePagoDTO the entity to save.
     * @return the persisted entity.
     */
    FacturaDePagoDTO save(FacturaDePagoDTO facturaDePagoDTO);

    /**
     * Updates a facturaDePago.
     *
     * @param facturaDePagoDTO the entity to update.
     * @return the persisted entity.
     */
    FacturaDePagoDTO update(FacturaDePagoDTO facturaDePagoDTO);

    /**
     * Partially updates a facturaDePago.
     *
     * @param facturaDePagoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FacturaDePagoDTO> partialUpdate(FacturaDePagoDTO facturaDePagoDTO);

    /**
     * Get all the facturaDePagos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FacturaDePagoDTO> findAll(Pageable pageable);

    /**
     * Get all the facturaDePagos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FacturaDePagoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" facturaDePago.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FacturaDePagoDTO> findOne(String id);

    /**
     * Delete the "id" facturaDePago.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
