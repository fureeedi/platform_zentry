package com.edu.sena.zentry.web.rest;

import static com.edu.sena.zentry.domain.ServicioConjuntoAsserts.*;
import static com.edu.sena.zentry.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.domain.Servicio;
import com.edu.sena.zentry.domain.ServicioConjunto;
import com.edu.sena.zentry.domain.enumeration.TipoDisponibilidad;
import com.edu.sena.zentry.repository.ServicioConjuntoRepository;
import com.edu.sena.zentry.service.ServicioConjuntoService;
import com.edu.sena.zentry.service.dto.ServicioConjuntoDTO;
import com.edu.sena.zentry.service.mapper.ServicioConjuntoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ServicioConjuntoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ServicioConjuntoResourceIT {

    private static final TipoDisponibilidad DEFAULT_DISPONIBLE = TipoDisponibilidad.ABIERTO;
    private static final TipoDisponibilidad UPDATED_DISPONIBLE = TipoDisponibilidad.CERRADO;

    private static final Integer DEFAULT_AFORO_MAXIMO = 1;
    private static final Integer UPDATED_AFORO_MAXIMO = 2;

    private static final String ENTITY_API_URL = "/api/servicio-conjuntos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServicioConjuntoRepository servicioConjuntoRepository;

    @Mock
    private ServicioConjuntoRepository servicioConjuntoRepositoryMock;

    @Autowired
    private ServicioConjuntoMapper servicioConjuntoMapper;

    @Mock
    private ServicioConjuntoService servicioConjuntoServiceMock;

    @Autowired
    private MockMvc restServicioConjuntoMockMvc;

    private ServicioConjunto servicioConjunto;

    private ServicioConjunto insertedServicioConjunto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicioConjunto createEntity() {
        ServicioConjunto servicioConjunto = new ServicioConjunto().disponible(DEFAULT_DISPONIBLE).aforoMaximo(DEFAULT_AFORO_MAXIMO);
        // Add required entity
        ConjuntoResidencial conjuntoResidencial;
        conjuntoResidencial = ConjuntoResidencialResourceIT.createEntity();
        conjuntoResidencial.setId("fixed-id-for-tests");
        servicioConjunto.setConjuntoResidencial(conjuntoResidencial);
        // Add required entity
        Servicio servicio;
        servicio = ServicioResourceIT.createEntity();
        servicio.setId("fixed-id-for-tests");
        servicioConjunto.setServicio(servicio);
        return servicioConjunto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicioConjunto createUpdatedEntity() {
        ServicioConjunto updatedServicioConjunto = new ServicioConjunto().disponible(UPDATED_DISPONIBLE).aforoMaximo(UPDATED_AFORO_MAXIMO);
        // Add required entity
        ConjuntoResidencial conjuntoResidencial;
        conjuntoResidencial = ConjuntoResidencialResourceIT.createUpdatedEntity();
        conjuntoResidencial.setId("fixed-id-for-tests");
        updatedServicioConjunto.setConjuntoResidencial(conjuntoResidencial);
        // Add required entity
        Servicio servicio;
        servicio = ServicioResourceIT.createUpdatedEntity();
        servicio.setId("fixed-id-for-tests");
        updatedServicioConjunto.setServicio(servicio);
        return updatedServicioConjunto;
    }

    @BeforeEach
    void initTest() {
        servicioConjunto = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedServicioConjunto != null) {
            servicioConjuntoRepository.delete(insertedServicioConjunto);
            insertedServicioConjunto = null;
        }
    }

    @Test
    void createServicioConjunto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServicioConjunto
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(servicioConjunto);
        var returnedServicioConjuntoDTO = om.readValue(
            restServicioConjuntoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicioConjuntoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServicioConjuntoDTO.class
        );

        // Validate the ServicioConjunto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServicioConjunto = servicioConjuntoMapper.toEntity(returnedServicioConjuntoDTO);
        assertServicioConjuntoUpdatableFieldsEquals(returnedServicioConjunto, getPersistedServicioConjunto(returnedServicioConjunto));

        insertedServicioConjunto = returnedServicioConjunto;
    }

    @Test
    void createServicioConjuntoWithExistingId() throws Exception {
        // Create the ServicioConjunto with an existing ID
        servicioConjunto.setId("existing_id");
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(servicioConjunto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicioConjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicioConjuntoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServicioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkDisponibleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        servicioConjunto.setDisponible(null);

        // Create the ServicioConjunto, which fails.
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(servicioConjunto);

        restServicioConjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicioConjuntoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAforoMaximoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        servicioConjunto.setAforoMaximo(null);

        // Create the ServicioConjunto, which fails.
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(servicioConjunto);

        restServicioConjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicioConjuntoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllServicioConjuntos() throws Exception {
        // Initialize the database
        insertedServicioConjunto = servicioConjuntoRepository.save(servicioConjunto);

        // Get all the servicioConjuntoList
        restServicioConjuntoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicioConjunto.getId())))
            .andExpect(jsonPath("$.[*].disponible").value(hasItem(DEFAULT_DISPONIBLE.toString())))
            .andExpect(jsonPath("$.[*].aforoMaximo").value(hasItem(DEFAULT_AFORO_MAXIMO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicioConjuntosWithEagerRelationshipsIsEnabled() throws Exception {
        when(servicioConjuntoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicioConjuntoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(servicioConjuntoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicioConjuntosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(servicioConjuntoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicioConjuntoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(servicioConjuntoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getServicioConjunto() throws Exception {
        // Initialize the database
        insertedServicioConjunto = servicioConjuntoRepository.save(servicioConjunto);

        // Get the servicioConjunto
        restServicioConjuntoMockMvc
            .perform(get(ENTITY_API_URL_ID, servicioConjunto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicioConjunto.getId()))
            .andExpect(jsonPath("$.disponible").value(DEFAULT_DISPONIBLE.toString()))
            .andExpect(jsonPath("$.aforoMaximo").value(DEFAULT_AFORO_MAXIMO));
    }

    @Test
    void getNonExistingServicioConjunto() throws Exception {
        // Get the servicioConjunto
        restServicioConjuntoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingServicioConjunto() throws Exception {
        // Initialize the database
        insertedServicioConjunto = servicioConjuntoRepository.save(servicioConjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicioConjunto
        ServicioConjunto updatedServicioConjunto = servicioConjuntoRepository.findById(servicioConjunto.getId()).orElseThrow();
        updatedServicioConjunto.disponible(UPDATED_DISPONIBLE).aforoMaximo(UPDATED_AFORO_MAXIMO);
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(updatedServicioConjunto);

        restServicioConjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicioConjuntoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicioConjuntoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ServicioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServicioConjuntoToMatchAllProperties(updatedServicioConjunto);
    }

    @Test
    void putNonExistingServicioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicioConjunto.setId(UUID.randomUUID().toString());

        // Create the ServicioConjunto
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(servicioConjunto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicioConjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicioConjuntoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicioConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchServicioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicioConjunto.setId(UUID.randomUUID().toString());

        // Create the ServicioConjunto
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(servicioConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioConjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicioConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamServicioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicioConjunto.setId(UUID.randomUUID().toString());

        // Create the ServicioConjunto
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(servicioConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioConjuntoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicioConjuntoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateServicioConjuntoWithPatch() throws Exception {
        // Initialize the database
        insertedServicioConjunto = servicioConjuntoRepository.save(servicioConjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicioConjunto using partial update
        ServicioConjunto partialUpdatedServicioConjunto = new ServicioConjunto();
        partialUpdatedServicioConjunto.setId(servicioConjunto.getId());

        partialUpdatedServicioConjunto.aforoMaximo(UPDATED_AFORO_MAXIMO);

        restServicioConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicioConjunto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicioConjunto))
            )
            .andExpect(status().isOk());

        // Validate the ServicioConjunto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicioConjuntoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServicioConjunto, servicioConjunto),
            getPersistedServicioConjunto(servicioConjunto)
        );
    }

    @Test
    void fullUpdateServicioConjuntoWithPatch() throws Exception {
        // Initialize the database
        insertedServicioConjunto = servicioConjuntoRepository.save(servicioConjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicioConjunto using partial update
        ServicioConjunto partialUpdatedServicioConjunto = new ServicioConjunto();
        partialUpdatedServicioConjunto.setId(servicioConjunto.getId());

        partialUpdatedServicioConjunto.disponible(UPDATED_DISPONIBLE).aforoMaximo(UPDATED_AFORO_MAXIMO);

        restServicioConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicioConjunto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicioConjunto))
            )
            .andExpect(status().isOk());

        // Validate the ServicioConjunto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicioConjuntoUpdatableFieldsEquals(
            partialUpdatedServicioConjunto,
            getPersistedServicioConjunto(partialUpdatedServicioConjunto)
        );
    }

    @Test
    void patchNonExistingServicioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicioConjunto.setId(UUID.randomUUID().toString());

        // Create the ServicioConjunto
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(servicioConjunto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicioConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicioConjuntoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicioConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchServicioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicioConjunto.setId(UUID.randomUUID().toString());

        // Create the ServicioConjunto
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(servicioConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicioConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamServicioConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicioConjunto.setId(UUID.randomUUID().toString());

        // Create the ServicioConjunto
        ServicioConjuntoDTO servicioConjuntoDTO = servicioConjuntoMapper.toDto(servicioConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioConjuntoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(servicioConjuntoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicioConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteServicioConjunto() throws Exception {
        // Initialize the database
        insertedServicioConjunto = servicioConjuntoRepository.save(servicioConjunto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicioConjunto
        restServicioConjuntoMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicioConjunto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return servicioConjuntoRepository.count();
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

    protected ServicioConjunto getPersistedServicioConjunto(ServicioConjunto servicioConjunto) {
        return servicioConjuntoRepository.findById(servicioConjunto.getId()).orElseThrow();
    }

    protected void assertPersistedServicioConjuntoToMatchAllProperties(ServicioConjunto expectedServicioConjunto) {
        assertServicioConjuntoAllPropertiesEquals(expectedServicioConjunto, getPersistedServicioConjunto(expectedServicioConjunto));
    }

    protected void assertPersistedServicioConjuntoToMatchUpdatableProperties(ServicioConjunto expectedServicioConjunto) {
        assertServicioConjuntoAllUpdatablePropertiesEquals(
            expectedServicioConjunto,
            getPersistedServicioConjunto(expectedServicioConjunto)
        );
    }
}
