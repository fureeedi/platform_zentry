package com.edu.sena.zentry.web.rest;

import com.edu.sena.zentry.repository.InmuebleRepository;
import com.edu.sena.zentry.security.AuthoritiesConstants;
import com.edu.sena.zentry.service.InmuebleService;
import com.edu.sena.zentry.service.dto.InmuebleDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.edu.sena.zentry.domain.Inmueble}.
 */
@RestController
@RequestMapping("/api/inmuebles")
public class InmuebleResource {

    private static final Logger LOG = LoggerFactory.getLogger(InmuebleResource.class);

    private static final String ENTITY_NAME = "inmueble";

    @Value("${jhipster.clientApp.name:zentry}")
    private String applicationName;

    private final InmuebleService inmuebleService;

    private final InmuebleRepository inmuebleRepository;

    public InmuebleResource(InmuebleService inmuebleService, InmuebleRepository inmuebleRepository) {
        this.inmuebleService = inmuebleService;
        this.inmuebleRepository = inmuebleRepository;
    }

    /**
     * {@code POST  /inmuebles} : Create a new inmueble.
     *
     * @param inmuebleDTO the inmuebleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inmuebleDTO, or with status {@code 400 (Bad Request)} if the inmueble has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<InmuebleDTO> createInmueble(@Valid @RequestBody InmuebleDTO inmuebleDTO) throws URISyntaxException {
        LOG.debug("REST request to save Inmueble : {}", inmuebleDTO);
        if (inmuebleDTO.getId() != null) {
            throw new BadRequestAlertException("A new inmueble cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inmuebleDTO = inmuebleService.save(inmuebleDTO);
        return ResponseEntity.created(new URI("/api/inmuebles/" + inmuebleDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inmuebleDTO.getId()))
            .body(inmuebleDTO);
    }

    /**
     * {@code PUT  /inmuebles/:id} : Updates an existing inmueble.
     *
     * @param id the id of the inmuebleDTO to save.
     * @param inmuebleDTO the inmuebleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inmuebleDTO,
     * or with status {@code 400 (Bad Request)} if the inmuebleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inmuebleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<InmuebleDTO> updateInmueble(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody InmuebleDTO inmuebleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Inmueble : {}, {}", id, inmuebleDTO);
        if (inmuebleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inmuebleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inmuebleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inmuebleDTO = inmuebleService.update(inmuebleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inmuebleDTO.getId()))
            .body(inmuebleDTO);
    }

    /**
     * {@code PATCH  /inmuebles/:id} : Partial updates given fields of an existing inmueble, field will ignore if it is null
     *
     * @param id the id of the inmuebleDTO to save.
     * @param inmuebleDTO the inmuebleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inmuebleDTO,
     * or with status {@code 400 (Bad Request)} if the inmuebleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inmuebleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inmuebleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<InmuebleDTO> partialUpdateInmueble(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody InmuebleDTO inmuebleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Inmueble partially : {}, {}", id, inmuebleDTO);
        if (inmuebleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inmuebleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inmuebleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InmuebleDTO> result = inmuebleService.partialUpdate(inmuebleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inmuebleDTO.getId())
        );
    }

    /**
     * {@code GET  /inmuebles} : get all the Inmuebles.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Inmuebles in body.
     */
    @GetMapping("")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<List<InmuebleDTO>> getAllInmuebles(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Inmuebles");
        Page<InmuebleDTO> page;
        if (eagerload) {
            page = inmuebleService.findAllWithEagerRelationships(pageable);
        } else {
            page = inmuebleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inmuebles/:id} : get the "id" inmueble.
     *
     * @param id the id of the inmuebleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inmuebleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<InmuebleDTO> getInmueble(@PathVariable("id") String id) {
        LOG.debug("REST request to get Inmueble : {}", id);
        Optional<InmuebleDTO> inmuebleDTO = inmuebleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inmuebleDTO);
    }

    /**
     * {@code DELETE  /inmuebles/:id} : delete the "id" inmueble.
     *
     * @param id the id of the inmuebleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<Void> deleteInmueble(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Inmueble : {}", id);
        inmuebleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
