package com.edu.sena.zentry.web.rest;

import static com.edu.sena.zentry.domain.VinculadoInmuebleAsserts.*;
import static com.edu.sena.zentry.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.domain.Inmueble;
import com.edu.sena.zentry.domain.Vinculado;
import com.edu.sena.zentry.domain.VinculadoInmueble;
import com.edu.sena.zentry.domain.enumeration.TipoVinculo;
import com.edu.sena.zentry.repository.VinculadoInmuebleRepository;
import com.edu.sena.zentry.service.VinculadoInmuebleService;
import com.edu.sena.zentry.service.dto.VinculadoInmuebleDTO;
import com.edu.sena.zentry.service.mapper.VinculadoInmuebleMapper;
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
 * Integration tests for the {@link VinculadoInmuebleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VinculadoInmuebleResourceIT {

    private static final TipoVinculo DEFAULT_TIPO_VINCULO = TipoVinculo.PROPIETARIO;
    private static final TipoVinculo UPDATED_TIPO_VINCULO = TipoVinculo.ARRENDATARIO;

    private static final String ENTITY_API_URL = "/api/vinculado-inmuebles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VinculadoInmuebleRepository vinculadoInmuebleRepository;

    @Mock
    private VinculadoInmuebleRepository vinculadoInmuebleRepositoryMock;

    @Autowired
    private VinculadoInmuebleMapper vinculadoInmuebleMapper;

    @Mock
    private VinculadoInmuebleService vinculadoInmuebleServiceMock;

    @Autowired
    private MockMvc restVinculadoInmuebleMockMvc;

    private VinculadoInmueble vinculadoInmueble;

    private VinculadoInmueble insertedVinculadoInmueble;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VinculadoInmueble createEntity() {
        VinculadoInmueble vinculadoInmueble = new VinculadoInmueble().tipoVinculo(DEFAULT_TIPO_VINCULO);
        // Add required entity
        Vinculado vinculado;
        vinculado = VinculadoResourceIT.createEntity();
        vinculado.setId("fixed-id-for-tests");
        vinculadoInmueble.setVinculado(vinculado);
        // Add required entity
        Inmueble inmueble;
        inmueble = InmuebleResourceIT.createEntity();
        inmueble.setId("fixed-id-for-tests");
        vinculadoInmueble.setInmueble(inmueble);
        return vinculadoInmueble;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VinculadoInmueble createUpdatedEntity() {
        VinculadoInmueble updatedVinculadoInmueble = new VinculadoInmueble().tipoVinculo(UPDATED_TIPO_VINCULO);
        // Add required entity
        Vinculado vinculado;
        vinculado = VinculadoResourceIT.createUpdatedEntity();
        vinculado.setId("fixed-id-for-tests");
        updatedVinculadoInmueble.setVinculado(vinculado);
        // Add required entity
        Inmueble inmueble;
        inmueble = InmuebleResourceIT.createUpdatedEntity();
        inmueble.setId("fixed-id-for-tests");
        updatedVinculadoInmueble.setInmueble(inmueble);
        return updatedVinculadoInmueble;
    }

    @BeforeEach
    void initTest() {
        vinculadoInmueble = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedVinculadoInmueble != null) {
            vinculadoInmuebleRepository.delete(insertedVinculadoInmueble);
            insertedVinculadoInmueble = null;
        }
    }

    @Test
    void createVinculadoInmueble() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the VinculadoInmueble
        VinculadoInmuebleDTO vinculadoInmuebleDTO = vinculadoInmuebleMapper.toDto(vinculadoInmueble);
        var returnedVinculadoInmuebleDTO = om.readValue(
            restVinculadoInmuebleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoInmuebleDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VinculadoInmuebleDTO.class
        );

        // Validate the VinculadoInmueble in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVinculadoInmueble = vinculadoInmuebleMapper.toEntity(returnedVinculadoInmuebleDTO);
        assertVinculadoInmuebleUpdatableFieldsEquals(returnedVinculadoInmueble, getPersistedVinculadoInmueble(returnedVinculadoInmueble));

        insertedVinculadoInmueble = returnedVinculadoInmueble;
    }

    @Test
    void createVinculadoInmuebleWithExistingId() throws Exception {
        // Create the VinculadoInmueble with an existing ID
        vinculadoInmueble.setId("existing_id");
        VinculadoInmuebleDTO vinculadoInmuebleDTO = vinculadoInmuebleMapper.toDto(vinculadoInmueble);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVinculadoInmuebleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoInmuebleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VinculadoInmueble in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTipoVinculoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vinculadoInmueble.setTipoVinculo(null);

        // Create the VinculadoInmueble, which fails.
        VinculadoInmuebleDTO vinculadoInmuebleDTO = vinculadoInmuebleMapper.toDto(vinculadoInmueble);

        restVinculadoInmuebleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoInmuebleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllVinculadoInmuebles() throws Exception {
        // Initialize the database
        insertedVinculadoInmueble = vinculadoInmuebleRepository.save(vinculadoInmueble);

        // Get all the vinculadoInmuebleList
        restVinculadoInmuebleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vinculadoInmueble.getId())))
            .andExpect(jsonPath("$.[*].tipoVinculo").value(hasItem(DEFAULT_TIPO_VINCULO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVinculadoInmueblesWithEagerRelationshipsIsEnabled() throws Exception {
        when(vinculadoInmuebleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVinculadoInmuebleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vinculadoInmuebleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVinculadoInmueblesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(vinculadoInmuebleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVinculadoInmuebleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(vinculadoInmuebleRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getVinculadoInmueble() throws Exception {
        // Initialize the database
        insertedVinculadoInmueble = vinculadoInmuebleRepository.save(vinculadoInmueble);

        // Get the vinculadoInmueble
        restVinculadoInmuebleMockMvc
            .perform(get(ENTITY_API_URL_ID, vinculadoInmueble.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vinculadoInmueble.getId()))
            .andExpect(jsonPath("$.tipoVinculo").value(DEFAULT_TIPO_VINCULO.toString()));
    }

    @Test
    void getNonExistingVinculadoInmueble() throws Exception {
        // Get the vinculadoInmueble
        restVinculadoInmuebleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingVinculadoInmueble() throws Exception {
        // Initialize the database
        insertedVinculadoInmueble = vinculadoInmuebleRepository.save(vinculadoInmueble);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vinculadoInmueble
        VinculadoInmueble updatedVinculadoInmueble = vinculadoInmuebleRepository.findById(vinculadoInmueble.getId()).orElseThrow();
        updatedVinculadoInmueble.tipoVinculo(UPDATED_TIPO_VINCULO);
        VinculadoInmuebleDTO vinculadoInmuebleDTO = vinculadoInmuebleMapper.toDto(updatedVinculadoInmueble);

        restVinculadoInmuebleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vinculadoInmuebleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vinculadoInmuebleDTO))
            )
            .andExpect(status().isOk());

        // Validate the VinculadoInmueble in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVinculadoInmuebleToMatchAllProperties(updatedVinculadoInmueble);
    }

    @Test
    void putNonExistingVinculadoInmueble() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculadoInmueble.setId(UUID.randomUUID().toString());

        // Create the VinculadoInmueble
        VinculadoInmuebleDTO vinculadoInmuebleDTO = vinculadoInmuebleMapper.toDto(vinculadoInmueble);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVinculadoInmuebleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vinculadoInmuebleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vinculadoInmuebleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VinculadoInmueble in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchVinculadoInmueble() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculadoInmueble.setId(UUID.randomUUID().toString());

        // Create the VinculadoInmueble
        VinculadoInmuebleDTO vinculadoInmuebleDTO = vinculadoInmuebleMapper.toDto(vinculadoInmueble);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVinculadoInmuebleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vinculadoInmuebleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VinculadoInmueble in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamVinculadoInmueble() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculadoInmueble.setId(UUID.randomUUID().toString());

        // Create the VinculadoInmueble
        VinculadoInmuebleDTO vinculadoInmuebleDTO = vinculadoInmuebleMapper.toDto(vinculadoInmueble);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVinculadoInmuebleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vinculadoInmuebleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VinculadoInmueble in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateVinculadoInmuebleWithPatch() throws Exception {
        // Initialize the database
        insertedVinculadoInmueble = vinculadoInmuebleRepository.save(vinculadoInmueble);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vinculadoInmueble using partial update
        VinculadoInmueble partialUpdatedVinculadoInmueble = new VinculadoInmueble();
        partialUpdatedVinculadoInmueble.setId(vinculadoInmueble.getId());

        partialUpdatedVinculadoInmueble.tipoVinculo(UPDATED_TIPO_VINCULO);

        restVinculadoInmuebleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVinculadoInmueble.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVinculadoInmueble))
            )
            .andExpect(status().isOk());

        // Validate the VinculadoInmueble in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVinculadoInmuebleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVinculadoInmueble, vinculadoInmueble),
            getPersistedVinculadoInmueble(vinculadoInmueble)
        );
    }

    @Test
    void fullUpdateVinculadoInmuebleWithPatch() throws Exception {
        // Initialize the database
        insertedVinculadoInmueble = vinculadoInmuebleRepository.save(vinculadoInmueble);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vinculadoInmueble using partial update
        VinculadoInmueble partialUpdatedVinculadoInmueble = new VinculadoInmueble();
        partialUpdatedVinculadoInmueble.setId(vinculadoInmueble.getId());

        partialUpdatedVinculadoInmueble.tipoVinculo(UPDATED_TIPO_VINCULO);

        restVinculadoInmuebleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVinculadoInmueble.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVinculadoInmueble))
            )
            .andExpect(status().isOk());

        // Validate the VinculadoInmueble in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVinculadoInmuebleUpdatableFieldsEquals(
            partialUpdatedVinculadoInmueble,
            getPersistedVinculadoInmueble(partialUpdatedVinculadoInmueble)
        );
    }

    @Test
    void patchNonExistingVinculadoInmueble() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculadoInmueble.setId(UUID.randomUUID().toString());

        // Create the VinculadoInmueble
        VinculadoInmuebleDTO vinculadoInmuebleDTO = vinculadoInmuebleMapper.toDto(vinculadoInmueble);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVinculadoInmuebleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vinculadoInmuebleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vinculadoInmuebleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VinculadoInmueble in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchVinculadoInmueble() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculadoInmueble.setId(UUID.randomUUID().toString());

        // Create the VinculadoInmueble
        VinculadoInmuebleDTO vinculadoInmuebleDTO = vinculadoInmuebleMapper.toDto(vinculadoInmueble);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVinculadoInmuebleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vinculadoInmuebleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VinculadoInmueble in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamVinculadoInmueble() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vinculadoInmueble.setId(UUID.randomUUID().toString());

        // Create the VinculadoInmueble
        VinculadoInmuebleDTO vinculadoInmuebleDTO = vinculadoInmuebleMapper.toDto(vinculadoInmueble);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVinculadoInmuebleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vinculadoInmuebleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VinculadoInmueble in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteVinculadoInmueble() throws Exception {
        // Initialize the database
        insertedVinculadoInmueble = vinculadoInmuebleRepository.save(vinculadoInmueble);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vinculadoInmueble
        restVinculadoInmuebleMockMvc
            .perform(delete(ENTITY_API_URL_ID, vinculadoInmueble.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vinculadoInmuebleRepository.count();
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

    protected VinculadoInmueble getPersistedVinculadoInmueble(VinculadoInmueble vinculadoInmueble) {
        return vinculadoInmuebleRepository.findById(vinculadoInmueble.getId()).orElseThrow();
    }

    protected void assertPersistedVinculadoInmuebleToMatchAllProperties(VinculadoInmueble expectedVinculadoInmueble) {
        assertVinculadoInmuebleAllPropertiesEquals(expectedVinculadoInmueble, getPersistedVinculadoInmueble(expectedVinculadoInmueble));
    }

    protected void assertPersistedVinculadoInmuebleToMatchUpdatableProperties(VinculadoInmueble expectedVinculadoInmueble) {
        assertVinculadoInmuebleAllUpdatablePropertiesEquals(
            expectedVinculadoInmueble,
            getPersistedVinculadoInmueble(expectedVinculadoInmueble)
        );
    }
}
