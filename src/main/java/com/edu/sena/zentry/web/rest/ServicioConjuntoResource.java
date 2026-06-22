package com.edu.sena.zentry.web.rest;

import com.edu.sena.zentry.repository.ServicioConjuntoRepository;
import com.edu.sena.zentry.service.ServicioConjuntoService;
import com.edu.sena.zentry.service.dto.ServicioConjuntoDTO;
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
 * REST controller for managing {@link com.edu.sena.zentry.domain.ServicioConjunto}.
 */
@RestController
@RequestMapping("/api/servicio-conjuntos")
public class ServicioConjuntoResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServicioConjuntoResource.class);

    private static final String ENTITY_NAME = "servicioConjunto";

    @Value("${jhipster.clientApp.name:zentry}")
    private String applicationName;

    private final ServicioConjuntoService servicioConjuntoService;

    private final ServicioConjuntoRepository servicioConjuntoRepository;

    public ServicioConjuntoResource(
        ServicioConjuntoService servicioConjuntoService,
        ServicioConjuntoRepository servicioConjuntoRepository
    ) {
        this.servicioConjuntoService = servicioConjuntoService;
        this.servicioConjuntoRepository = servicioConjuntoRepository;
    }

    /**
     * {@code POST  /servicio-conjuntos} : Create a new servicioConjunto.
     *
     * @param servicioConjuntoDTO the servicioConjuntoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicioConjuntoDTO, or with status {@code 400 (Bad Request)} if the servicioConjunto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServicioConjuntoDTO> createServicioConjunto(@Valid @RequestBody ServicioConjuntoDTO servicioConjuntoDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ServicioConjunto : {}", servicioConjuntoDTO);
        if (servicioConjuntoDTO.getId() != null) {
            throw new BadRequestAlertException("A new servicioConjunto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        servicioConjuntoDTO = servicioConjuntoService.save(servicioConjuntoDTO);
        return ResponseEntity.created(new URI("/api/servicio-conjuntos/" + servicioConjuntoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, servicioConjuntoDTO.getId()))
            .body(servicioConjuntoDTO);
    }

    /**
     * {@code PUT  /servicio-conjuntos/:id} : Updates an existing servicioConjunto.
     *
     * @param id the id of the servicioConjuntoDTO to save.
     * @param servicioConjuntoDTO the servicioConjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicioConjuntoDTO,
     * or with status {@code 400 (Bad Request)} if the servicioConjuntoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicioConjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServicioConjuntoDTO> updateServicioConjunto(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ServicioConjuntoDTO servicioConjuntoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ServicioConjunto : {}, {}", id, servicioConjuntoDTO);
        if (servicioConjuntoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicioConjuntoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicioConjuntoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        servicioConjuntoDTO = servicioConjuntoService.update(servicioConjuntoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, servicioConjuntoDTO.getId()))
            .body(servicioConjuntoDTO);
    }

    /**
     * {@code PATCH  /servicio-conjuntos/:id} : Partial updates given fields of an existing servicioConjunto, field will ignore if it is null
     *
     * @param id the id of the servicioConjuntoDTO to save.
     * @param servicioConjuntoDTO the servicioConjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicioConjuntoDTO,
     * or with status {@code 400 (Bad Request)} if the servicioConjuntoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the servicioConjuntoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicioConjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServicioConjuntoDTO> partialUpdateServicioConjunto(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ServicioConjuntoDTO servicioConjuntoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ServicioConjunto partially : {}, {}", id, servicioConjuntoDTO);
        if (servicioConjuntoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicioConjuntoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicioConjuntoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServicioConjuntoDTO> result = servicioConjuntoService.partialUpdate(servicioConjuntoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, servicioConjuntoDTO.getId())
        );
    }

    /**
     * {@code GET  /servicio-conjuntos} : get all the Servicio Conjuntos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Servicio Conjuntos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServicioConjuntoDTO>> getAllServicioConjuntos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of ServicioConjuntos");
        Page<ServicioConjuntoDTO> page;
        if (eagerload) {
            page = servicioConjuntoService.findAllWithEagerRelationships(pageable);
        } else {
            page = servicioConjuntoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servicio-conjuntos/:id} : get the "id" servicioConjunto.
     *
     * @param id the id of the servicioConjuntoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicioConjuntoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicioConjuntoDTO> getServicioConjunto(@PathVariable("id") String id) {
        LOG.debug("REST request to get ServicioConjunto : {}", id);
        Optional<ServicioConjuntoDTO> servicioConjuntoDTO = servicioConjuntoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicioConjuntoDTO);
    }

    /**
     * {@code DELETE  /servicio-conjuntos/:id} : delete the "id" servicioConjunto.
     *
     * @param id the id of the servicioConjuntoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicioConjunto(@PathVariable("id") String id) {
        LOG.debug("REST request to delete ServicioConjunto : {}", id);
        servicioConjuntoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
