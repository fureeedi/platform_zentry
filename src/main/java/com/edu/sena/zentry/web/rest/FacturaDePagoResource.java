package com.edu.sena.zentry.web.rest;

import com.edu.sena.zentry.repository.FacturaDePagoRepository;
import com.edu.sena.zentry.service.FacturaDePagoService;
import com.edu.sena.zentry.service.dto.FacturaDePagoDTO;
import com.edu.sena.zentry.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.edu.sena.zentry.domain.FacturaDePago}.
 */
@RestController
@RequestMapping("/api/factura-de-pagos")
public class FacturaDePagoResource {

    private static final Logger LOG = LoggerFactory.getLogger(FacturaDePagoResource.class);

    private static final String ENTITY_NAME = "facturaDePago";

    @Value("${jhipster.clientApp.name:zentry}")
    private String applicationName;

    private final FacturaDePagoService facturaDePagoService;

    private final FacturaDePagoRepository facturaDePagoRepository;

    public FacturaDePagoResource(FacturaDePagoService facturaDePagoService, FacturaDePagoRepository facturaDePagoRepository) {
        this.facturaDePagoService = facturaDePagoService;
        this.facturaDePagoRepository = facturaDePagoRepository;
    }

    /**
     * {@code POST  /factura-de-pagos} : Create a new facturaDePago.
     *
     * @param facturaDePagoDTO the facturaDePagoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facturaDePagoDTO, or with status {@code 400 (Bad Request)} if the facturaDePago has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FacturaDePagoDTO> createFacturaDePago(@Valid @RequestBody FacturaDePagoDTO facturaDePagoDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save FacturaDePago : {}", facturaDePagoDTO);
        if (facturaDePagoDTO.getId() != null) {
            throw new BadRequestAlertException("A new facturaDePago cannot already have an ID", ENTITY_NAME, "idexists");
        }
        facturaDePagoDTO = facturaDePagoService.save(facturaDePagoDTO);
        return ResponseEntity.created(new URI("/api/factura-de-pagos/" + facturaDePagoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, facturaDePagoDTO.getId()))
            .body(facturaDePagoDTO);
    }

    /**
     * {@code PUT  /factura-de-pagos/:id} : Updates an existing facturaDePago.
     *
     * @param id the id of the facturaDePagoDTO to save.
     * @param facturaDePagoDTO the facturaDePagoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facturaDePagoDTO,
     * or with status {@code 400 (Bad Request)} if the facturaDePagoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facturaDePagoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FacturaDePagoDTO> updateFacturaDePago(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody FacturaDePagoDTO facturaDePagoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FacturaDePago : {}, {}", id, facturaDePagoDTO);
        if (facturaDePagoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facturaDePagoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facturaDePagoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        facturaDePagoDTO = facturaDePagoService.update(facturaDePagoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, facturaDePagoDTO.getId()))
            .body(facturaDePagoDTO);
    }

    /**
     * {@code PATCH  /factura-de-pagos/:id} : Partial updates given fields of an existing facturaDePago, field will ignore if it is null
     *
     * @param id the id of the facturaDePagoDTO to save.
     * @param facturaDePagoDTO the facturaDePagoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facturaDePagoDTO,
     * or with status {@code 400 (Bad Request)} if the facturaDePagoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the facturaDePagoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the facturaDePagoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FacturaDePagoDTO> partialUpdateFacturaDePago(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody FacturaDePagoDTO facturaDePagoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FacturaDePago partially : {}, {}", id, facturaDePagoDTO);
        if (facturaDePagoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facturaDePagoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facturaDePagoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FacturaDePagoDTO> result = facturaDePagoService.partialUpdate(facturaDePagoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, facturaDePagoDTO.getId())
        );
    }

    /**
     * {@code GET  /factura-de-pagos} : get all the Factura De Pagos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Factura De Pagos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FacturaDePagoDTO>> getAllFacturaDePagos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of FacturaDePagos");
        Page<FacturaDePagoDTO> page;
        if (eagerload) {
            page = facturaDePagoService.findAllWithEagerRelationships(pageable);
        } else {
            page = facturaDePagoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /factura-de-pagos/:id} : get the "id" facturaDePago.
     *
     * @param id the id of the facturaDePagoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facturaDePagoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FacturaDePagoDTO> getFacturaDePago(@PathVariable("id") String id) {
        LOG.debug("REST request to get FacturaDePago : {}", id);
        Optional<FacturaDePagoDTO> facturaDePagoDTO = facturaDePagoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facturaDePagoDTO);
    }

    /**
     * {@code DELETE  /factura-de-pagos/:id} : delete the "id" facturaDePago.
     *
     * @param id the id of the facturaDePagoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacturaDePago(@PathVariable("id") String id) {
        LOG.debug("REST request to delete FacturaDePago : {}", id);
        facturaDePagoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
