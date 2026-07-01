package com.edu.sena.zentry.web.rest;

import com.edu.sena.zentry.repository.VinculadoInmuebleRepository;
import com.edu.sena.zentry.security.AuthoritiesConstants;
import com.edu.sena.zentry.service.VinculadoInmuebleService;
import com.edu.sena.zentry.service.dto.VinculadoInmuebleDTO;
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
 * REST controller for managing {@link com.edu.sena.zentry.domain.VinculadoInmueble}.
 */
@RestController
@RequestMapping("/api/vinculado-inmuebles")
public class VinculadoInmuebleResource {

    private static final Logger LOG = LoggerFactory.getLogger(VinculadoInmuebleResource.class);

    private static final String ENTITY_NAME = "vinculadoInmueble";

    @Value("${jhipster.clientApp.name:zentry}")
    private String applicationName;

    private final VinculadoInmuebleService vinculadoInmuebleService;

    private final VinculadoInmuebleRepository vinculadoInmuebleRepository;

    public VinculadoInmuebleResource(
        VinculadoInmuebleService vinculadoInmuebleService,
        VinculadoInmuebleRepository vinculadoInmuebleRepository
    ) {
        this.vinculadoInmuebleService = vinculadoInmuebleService;
        this.vinculadoInmuebleRepository = vinculadoInmuebleRepository;
    }

    /**
     * {@code POST  /vinculado-inmuebles} : Create a new vinculadoInmueble.
     *
     * @param vinculadoInmuebleDTO the vinculadoInmuebleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vinculadoInmuebleDTO, or with status {@code 400 (Bad Request)} if the vinculadoInmueble has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<VinculadoInmuebleDTO> createVinculadoInmueble(@Valid @RequestBody VinculadoInmuebleDTO vinculadoInmuebleDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save VinculadoInmueble : {}", vinculadoInmuebleDTO);
        if (vinculadoInmuebleDTO.getId() != null) {
            throw new BadRequestAlertException("A new vinculadoInmueble cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vinculadoInmuebleDTO = vinculadoInmuebleService.save(vinculadoInmuebleDTO);
        return ResponseEntity.created(new URI("/api/vinculado-inmuebles/" + vinculadoInmuebleDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, vinculadoInmuebleDTO.getId()))
            .body(vinculadoInmuebleDTO);
    }

    /**
     * {@code PUT  /vinculado-inmuebles/:id} : Updates an existing vinculadoInmueble.
     *
     * @param id the id of the vinculadoInmuebleDTO to save.
     * @param vinculadoInmuebleDTO the vinculadoInmuebleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vinculadoInmuebleDTO,
     * or with status {@code 400 (Bad Request)} if the vinculadoInmuebleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vinculadoInmuebleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<VinculadoInmuebleDTO> updateVinculadoInmueble(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody VinculadoInmuebleDTO vinculadoInmuebleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update VinculadoInmueble : {}, {}", id, vinculadoInmuebleDTO);
        if (vinculadoInmuebleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vinculadoInmuebleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vinculadoInmuebleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vinculadoInmuebleDTO = vinculadoInmuebleService.update(vinculadoInmuebleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vinculadoInmuebleDTO.getId()))
            .body(vinculadoInmuebleDTO);
    }

    /**
     * {@code PATCH  /vinculado-inmuebles/:id} : Partial updates given fields of an existing vinculadoInmueble, field will ignore if it is null
     *
     * @param id the id of the vinculadoInmuebleDTO to save.
     * @param vinculadoInmuebleDTO the vinculadoInmuebleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vinculadoInmuebleDTO,
     * or with status {@code 400 (Bad Request)} if the vinculadoInmuebleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vinculadoInmuebleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vinculadoInmuebleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<VinculadoInmuebleDTO> partialUpdateVinculadoInmueble(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody VinculadoInmuebleDTO vinculadoInmuebleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update VinculadoInmueble partially : {}, {}", id, vinculadoInmuebleDTO);
        if (vinculadoInmuebleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vinculadoInmuebleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vinculadoInmuebleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VinculadoInmuebleDTO> result = vinculadoInmuebleService.partialUpdate(vinculadoInmuebleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vinculadoInmuebleDTO.getId())
        );
    }

    /**
     * {@code GET  /vinculado-inmuebles} : get all the Vinculado Inmuebles.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Vinculado Inmuebles in body.
     */
    @GetMapping("")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<List<VinculadoInmuebleDTO>> getAllVinculadoInmuebles(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of VinculadoInmuebles");
        Page<VinculadoInmuebleDTO> page;
        if (eagerload) {
            page = vinculadoInmuebleService.findAllWithEagerRelationships(pageable);
        } else {
            page = vinculadoInmuebleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vinculado-inmuebles/:id} : get the "id" vinculadoInmueble.
     *
     * @param id the id of the vinculadoInmuebleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vinculadoInmuebleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<VinculadoInmuebleDTO> getVinculadoInmueble(@PathVariable("id") String id) {
        LOG.debug("REST request to get VinculadoInmueble : {}", id);
        Optional<VinculadoInmuebleDTO> vinculadoInmuebleDTO = vinculadoInmuebleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vinculadoInmuebleDTO);
    }

    /**
     * {@code DELETE  /vinculado-inmuebles/:id} : delete the "id" vinculadoInmueble.
     *
     * @param id the id of the vinculadoInmuebleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<Void> deleteVinculadoInmueble(@PathVariable("id") String id) {
        LOG.debug("REST request to delete VinculadoInmueble : {}", id);
        vinculadoInmuebleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
