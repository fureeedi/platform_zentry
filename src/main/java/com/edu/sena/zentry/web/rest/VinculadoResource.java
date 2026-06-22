package com.edu.sena.zentry.web.rest;

import com.edu.sena.zentry.repository.VinculadoRepository;
import com.edu.sena.zentry.service.VinculadoService;
import com.edu.sena.zentry.service.dto.VinculadoDTO;
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
 * REST controller for managing {@link com.edu.sena.zentry.domain.Vinculado}.
 */
@RestController
@RequestMapping("/api/vinculados")
public class VinculadoResource {

    private static final Logger LOG = LoggerFactory.getLogger(VinculadoResource.class);

    private static final String ENTITY_NAME = "vinculado";

    @Value("${jhipster.clientApp.name:zentry}")
    private String applicationName;

    private final VinculadoService vinculadoService;

    private final VinculadoRepository vinculadoRepository;

    public VinculadoResource(VinculadoService vinculadoService, VinculadoRepository vinculadoRepository) {
        this.vinculadoService = vinculadoService;
        this.vinculadoRepository = vinculadoRepository;
    }

    /**
     * {@code POST  /vinculados} : Create a new vinculado.
     *
     * @param vinculadoDTO the vinculadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vinculadoDTO, or with status {@code 400 (Bad Request)} if the vinculado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VinculadoDTO> createVinculado(@Valid @RequestBody VinculadoDTO vinculadoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Vinculado : {}", vinculadoDTO);
        if (vinculadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new vinculado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vinculadoDTO = vinculadoService.save(vinculadoDTO);
        return ResponseEntity.created(new URI("/api/vinculados/" + vinculadoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vinculadoDTO.getId()))
            .body(vinculadoDTO);
    }

    /**
     * {@code PUT  /vinculados/:id} : Updates an existing vinculado.
     *
     * @param id the id of the vinculadoDTO to save.
     * @param vinculadoDTO the vinculadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vinculadoDTO,
     * or with status {@code 400 (Bad Request)} if the vinculadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vinculadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VinculadoDTO> updateVinculado(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody VinculadoDTO vinculadoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Vinculado : {}, {}", id, vinculadoDTO);
        if (vinculadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vinculadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vinculadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vinculadoDTO = vinculadoService.update(vinculadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vinculadoDTO.getId()))
            .body(vinculadoDTO);
    }

    /**
     * {@code PATCH  /vinculados/:id} : Partial updates given fields of an existing vinculado, field will ignore if it is null
     *
     * @param id the id of the vinculadoDTO to save.
     * @param vinculadoDTO the vinculadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vinculadoDTO,
     * or with status {@code 400 (Bad Request)} if the vinculadoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vinculadoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vinculadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VinculadoDTO> partialUpdateVinculado(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody VinculadoDTO vinculadoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Vinculado partially : {}, {}", id, vinculadoDTO);
        if (vinculadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vinculadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vinculadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VinculadoDTO> result = vinculadoService.partialUpdate(vinculadoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vinculadoDTO.getId())
        );
    }

    /**
     * {@code GET  /vinculados} : get all the Vinculados.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Vinculados in body.
     */
    @GetMapping("")
    public ResponseEntity<List<VinculadoDTO>> getAllVinculados(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Vinculados");
        Page<VinculadoDTO> page;
        if (eagerload) {
            page = vinculadoService.findAllWithEagerRelationships(pageable);
        } else {
            page = vinculadoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vinculados/:id} : get the "id" vinculado.
     *
     * @param id the id of the vinculadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vinculadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VinculadoDTO> getVinculado(@PathVariable("id") String id) {
        LOG.debug("REST request to get Vinculado : {}", id);
        Optional<VinculadoDTO> vinculadoDTO = vinculadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vinculadoDTO);
    }

    /**
     * {@code DELETE  /vinculados/:id} : delete the "id" vinculado.
     *
     * @param id the id of the vinculadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVinculado(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Vinculado : {}", id);
        vinculadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
