package com.edu.sena.zentry.web.rest;

import static com.edu.sena.zentry.domain.AdministradorConjuntoAsserts.*;
import static com.edu.sena.zentry.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.domain.TipoDocumento;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.repository.AdministradorConjuntoRepository;
import com.edu.sena.zentry.repository.UserRepository;
import com.edu.sena.zentry.service.AdministradorConjuntoService;
import com.edu.sena.zentry.service.dto.AdministradorConjuntoDTO;
import com.edu.sena.zentry.service.mapper.AdministradorConjuntoMapper;
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
 * Integration tests for the {@link AdministradorConjuntoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AdministradorConjuntoResourceIT {

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String ENTITY_API_URL = "/api/administrador-conjuntos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdministradorConjuntoRepository administradorConjuntoRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private AdministradorConjuntoRepository administradorConjuntoRepositoryMock;

    @Autowired
    private AdministradorConjuntoMapper administradorConjuntoMapper;

    @Mock
    private AdministradorConjuntoService administradorConjuntoServiceMock;

    @Autowired
    private MockMvc restAdministradorConjuntoMockMvc;

    private AdministradorConjunto administradorConjunto;

    private AdministradorConjunto insertedAdministradorConjunto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdministradorConjunto createEntity() {
        AdministradorConjunto administradorConjunto = new AdministradorConjunto()
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .activo(DEFAULT_ACTIVO);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        administradorConjunto.setUser(user);
        // Add required entity
        ConjuntoResidencial conjuntoResidencial;
        conjuntoResidencial = ConjuntoResidencialResourceIT.createEntity();
        conjuntoResidencial.setId("fixed-id-for-tests");
        administradorConjunto.setConjuntoResidencial(conjuntoResidencial);
        // Add required entity
        TipoDocumento tipoDocumento;
        tipoDocumento = TipoDocumentoResourceIT.createEntity();
        tipoDocumento.setId("fixed-id-for-tests");
        administradorConjunto.setTipoDocumento(tipoDocumento);
        return administradorConjunto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdministradorConjunto createUpdatedEntity() {
        AdministradorConjunto updatedAdministradorConjunto = new AdministradorConjunto()
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .activo(UPDATED_ACTIVO);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        updatedAdministradorConjunto.setUser(user);
        // Add required entity
        ConjuntoResidencial conjuntoResidencial;
        conjuntoResidencial = ConjuntoResidencialResourceIT.createUpdatedEntity();
        conjuntoResidencial.setId("fixed-id-for-tests");
        updatedAdministradorConjunto.setConjuntoResidencial(conjuntoResidencial);
        // Add required entity
        TipoDocumento tipoDocumento;
        tipoDocumento = TipoDocumentoResourceIT.createUpdatedEntity();
        tipoDocumento.setId("fixed-id-for-tests");
        updatedAdministradorConjunto.setTipoDocumento(tipoDocumento);
        return updatedAdministradorConjunto;
    }

    @BeforeEach
    void initTest() {
        administradorConjunto = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAdministradorConjunto != null) {
            administradorConjuntoRepository.delete(insertedAdministradorConjunto);
            insertedAdministradorConjunto = null;
        }
        userRepository.deleteAll();
    }

    @Test
    void createAdministradorConjunto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AdministradorConjunto
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);
        var returnedAdministradorConjuntoDTO = om.readValue(
            restAdministradorConjuntoMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(administradorConjuntoDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdministradorConjuntoDTO.class
        );

        // Validate the AdministradorConjunto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAdministradorConjunto = administradorConjuntoMapper.toEntity(returnedAdministradorConjuntoDTO);
        assertAdministradorConjuntoUpdatableFieldsEquals(
            returnedAdministradorConjunto,
            getPersistedAdministradorConjunto(returnedAdministradorConjunto)
        );

        insertedAdministradorConjunto = returnedAdministradorConjunto;
    }

    @Test
    void createAdministradorConjuntoWithExistingId() throws Exception {
        // Create the AdministradorConjunto with an existing ID
        administradorConjunto.setId("existing_id");
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdministradorConjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(administradorConjuntoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdministradorConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNombresIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        administradorConjunto.setNombres(null);

        // Create the AdministradorConjunto, which fails.
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        restAdministradorConjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(administradorConjuntoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkApellidosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        administradorConjunto.setApellidos(null);

        // Create the AdministradorConjunto, which fails.
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        restAdministradorConjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(administradorConjuntoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNumeroDocumentoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        administradorConjunto.setNumeroDocumento(null);

        // Create the AdministradorConjunto, which fails.
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        restAdministradorConjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(administradorConjuntoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCorreoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        administradorConjunto.setCorreo(null);

        // Create the AdministradorConjunto, which fails.
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        restAdministradorConjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(administradorConjuntoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkActivoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        administradorConjunto.setActivo(null);

        // Create the AdministradorConjunto, which fails.
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        restAdministradorConjuntoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(administradorConjuntoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllAdministradorConjuntos() throws Exception {
        // Initialize the database
        insertedAdministradorConjunto = administradorConjuntoRepository.save(administradorConjunto);

        // Get all the administradorConjuntoList
        restAdministradorConjuntoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administradorConjunto.getId())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAdministradorConjuntosWithEagerRelationshipsIsEnabled() throws Exception {
        when(administradorConjuntoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdministradorConjuntoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(administradorConjuntoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAdministradorConjuntosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(administradorConjuntoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdministradorConjuntoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(administradorConjuntoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getAdministradorConjunto() throws Exception {
        // Initialize the database
        insertedAdministradorConjunto = administradorConjuntoRepository.save(administradorConjunto);

        // Get the administradorConjunto
        restAdministradorConjuntoMockMvc
            .perform(get(ENTITY_API_URL_ID, administradorConjunto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(administradorConjunto.getId()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO));
    }

    @Test
    void getNonExistingAdministradorConjunto() throws Exception {
        // Get the administradorConjunto
        restAdministradorConjuntoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingAdministradorConjunto() throws Exception {
        // Initialize the database
        insertedAdministradorConjunto = administradorConjuntoRepository.save(administradorConjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the administradorConjunto
        AdministradorConjunto updatedAdministradorConjunto = administradorConjuntoRepository
            .findById(administradorConjunto.getId())
            .orElseThrow();
        updatedAdministradorConjunto
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .activo(UPDATED_ACTIVO);
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(updatedAdministradorConjunto);

        restAdministradorConjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, administradorConjuntoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(administradorConjuntoDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdministradorConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdministradorConjuntoToMatchAllProperties(updatedAdministradorConjunto);
    }

    @Test
    void putNonExistingAdministradorConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        administradorConjunto.setId(UUID.randomUUID().toString());

        // Create the AdministradorConjunto
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministradorConjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, administradorConjuntoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(administradorConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministradorConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAdministradorConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        administradorConjunto.setId(UUID.randomUUID().toString());

        // Create the AdministradorConjunto
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministradorConjuntoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(administradorConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministradorConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAdministradorConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        administradorConjunto.setId(UUID.randomUUID().toString());

        // Create the AdministradorConjunto
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministradorConjuntoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(administradorConjuntoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdministradorConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAdministradorConjuntoWithPatch() throws Exception {
        // Initialize the database
        insertedAdministradorConjunto = administradorConjuntoRepository.save(administradorConjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the administradorConjunto using partial update
        AdministradorConjunto partialUpdatedAdministradorConjunto = new AdministradorConjunto();
        partialUpdatedAdministradorConjunto.setId(administradorConjunto.getId());

        partialUpdatedAdministradorConjunto.nombres(UPDATED_NOMBRES).telefono(UPDATED_TELEFONO).correo(UPDATED_CORREO);

        restAdministradorConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdministradorConjunto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdministradorConjunto))
            )
            .andExpect(status().isOk());

        // Validate the AdministradorConjunto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdministradorConjuntoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdministradorConjunto, administradorConjunto),
            getPersistedAdministradorConjunto(administradorConjunto)
        );
    }

    @Test
    void fullUpdateAdministradorConjuntoWithPatch() throws Exception {
        // Initialize the database
        insertedAdministradorConjunto = administradorConjuntoRepository.save(administradorConjunto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the administradorConjunto using partial update
        AdministradorConjunto partialUpdatedAdministradorConjunto = new AdministradorConjunto();
        partialUpdatedAdministradorConjunto.setId(administradorConjunto.getId());

        partialUpdatedAdministradorConjunto
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .activo(UPDATED_ACTIVO);

        restAdministradorConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdministradorConjunto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdministradorConjunto))
            )
            .andExpect(status().isOk());

        // Validate the AdministradorConjunto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdministradorConjuntoUpdatableFieldsEquals(
            partialUpdatedAdministradorConjunto,
            getPersistedAdministradorConjunto(partialUpdatedAdministradorConjunto)
        );
    }

    @Test
    void patchNonExistingAdministradorConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        administradorConjunto.setId(UUID.randomUUID().toString());

        // Create the AdministradorConjunto
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministradorConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, administradorConjuntoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(administradorConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministradorConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAdministradorConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        administradorConjunto.setId(UUID.randomUUID().toString());

        // Create the AdministradorConjunto
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministradorConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(administradorConjuntoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdministradorConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAdministradorConjunto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        administradorConjunto.setId(UUID.randomUUID().toString());

        // Create the AdministradorConjunto
        AdministradorConjuntoDTO administradorConjuntoDTO = administradorConjuntoMapper.toDto(administradorConjunto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministradorConjuntoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(administradorConjuntoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdministradorConjunto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAdministradorConjunto() throws Exception {
        // Initialize the database
        insertedAdministradorConjunto = administradorConjuntoRepository.save(administradorConjunto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the administradorConjunto
        restAdministradorConjuntoMockMvc
            .perform(delete(ENTITY_API_URL_ID, administradorConjunto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return administradorConjuntoRepository.count();
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

    protected AdministradorConjunto getPersistedAdministradorConjunto(AdministradorConjunto administradorConjunto) {
        return administradorConjuntoRepository.findById(administradorConjunto.getId()).orElseThrow();
    }

    protected void assertPersistedAdministradorConjuntoToMatchAllProperties(AdministradorConjunto expectedAdministradorConjunto) {
        assertAdministradorConjuntoAllPropertiesEquals(
            expectedAdministradorConjunto,
            getPersistedAdministradorConjunto(expectedAdministradorConjunto)
        );
    }

    protected void assertPersistedAdministradorConjuntoToMatchUpdatableProperties(AdministradorConjunto expectedAdministradorConjunto) {
        assertAdministradorConjuntoAllUpdatablePropertiesEquals(
            expectedAdministradorConjunto,
            getPersistedAdministradorConjunto(expectedAdministradorConjunto)
        );
    }
}
