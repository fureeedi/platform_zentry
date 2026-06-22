package com.edu.sena.zentry.web.rest;

import com.edu.sena.zentry.repository.AnunciosRepository;
import com.edu.sena.zentry.service.AnunciosService;
import com.edu.sena.zentry.service.dto.AnunciosDTO;
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
 * REST controller for managing {@link com.edu.sena.zentry.domain.Anuncios}.
 */
@RestController
@RequestMapping("/api/anuncios")
public class AnunciosResource {

    private static final Logger LOG = LoggerFactory.getLogger(AnunciosResource.class);

    private static final String ENTITY_NAME = "anuncios";

    @Value("${jhipster.clientApp.name:zentry}")
    private String applicationName;

    private final AnunciosService anunciosService;

    private final AnunciosRepository anunciosRepository;

    public AnunciosResource(AnunciosService anunciosService, AnunciosRepository anunciosRepository) {
        this.anunciosService = anunciosService;
        this.anunciosRepository = anunciosRepository;
    }

    /**
     * {@code POST  /anuncios} : Create a new anuncios.
     *
     * @param anunciosDTO the anunciosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anunciosDTO, or with status {@code 400 (Bad Request)} if the anuncios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnunciosDTO> createAnuncios(@Valid @RequestBody AnunciosDTO anunciosDTO) throws URISyntaxException {
        LOG.debug("REST request to save Anuncios : {}", anunciosDTO);
        if (anunciosDTO.getId() != null) {
            throw new BadRequestAlertException("A new anuncios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anunciosDTO = anunciosService.save(anunciosDTO);
        return ResponseEntity.created(new URI("/api/anuncios/" + anunciosDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, anunciosDTO.getId()))
            .body(anunciosDTO);
    }

    /**
     * {@code PUT  /anuncios/:id} : Updates an existing anuncios.
     *
     * @param id the id of the anunciosDTO to save.
     * @param anunciosDTO the anunciosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anunciosDTO,
     * or with status {@code 400 (Bad Request)} if the anunciosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anunciosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnunciosDTO> updateAnuncios(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody AnunciosDTO anunciosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Anuncios : {}, {}", id, anunciosDTO);
        if (anunciosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anunciosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anunciosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anunciosDTO = anunciosService.update(anunciosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anunciosDTO.getId()))
            .body(anunciosDTO);
    }

    /**
     * {@code PATCH  /anuncios/:id} : Partial updates given fields of an existing anuncios, field will ignore if it is null
     *
     * @param id the id of the anunciosDTO to save.
     * @param anunciosDTO the anunciosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anunciosDTO,
     * or with status {@code 400 (Bad Request)} if the anunciosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anunciosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anunciosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnunciosDTO> partialUpdateAnuncios(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody AnunciosDTO anunciosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Anuncios partially : {}, {}", id, anunciosDTO);
        if (anunciosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anunciosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anunciosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnunciosDTO> result = anunciosService.partialUpdate(anunciosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anunciosDTO.getId())
        );
    }

    /**
     * {@code GET  /anuncios} : get all the Anuncios.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Anuncios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AnunciosDTO>> getAllAnuncioses(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Anuncioses");
        Page<AnunciosDTO> page;
        if (eagerload) {
            page = anunciosService.findAllWithEagerRelationships(pageable);
        } else {
            page = anunciosService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /anuncios/:id} : get the "id" anuncios.
     *
     * @param id the id of the anunciosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anunciosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnunciosDTO> getAnuncios(@PathVariable("id") String id) {
        LOG.debug("REST request to get Anuncios : {}", id);
        Optional<AnunciosDTO> anunciosDTO = anunciosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anunciosDTO);
    }

    /**
     * {@code DELETE  /anuncios/:id} : delete the "id" anuncios.
     *
     * @param id the id of the anunciosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnuncios(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Anuncios : {}", id);
        anunciosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
