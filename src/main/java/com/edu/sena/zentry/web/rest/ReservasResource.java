package com.edu.sena.zentry.web.rest;

import com.edu.sena.zentry.repository.ReservasRepository;
import com.edu.sena.zentry.security.AuthoritiesConstants;
import com.edu.sena.zentry.service.ReservasService;
import com.edu.sena.zentry.service.dto.ReservasDTO;
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
 * REST controller for managing {@link com.edu.sena.zentry.domain.Reservas}.
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservasResource {

    private static final Logger LOG = LoggerFactory.getLogger(ReservasResource.class);

    private static final String ENTITY_NAME = "reservas";

    @Value("${jhipster.clientApp.name:zentry}")
    private String applicationName;

    private final ReservasService reservasService;

    private final ReservasRepository reservasRepository;

    public ReservasResource(ReservasService reservasService, ReservasRepository reservasRepository) {
        this.reservasService = reservasService;
        this.reservasRepository = reservasRepository;
    }

    /**
     * {@code POST  /reservas} : Create a new reservas.
     *
     * @param reservasDTO the reservasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservasDTO, or with status {@code 400 (Bad Request)} if the reservas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.CLIENTE + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ReservasDTO> createReservas(@Valid @RequestBody ReservasDTO reservasDTO) throws URISyntaxException {
        LOG.debug("REST request to save Reservas : {}", reservasDTO);
        if (reservasDTO.getId() != null) {
            throw new BadRequestAlertException("A new reservas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        reservasDTO = reservasService.save(reservasDTO);
        return ResponseEntity.created(new URI("/api/reservas/" + reservasDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, reservasDTO.getId()))
            .body(reservasDTO);
    }

    /**
     * {@code PUT  /reservas/:id} : Updates an existing reservas.
     *
     * @param id the id of the reservasDTO to save.
     * @param reservasDTO the reservasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservasDTO,
     * or with status {@code 400 (Bad Request)} if the reservasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<ReservasDTO> updateReservas(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ReservasDTO reservasDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Reservas : {}, {}", id, reservasDTO);
        if (reservasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        reservasDTO = reservasService.update(reservasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reservasDTO.getId()))
            .body(reservasDTO);
    }

    /**
     * {@code PATCH  /reservas/:id} : Partial updates given fields of an existing reservas, field will ignore if it is null
     *
     * @param id the id of the reservasDTO to save.
     * @param reservasDTO the reservasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservasDTO,
     * or with status {@code 400 (Bad Request)} if the reservasDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reservasDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reservasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<ReservasDTO> partialUpdateReservas(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ReservasDTO reservasDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Reservas partially : {}, {}", id, reservasDTO);
        if (reservasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReservasDTO> result = reservasService.partialUpdate(reservasDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reservasDTO.getId())
        );
    }

    /**
     * {@code GET  /reservas} : get all the Reservas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Reservas in body.
     */
    @GetMapping("")
    @PreAuthorize(
        "hasAuthority(\"" +
            AuthoritiesConstants.ADMINISTRADOR_CONJUNTO +
            "\") or hasAuthority(\"" +
            AuthoritiesConstants.CLIENTE +
            "\") or hasAuthority(\"" +
            AuthoritiesConstants.ADMIN +
            "\")"
    )
    public ResponseEntity<List<ReservasDTO>> getAllReservases(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Reservases");
        Page<ReservasDTO> page;
        if (eagerload) {
            page = reservasService.findAllWithEagerRelationships(pageable);
        } else {
            page = reservasService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reservas/:id} : get the "id" reservas.
     *
     * @param id the id of the reservasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize(
        "hasAuthority(\"" +
            AuthoritiesConstants.ADMINISTRADOR_CONJUNTO +
            "\") or hasAuthority(\"" +
            AuthoritiesConstants.CLIENTE +
            "\") or hasAuthority(\"" +
            AuthoritiesConstants.ADMIN +
            "\")"
    )
    public ResponseEntity<ReservasDTO> getReservas(@PathVariable("id") String id) {
        LOG.debug("REST request to get Reservas : {}", id);
        Optional<ReservasDTO> reservasDTO = reservasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reservasDTO);
    }

    /**
     * {@code DELETE  /reservas/:id} : delete the "id" reservas.
     *
     * @param id the id of the reservasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize(
        "hasAuthority(\"" + AuthoritiesConstants.ADMINISTRADOR_CONJUNTO + "\") or hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")"
    )
    public ResponseEntity<Void> deleteReservas(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Reservas : {}", id);
        reservasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
