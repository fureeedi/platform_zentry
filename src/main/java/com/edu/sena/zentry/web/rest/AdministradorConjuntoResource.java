package com.edu.sena.zentry.web.rest;

import com.edu.sena.zentry.repository.AdministradorConjuntoRepository;
import com.edu.sena.zentry.service.AdministradorConjuntoService;
import com.edu.sena.zentry.service.dto.AdministradorConjuntoDTO;
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
 * REST controller for managing {@link com.edu.sena.zentry.domain.AdministradorConjunto}.
 */
@RestController
@RequestMapping("/api/administrador-conjuntos")
public class AdministradorConjuntoResource {

    private static final Logger LOG = LoggerFactory.getLogger(AdministradorConjuntoResource.class);

    private static final String ENTITY_NAME = "administradorConjunto";

    @Value("${jhipster.clientApp.name:zentry}")
    private String applicationName;

    private final AdministradorConjuntoService administradorConjuntoService;

    private final AdministradorConjuntoRepository administradorConjuntoRepository;

    public AdministradorConjuntoResource(
        AdministradorConjuntoService administradorConjuntoService,
        AdministradorConjuntoRepository administradorConjuntoRepository
    ) {
        this.administradorConjuntoService = administradorConjuntoService;
        this.administradorConjuntoRepository = administradorConjuntoRepository;
    }

    /**
     * {@code POST  /administrador-conjuntos} : Create a new administradorConjunto.
     *
     * @param administradorConjuntoDTO the administradorConjuntoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new administradorConjuntoDTO, or with status {@code 400 (Bad Request)} if the administradorConjunto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdministradorConjuntoDTO> createAdministradorConjunto(
        @Valid @RequestBody AdministradorConjuntoDTO administradorConjuntoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save AdministradorConjunto : {}", administradorConjuntoDTO);
        if (administradorConjuntoDTO.getId() != null) {
            throw new BadRequestAlertException("A new administradorConjunto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        administradorConjuntoDTO = administradorConjuntoService.save(administradorConjuntoDTO);
        return ResponseEntity.created(new URI("/api/administrador-conjuntos/" + administradorConjuntoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, administradorConjuntoDTO.getId()))
            .body(administradorConjuntoDTO);
    }

    /**
     * {@code PUT  /administrador-conjuntos/:id} : Updates an existing administradorConjunto.
     *
     * @param id the id of the administradorConjuntoDTO to save.
     * @param administradorConjuntoDTO the administradorConjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administradorConjuntoDTO,
     * or with status {@code 400 (Bad Request)} if the administradorConjuntoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the administradorConjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdministradorConjuntoDTO> updateAdministradorConjunto(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody AdministradorConjuntoDTO administradorConjuntoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update AdministradorConjunto : {}, {}", id, administradorConjuntoDTO);
        if (administradorConjuntoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administradorConjuntoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administradorConjuntoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        administradorConjuntoDTO = administradorConjuntoService.update(administradorConjuntoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, administradorConjuntoDTO.getId()))
            .body(administradorConjuntoDTO);
    }

    /**
     * {@code PATCH  /administrador-conjuntos/:id} : Partial updates given fields of an existing administradorConjunto, field will ignore if it is null
     *
     * @param id the id of the administradorConjuntoDTO to save.
     * @param administradorConjuntoDTO the administradorConjuntoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administradorConjuntoDTO,
     * or with status {@code 400 (Bad Request)} if the administradorConjuntoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the administradorConjuntoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the administradorConjuntoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdministradorConjuntoDTO> partialUpdateAdministradorConjunto(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody AdministradorConjuntoDTO administradorConjuntoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AdministradorConjunto partially : {}, {}", id, administradorConjuntoDTO);
        if (administradorConjuntoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administradorConjuntoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administradorConjuntoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdministradorConjuntoDTO> result = administradorConjuntoService.partialUpdate(administradorConjuntoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, administradorConjuntoDTO.getId())
        );
    }

    /**
     * {@code GET  /administrador-conjuntos} : get all the Administrador Conjuntos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Administrador Conjuntos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AdministradorConjuntoDTO>> getAllAdministradorConjuntos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of AdministradorConjuntos");
        Page<AdministradorConjuntoDTO> page;
        if (eagerload) {
            page = administradorConjuntoService.findAllWithEagerRelationships(pageable);
        } else {
            page = administradorConjuntoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /administrador-conjuntos/:id} : get the "id" administradorConjunto.
     *
     * @param id the id of the administradorConjuntoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the administradorConjuntoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdministradorConjuntoDTO> getAdministradorConjunto(@PathVariable("id") String id) {
        LOG.debug("REST request to get AdministradorConjunto : {}", id);
        Optional<AdministradorConjuntoDTO> administradorConjuntoDTO = administradorConjuntoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(administradorConjuntoDTO);
    }

    /**
     * {@code DELETE  /administrador-conjuntos/:id} : delete the "id" administradorConjunto.
     *
     * @param id the id of the administradorConjuntoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministradorConjunto(@PathVariable("id") String id) {
        LOG.debug("REST request to delete AdministradorConjunto : {}", id);
        administradorConjuntoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
