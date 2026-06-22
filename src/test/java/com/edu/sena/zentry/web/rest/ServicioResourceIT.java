package com.edu.sena.zentry.web.rest;

import static com.edu.sena.zentry.domain.ServicioAsserts.*;
import static com.edu.sena.zentry.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.domain.Servicio;
import com.edu.sena.zentry.repository.ServicioRepository;
import com.edu.sena.zentry.service.dto.ServicioDTO;
import com.edu.sena.zentry.service.mapper.ServicioMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ServicioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServicioResourceIT {

    private static final String DEFAULT_DISPONIBILIDAD = "AAAAAAAAAA";
    private static final String UPDATED_DISPONIBILIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_ZONA_COMUN = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ZONA_COMUN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ServicioMapper servicioMapper;

    @Autowired
    private MockMvc restServicioMockMvc;

    private Servicio servicio;

    private Servicio insertedServicio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicio createEntity() {
        return new Servicio()
            .disponibilidad(DEFAULT_DISPONIBILIDAD)
            .nombreZonaComun(DEFAULT_NOMBRE_ZONA_COMUN)
            .descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicio createUpdatedEntity() {
        return new Servicio()
            .disponibilidad(UPDATED_DISPONIBILIDAD)
            .nombreZonaComun(UPDATED_NOMBRE_ZONA_COMUN)
            .descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    void initTest() {
        servicio = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedServicio != null) {
            servicioRepository.delete(insertedServicio);
            insertedServicio = null;
        }
    }

    @Test
    void createServicio() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Servicio
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);
        var returnedServicioDTO = om.readValue(
            restServicioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServicioDTO.class
        );

        // Validate the Servicio in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServicio = servicioMapper.toEntity(returnedServicioDTO);
        assertServicioUpdatableFieldsEquals(returnedServicio, getPersistedServicio(returnedServicio));

        insertedServicio = returnedServicio;
    }

    @Test
    void createServicioWithExistingId() throws Exception {
        // Create the Servicio with an existing ID
        servicio.setId("existing_id");
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkDisponibilidadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        servicio.setDisponibilidad(null);

        // Create the Servicio, which fails.
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        restServicioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNombreZonaComunIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        servicio.setNombreZonaComun(null);

        // Create the Servicio, which fails.
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        restServicioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllServicios() throws Exception {
        // Initialize the database
        insertedServicio = servicioRepository.save(servicio);

        // Get all the servicioList
        restServicioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicio.getId())))
            .andExpect(jsonPath("$.[*].disponibilidad").value(hasItem(DEFAULT_DISPONIBILIDAD)))
            .andExpect(jsonPath("$.[*].nombreZonaComun").value(hasItem(DEFAULT_NOMBRE_ZONA_COMUN)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    void getServicio() throws Exception {
        // Initialize the database
        insertedServicio = servicioRepository.save(servicio);

        // Get the servicio
        restServicioMockMvc
            .perform(get(ENTITY_API_URL_ID, servicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicio.getId()))
            .andExpect(jsonPath("$.disponibilidad").value(DEFAULT_DISPONIBILIDAD))
            .andExpect(jsonPath("$.nombreZonaComun").value(DEFAULT_NOMBRE_ZONA_COMUN))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    void getNonExistingServicio() throws Exception {
        // Get the servicio
        restServicioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingServicio() throws Exception {
        // Initialize the database
        insertedServicio = servicioRepository.save(servicio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicio
        Servicio updatedServicio = servicioRepository.findById(servicio.getId()).orElseThrow();
        updatedServicio.disponibilidad(UPDATED_DISPONIBILIDAD).nombreZonaComun(UPDATED_NOMBRE_ZONA_COMUN).descripcion(UPDATED_DESCRIPCION);
        ServicioDTO servicioDTO = servicioMapper.toDto(updatedServicio);

        restServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Servicio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServicioToMatchAllProperties(updatedServicio);
    }

    @Test
    void putNonExistingServicio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicio.setId(UUID.randomUUID().toString());

        // Create the Servicio
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchServicio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicio.setId(UUID.randomUUID().toString());

        // Create the Servicio
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamServicio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicio.setId(UUID.randomUUID().toString());

        // Create the Servicio
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateServicioWithPatch() throws Exception {
        // Initialize the database
        insertedServicio = servicioRepository.save(servicio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicio using partial update
        Servicio partialUpdatedServicio = new Servicio();
        partialUpdatedServicio.setId(servicio.getId());

        partialUpdatedServicio.nombreZonaComun(UPDATED_NOMBRE_ZONA_COMUN);

        restServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicio))
            )
            .andExpect(status().isOk());

        // Validate the Servicio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicioUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedServicio, servicio), getPersistedServicio(servicio));
    }

    @Test
    void fullUpdateServicioWithPatch() throws Exception {
        // Initialize the database
        insertedServicio = servicioRepository.save(servicio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicio using partial update
        Servicio partialUpdatedServicio = new Servicio();
        partialUpdatedServicio.setId(servicio.getId());

        partialUpdatedServicio
            .disponibilidad(UPDATED_DISPONIBILIDAD)
            .nombreZonaComun(UPDATED_NOMBRE_ZONA_COMUN)
            .descripcion(UPDATED_DESCRIPCION);

        restServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicio))
            )
            .andExpect(status().isOk());

        // Validate the Servicio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicioUpdatableFieldsEquals(partialUpdatedServicio, getPersistedServicio(partialUpdatedServicio));
    }

    @Test
    void patchNonExistingServicio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicio.setId(UUID.randomUUID().toString());

        // Create the Servicio
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchServicio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicio.setId(UUID.randomUUID().toString());

        // Create the Servicio
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamServicio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicio.setId(UUID.randomUUID().toString());

        // Create the Servicio
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(servicioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteServicio() throws Exception {
        // Initialize the database
        insertedServicio = servicioRepository.save(servicio);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicio
        restServicioMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return servicioRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Servicio getPersistedServicio(Servicio servicio) {
        return servicioRepository.findById(servicio.getId()).orElseThrow();
    }

    protected void assertPersistedServicioToMatchAllProperties(Servicio expectedServicio) {
        assertServicioAllPropertiesEquals(expectedServicio, getPersistedServicio(expectedServicio));
    }

    protected void assertPersistedServicioToMatchUpdatableProperties(Servicio expectedServicio) {
        assertServicioAllUpdatablePropertiesEquals(expectedServicio, getPersistedServicio(expectedServicio));
    }
}
