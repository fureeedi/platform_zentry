package com.edu.sena.zentry.web.rest;

import static com.edu.sena.zentry.domain.AnunciosAsserts.*;
import static com.edu.sena.zentry.web.rest.TestUtil.createUpdateProxyForBean;
import static com.edu.sena.zentry.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.Anuncios;
import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.repository.AnunciosRepository;
import com.edu.sena.zentry.service.AnunciosService;
import com.edu.sena.zentry.service.dto.AnunciosDTO;
import com.edu.sena.zentry.service.mapper.AnunciosMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
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
 * Integration tests for the {@link AnunciosResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnunciosResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/anuncios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnunciosRepository anunciosRepository;

    @Mock
    private AnunciosRepository anunciosRepositoryMock;

    @Autowired
    private AnunciosMapper anunciosMapper;

    @Mock
    private AnunciosService anunciosServiceMock;

    @Autowired
    private MockMvc restAnunciosMockMvc;

    private Anuncios anuncios;

    private Anuncios insertedAnuncios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anuncios createEntity() {
        Anuncios anuncios = new Anuncios()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .fecha(DEFAULT_FECHA)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
        // Add required entity
        ConjuntoResidencial conjuntoResidencial;
        conjuntoResidencial = ConjuntoResidencialResourceIT.createEntity();
        conjuntoResidencial.setId("fixed-id-for-tests");
        anuncios.setConjuntoResidencial(conjuntoResidencial);
        // Add required entity
        AdministradorConjunto administradorConjunto;
        administradorConjunto = AdministradorConjuntoResourceIT.createEntity();
        administradorConjunto.setId("fixed-id-for-tests");
        anuncios.setAdministradorConjunto(administradorConjunto);
        return anuncios;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anuncios createUpdatedEntity() {
        Anuncios updatedAnuncios = new Anuncios()
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .fecha(UPDATED_FECHA)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        // Add required entity
        ConjuntoResidencial conjuntoResidencial;
        conjuntoResidencial = ConjuntoResidencialResourceIT.createUpdatedEntity();
        conjuntoResidencial.setId("fixed-id-for-tests");
        updatedAnuncios.setConjuntoResidencial(conjuntoResidencial);
        // Add required entity
        AdministradorConjunto administradorConjunto;
        administradorConjunto = AdministradorConjuntoResourceIT.createUpdatedEntity();
        administradorConjunto.setId("fixed-id-for-tests");
        updatedAnuncios.setAdministradorConjunto(administradorConjunto);
        return updatedAnuncios;
    }

    @BeforeEach
    void initTest() {
        anuncios = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAnuncios != null) {
            anunciosRepository.delete(insertedAnuncios);
            insertedAnuncios = null;
        }
    }

    @Test
    void createAnuncios() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Anuncios
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);
        var returnedAnunciosDTO = om.readValue(
            restAnunciosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anunciosDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnunciosDTO.class
        );

        // Validate the Anuncios in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAnuncios = anunciosMapper.toEntity(returnedAnunciosDTO);
        assertAnunciosUpdatableFieldsEquals(returnedAnuncios, getPersistedAnuncios(returnedAnuncios));

        insertedAnuncios = returnedAnuncios;
    }

    @Test
    void createAnunciosWithExistingId() throws Exception {
        // Create the Anuncios with an existing ID
        anuncios.setId("existing_id");
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnunciosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anunciosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Anuncios in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        anuncios.setTitulo(null);

        // Create the Anuncios, which fails.
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);

        restAnunciosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anunciosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        anuncios.setDescripcion(null);

        // Create the Anuncios, which fails.
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);

        restAnunciosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anunciosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFechaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        anuncios.setFecha(null);

        // Create the Anuncios, which fails.
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);

        restAnunciosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anunciosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllAnuncioses() throws Exception {
        // Initialize the database
        insertedAnuncios = anunciosRepository.save(anuncios);

        // Get all the anunciosList
        restAnunciosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anuncios.getId())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnunciosesWithEagerRelationshipsIsEnabled() throws Exception {
        when(anunciosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnunciosMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anunciosServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnunciosesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anunciosServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnunciosMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anunciosRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getAnuncios() throws Exception {
        // Initialize the database
        insertedAnuncios = anunciosRepository.save(anuncios);

        // Get the anuncios
        restAnunciosMockMvc
            .perform(get(ENTITY_API_URL_ID, anuncios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anuncios.getId()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN)));
    }

    @Test
    void getNonExistingAnuncios() throws Exception {
        // Get the anuncios
        restAnunciosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingAnuncios() throws Exception {
        // Initialize the database
        insertedAnuncios = anunciosRepository.save(anuncios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anuncios
        Anuncios updatedAnuncios = anunciosRepository.findById(anuncios.getId()).orElseThrow();
        updatedAnuncios
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .fecha(UPDATED_FECHA)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(updatedAnuncios);

        restAnunciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anunciosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anunciosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Anuncios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnunciosToMatchAllProperties(updatedAnuncios);
    }

    @Test
    void putNonExistingAnuncios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncios.setId(UUID.randomUUID().toString());

        // Create the Anuncios
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnunciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anunciosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anunciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anuncios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAnuncios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncios.setId(UUID.randomUUID().toString());

        // Create the Anuncios
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnunciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anunciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anuncios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAnuncios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncios.setId(UUID.randomUUID().toString());

        // Create the Anuncios
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnunciosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anunciosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anuncios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAnunciosWithPatch() throws Exception {
        // Initialize the database
        insertedAnuncios = anunciosRepository.save(anuncios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anuncios using partial update
        Anuncios partialUpdatedAnuncios = new Anuncios();
        partialUpdatedAnuncios.setId(anuncios.getId());

        partialUpdatedAnuncios.titulo(UPDATED_TITULO);

        restAnunciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnuncios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnuncios))
            )
            .andExpect(status().isOk());

        // Validate the Anuncios in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnunciosUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAnuncios, anuncios), getPersistedAnuncios(anuncios));
    }

    @Test
    void fullUpdateAnunciosWithPatch() throws Exception {
        // Initialize the database
        insertedAnuncios = anunciosRepository.save(anuncios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anuncios using partial update
        Anuncios partialUpdatedAnuncios = new Anuncios();
        partialUpdatedAnuncios.setId(anuncios.getId());

        partialUpdatedAnuncios
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .fecha(UPDATED_FECHA)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);

        restAnunciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnuncios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnuncios))
            )
            .andExpect(status().isOk());

        // Validate the Anuncios in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnunciosUpdatableFieldsEquals(partialUpdatedAnuncios, getPersistedAnuncios(partialUpdatedAnuncios));
    }

    @Test
    void patchNonExistingAnuncios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncios.setId(UUID.randomUUID().toString());

        // Create the Anuncios
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnunciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anunciosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anunciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anuncios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAnuncios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncios.setId(UUID.randomUUID().toString());

        // Create the Anuncios
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnunciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anunciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anuncios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAnuncios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncios.setId(UUID.randomUUID().toString());

        // Create the Anuncios
        AnunciosDTO anunciosDTO = anunciosMapper.toDto(anuncios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnunciosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anunciosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anuncios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAnuncios() throws Exception {
        // Initialize the database
        insertedAnuncios = anunciosRepository.save(anuncios);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anuncios
        restAnunciosMockMvc
            .perform(delete(ENTITY_API_URL_ID, anuncios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anunciosRepository.count();
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

    protected Anuncios getPersistedAnuncios(Anuncios anuncios) {
        return anunciosRepository.findById(anuncios.getId()).orElseThrow();
    }

    protected void assertPersistedAnunciosToMatchAllProperties(Anuncios expectedAnuncios) {
        assertAnunciosAllPropertiesEquals(expectedAnuncios, getPersistedAnuncios(expectedAnuncios));
    }

    protected void assertPersistedAnunciosToMatchUpdatableProperties(Anuncios expectedAnuncios) {
        assertAnunciosAllUpdatablePropertiesEquals(expectedAnuncios, getPersistedAnuncios(expectedAnuncios));
    }
}
