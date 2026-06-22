package com.edu.sena.zentry.web.rest;

import static com.edu.sena.zentry.domain.VinculadoAsserts.*;
import static com.edu.sena.zentry.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.domain.AdministradorConjunto;
import com.edu.sena.zentry.domain.TipoDocumento;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.domain.Vinculado;
import com.edu.sena.zentry.repository.UserRepository;
import com.edu.sena.zentry.repository.VinculadoRepository;
import com.edu.sena.zentry.service.VinculadoService;
import com.edu.sena.zentry.service.dto.VinculadoDTO;
import com.edu.sena.zentry.service.mapper.VinculadoMapper;
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
 * Integration tests for the {@link VinculadoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VinculadoResourceIT {

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

    private static final String ENTITY_API_URL = "/api/vinculados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VinculadoRepository vinculadoRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private VinculadoRepository vinculadoRepositoryMock;

    @Autowired
    private VinculadoMapper vinculadoMapper;

    @Mock
    private VinculadoService vinculadoServiceMock;

    @Autowired
    private MockMvc restVinculadoMockMvc;

    private Vinculado vinculado;

    private Vinculado insertedVinculado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vinculado createEntity() {
        Vinculado vinculado = new Vinculado()
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .activo(DEFAULT_ACTIVO);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        vinculado.setUser(user);
        // Add required entity
        TipoDocumento tipoDocumento;
        tipoDocumento = TipoDocumentoResourceIT.createEntity();
        tipoDocumento.setId("fixed-id-for-tests");
        vinculado.setTipoDocumento(tipoDocumento);
        // Add required entity
        AdministradorConjunto administradorConjunto;
        administradorConjunto = AdministradorConjuntoResourceIT.createEntity();
        administradorConjunto.setId("fixed-id-for-tests");
        vinculado.setAdministradorConjunto(administradorConjunto);
        return vinculado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vinculado createUpdatedEntity() {
        Vinculado updatedVinculado = new Vinculado()
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .activo(UPDATED_ACTIVO);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        updatedVinculado.setUser(user);
        // Add required entity
        TipoDocumento tipoDocumento;
        tipoDocumento = TipoDocumentoResourceIT.createUpdatedEntity();
        tipoDocumento.setId("fixed-id-for-tests");
        updatedVinculado.setTipoDocumento(tipoDocumento);
        // Add required entity
        AdministradorConjunto administradorConjunto;
        administradorConjunto = AdministradorConjuntoResourceIT.createUpdatedEntity();
        administradorConjunto.setId("fixed-id-for-tests");
        updatedVinculado.setAdministradorConjunto(administradorConjunto);
        return updatedVinculado;
    }

    @BeforeEach
    void initTest() {
        vinculado = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedVinculado != null) {
            vinculadoRepository.delete(insertedVinculado);
            insertedVinculado = null;
        }
        userRepository.deleteAll();
    }

    @Test
    void createVinculado() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vinculado
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);
        var returnedVinculadoDTO = om.readValue(
            restVinculadoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VinculadoDTO.class
        );

        // Validate the Vinculado in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVinculado = vinculadoMapper.toEntity(returnedVinculadoDTO);
        assertVinculadoUpdatableFieldsEquals(returnedVinculado, getPersistedVinculado(returnedVinculado));

        insertedVinculado = returnedVinculado;
    }

    @Test
    void createVinculadoWithExistingId() throws Exception {
        // Create the Vinculado with an existing ID
        vinculado.setId("existing_id");
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVinculadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vinculado in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNombresIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vinculado.setNombres(null);

        // Create the Vinculado, which fails.
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        restVinculadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkApellidosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vinculado.setApellidos(null);

        // Create the Vinculado, which fails.
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        restVinculadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNumeroDocumentoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vinculado.setNumeroDocumento(null);

        // Create the Vinculado, which fails.
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        restVinculadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCorreoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vinculado.setCorreo(null);

        // Create the Vinculado, which fails.
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        restVinculadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkActivoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vinculado.setActivo(null);

        // Create the Vinculado, which fails.
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        restVinculadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllVinculados() throws Exception {
        // Initialize the database
        insertedVinculado = vinculadoRepository.save(vinculado);

        // Get all the vinculadoList
        restVinculadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vinculado.getId())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVinculadosWithEagerRelationshipsIsEnabled() throws Exception {
        when(vinculadoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVinculadoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vinculadoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVinculadosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(vinculadoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVinculadoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(vinculadoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getVinculado() throws Exception {
        // Initialize the database
        insertedVinculado = vinculadoRepository.save(vinculado);

        // Get the vinculado
        restVinculadoMockMvc
            .perform(get(ENTITY_API_URL_ID, vinculado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vinculado.getId()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO));
    }

    @Test
    void getNonExistingVinculado() throws Exception {
        // Get the vinculado
        restVinculadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingVinculado() throws Exception {
        // Initialize the database
        insertedVinculado = vinculadoRepository.save(vinculado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vinculado
        Vinculado updatedVinculado = vinculadoRepository.findById(vinculado.getId()).orElseThrow();
        updatedVinculado
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .activo(UPDATED_ACTIVO);
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(updatedVinculado);

        restVinculadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vinculadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vinculadoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Vinculado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVinculadoToMatchAllProperties(updatedVinculado);
    }

    @Test
    void putNonExistingVinculado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculado.setId(UUID.randomUUID().toString());

        // Create the Vinculado
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVinculadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vinculadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vinculadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vinculado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchVinculado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculado.setId(UUID.randomUUID().toString());

        // Create the Vinculado
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVinculadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vinculadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vinculado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamVinculado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculado.setId(UUID.randomUUID().toString());

        // Create the Vinculado
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVinculadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vinculado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateVinculadoWithPatch() throws Exception {
        // Initialize the database
        insertedVinculado = vinculadoRepository.save(vinculado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vinculado using partial update
        Vinculado partialUpdatedVinculado = new Vinculado();
        partialUpdatedVinculado.setId(vinculado.getId());

        partialUpdatedVinculado.nombres(UPDATED_NOMBRES).apellidos(UPDATED_APELLIDOS).correo(UPDATED_CORREO);

        restVinculadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVinculado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVinculado))
            )
            .andExpect(status().isOk());

        // Validate the Vinculado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVinculadoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVinculado, vinculado),
            getPersistedVinculado(vinculado)
        );
    }

    @Test
    void fullUpdateVinculadoWithPatch() throws Exception {
        // Initialize the database
        insertedVinculado = vinculadoRepository.save(vinculado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vinculado using partial update
        Vinculado partialUpdatedVinculado = new Vinculado();
        partialUpdatedVinculado.setId(vinculado.getId());

        partialUpdatedVinculado
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .activo(UPDATED_ACTIVO);

        restVinculadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVinculado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVinculado))
            )
            .andExpect(status().isOk());

        // Validate the Vinculado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVinculadoUpdatableFieldsEquals(partialUpdatedVinculado, getPersistedVinculado(partialUpdatedVinculado));
    }

    @Test
    void patchNonExistingVinculado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculado.setId(UUID.randomUUID().toString());

        // Create the Vinculado
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVinculadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vinculadoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vinculadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vinculado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchVinculado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculado.setId(UUID.randomUUID().toString());

        // Create the Vinculado
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVinculadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vinculadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vinculado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamVinculado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculado.setId(UUID.randomUUID().toString());

        // Create the Vinculado
        VinculadoDTO vinculadoDTO = vinculadoMapper.toDto(vinculado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVinculadoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vinculadoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vinculado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteVinculado() throws Exception {
        // Initialize the database
        insertedVinculado = vinculadoRepository.save(vinculado);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vinculado
        restVinculadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, vinculado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vinculadoRepository.count();
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

    protected Vinculado getPersistedVinculado(Vinculado vinculado) {
        return vinculadoRepository.findById(vinculado.getId()).orElseThrow();
    }

    protected void assertPersistedVinculadoToMatchAllProperties(Vinculado expectedVinculado) {
        assertVinculadoAllPropertiesEquals(expectedVinculado, getPersistedVinculado(expectedVinculado));
    }

    protected void assertPersistedVinculadoToMatchUpdatableProperties(Vinculado expectedVinculado) {
        assertVinculadoAllUpdatablePropertiesEquals(expectedVinculado, getPersistedVinculado(expectedVinculado));
    }
}
