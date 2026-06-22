package com.edu.sena.zentry.web.rest;

import com.edu.sena.zentry.repository.ConjuntoResidencialRepository;
import com.edu.sena.zentry.service.ConjuntoResidencialService;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
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
 * REST controller for managing {@link com.edu.sena.zentry.domain.ConjuntoResidencial}.
 */
@RestController
@RequestMapping("/api/conjunto-residencials")
public class ConjuntoResidencialResource {

    private static final Logger LOG = LoggerFactory.getLogger(ConjuntoResidencialResource.class);

    private static final String ENTITY_NAME = "conjuntoResidencial";

    @Value("${jhipster.clientApp.name:zentry}")
    private String applicationName;

    private final ConjuntoResidencialService conjuntoResidencialService;

    private final ConjuntoResidencialRepository conjuntoResidencialRepository;

    public ConjuntoResidencialResource(
        ConjuntoResidencialService conjuntoResidencialService,
        ConjuntoResidencialRepository conjuntoResidencialRepository
    ) {
        this.conjuntoResidencialService = conjuntoResidencialService;
        this.conjuntoResidencialRepository = conjuntoResidencialRepository;
    }

    /**
     * {@code POST  /conjunto-residencials} : Create a new conjuntoResidencial.
     *
     * @param conjuntoResidencialDTO the conjuntoResidencialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conjuntoResidencialDTO, or with status {@code 400 (Bad Request)} if the conjuntoResidencial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConjuntoResidencialDTO> createConjuntoResidencial(
        @Valid @RequestBody ConjuntoResidencialDTO conjuntoResidencialDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save ConjuntoResidencial : {}", conjuntoResidencialDTO);
        if (conjuntoResidencialDTO.getId() != null) {
            throw new BadRequestAlertException("A new conjuntoResidencial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        conjuntoResidencialDTO = conjuntoResidencialService.save(conjuntoResidencialDTO);
        return ResponseEntity.created(new URI("/api/conjunto-residencials/" + conjuntoResidencialDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, conjuntoResidencialDTO.getId()))
            .body(conjuntoResidencialDTO);
    }

    /**
     * {@code PUT  /conjunto-residencials/:id} : Updates an existing conjuntoResidencial.
     *
     * @param id the id of the conjuntoResidencialDTO to save.
     * @param conjuntoResidencialDTO the conjuntoResidencialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conjuntoResidencialDTO,
     * or with status {@code 400 (Bad Request)} if the conjuntoResidencialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conjuntoResidencialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConjuntoResidencialDTO> updateConjuntoResidencial(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ConjuntoResidencialDTO conjuntoResidencialDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ConjuntoResidencial : {}, {}", id, conjuntoResidencialDTO);
        if (conjuntoResidencialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conjuntoResidencialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conjuntoResidencialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        conjuntoResidencialDTO = conjuntoResidencialService.update(conjuntoResidencialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, conjuntoResidencialDTO.getId()))
            .body(conjuntoResidencialDTO);
    }

    /**
     * {@code PATCH  /conjunto-residencials/:id} : Partial updates given fields of an existing conjuntoResidencial, field will ignore if it is null
     *
     * @param id the id of the conjuntoResidencialDTO to save.
     * @param conjuntoResidencialDTO the conjuntoResidencialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conjuntoResidencialDTO,
     * or with status {@code 400 (Bad Request)} if the conjuntoResidencialDTO is not valid,
     * or with status {@code 404 (Not Found)} if the conjuntoResidencialDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the conjuntoResidencialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConjuntoResidencialDTO> partialUpdateConjuntoResidencial(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ConjuntoResidencialDTO conjuntoResidencialDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ConjuntoResidencial partially : {}, {}", id, conjuntoResidencialDTO);
        if (conjuntoResidencialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conjuntoResidencialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conjuntoResidencialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConjuntoResidencialDTO> result = conjuntoResidencialService.partialUpdate(conjuntoResidencialDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, conjuntoResidencialDTO.getId())
        );
    }

    /**
     * {@code GET  /conjunto-residencials} : get all the Conjunto Residencials.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Conjunto Residencials in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ConjuntoResidencialDTO>> getAllConjuntoResidencials(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ConjuntoResidencials");
        Page<ConjuntoResidencialDTO> page = conjuntoResidencialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conjunto-residencials/:id} : get the "id" conjuntoResidencial.
     *
     * @param id the id of the conjuntoResidencialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conjuntoResidencialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConjuntoResidencialDTO> getConjuntoResidencial(@PathVariable("id") String id) {
        LOG.debug("REST request to get ConjuntoResidencial : {}", id);
        Optional<ConjuntoResidencialDTO> conjuntoResidencialDTO = conjuntoResidencialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conjuntoResidencialDTO);
    }

    /**
     * {@code DELETE  /conjunto-residencials/:id} : delete the "id" conjuntoResidencial.
     *
     * @param id the id of the conjuntoResidencialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConjuntoResidencial(@PathVariable("id") String id) {
        LOG.debug("REST request to delete ConjuntoResidencial : {}", id);
        conjuntoResidencialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
