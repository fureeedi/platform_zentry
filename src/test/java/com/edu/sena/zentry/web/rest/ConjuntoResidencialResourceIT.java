package com.edu.sena.zentry.web.rest;

import static com.edu.sena.zentry.domain.ConjuntoResidencialAsserts.*;
import static com.edu.sena.zentry.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.repository.ConjuntoResidencialRepository;
import com.edu.sena.zentry.service.dto.ConjuntoResidencialDTO;
import com.edu.sena.zentry.service.mapper.ConjuntoResidencialMapper;
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
 * Integration tests for the {@link ConjuntoResidencialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConjuntoResidencialResourceIT {

    private static final String DEFAULT_NOMBRE_CONJUNTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CONJUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_CONJUNTO = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_CONJUNTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/conjunto-residencials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ConjuntoResidencialRepository conjuntoResidencialRepository;

    @Autowired
    private ConjuntoResidencialMapper conjuntoResidencialMapper;

    @Autowired
    private MockMvc restConjuntoResidencialMockMvc;

    private ConjuntoResidencial conjuntoResidencial;

    private ConjuntoResidencial insertedConjuntoResidencial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConjuntoResidencial createEntity() {
        return new ConjuntoResidencial().nombreConjunto(DEFAULT_NOMBRE_CONJUNTO).direccionConjunto(DEFAULT_DIRECCION_CONJUNTO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConjuntoResidencial createUpdatedEntity() {
        return new ConjuntoResidencial().nombreConjunto(UPDATED_NOMBRE_CONJUNTO).direccionConjunto(UPDATED_DIRECCION_CONJUNTO);
    }

    @BeforeEach
    void initTest() {
        conjuntoResidencial = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedConjuntoResidencial != null) {
            conjuntoResidencialRepository.delete(insertedConjuntoResidencial);
            insertedConjuntoResidencial = null;
        }
    }

    @Test
    void createConjuntoResidencial() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ConjuntoResidencial
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(conjuntoResidencial);
        var returnedConjuntoResidencialDTO = om.readValue(
            restConjuntoResidencialMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(conjuntoResidencialDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ConjuntoResidencialDTO.class
        );

        // Validate the ConjuntoResidencial in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedConjuntoResidencial = conjuntoResidencialMapper.toEntity(returnedConjuntoResidencialDTO);
        assertConjuntoResidencialUpdatableFieldsEquals(
            returnedConjuntoResidencial,
            getPersistedConjuntoResidencial(returnedConjuntoResidencial)
        );

        insertedConjuntoResidencial = returnedConjuntoResidencial;
    }

    @Test
    void createConjuntoResidencialWithExistingId() throws Exception {
        // Create the ConjuntoResidencial with an existing ID
        conjuntoResidencial.setId("existing_id");
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(conjuntoResidencial);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConjuntoResidencialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(conjuntoResidencialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConjuntoResidencial in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNombreConjuntoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        conjuntoResidencial.setNombreConjunto(null);

        // Create the ConjuntoResidencial, which fails.
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(conjuntoResidencial);

        restConjuntoResidencialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(conjuntoResidencialDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDireccionConjuntoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        conjuntoResidencial.setDireccionConjunto(null);

        // Create the ConjuntoResidencial, which fails.
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(conjuntoResidencial);

        restConjuntoResidencialMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(conjuntoResidencialDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllConjuntoResidencials() throws Exception {
        // Initialize the database
        insertedConjuntoResidencial = conjuntoResidencialRepository.save(conjuntoResidencial);

        // Get all the conjuntoResidencialList
        restConjuntoResidencialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conjuntoResidencial.getId())))
            .andExpect(jsonPath("$.[*].nombreConjunto").value(hasItem(DEFAULT_NOMBRE_CONJUNTO)))
            .andExpect(jsonPath("$.[*].direccionConjunto").value(hasItem(DEFAULT_DIRECCION_CONJUNTO)));
    }

    @Test
    void getConjuntoResidencial() throws Exception {
        // Initialize the database
        insertedConjuntoResidencial = conjuntoResidencialRepository.save(conjuntoResidencial);

        // Get the conjuntoResidencial
        restConjuntoResidencialMockMvc
            .perform(get(ENTITY_API_URL_ID, conjuntoResidencial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conjuntoResidencial.getId()))
            .andExpect(jsonPath("$.nombreConjunto").value(DEFAULT_NOMBRE_CONJUNTO))
            .andExpect(jsonPath("$.direccionConjunto").value(DEFAULT_DIRECCION_CONJUNTO));
    }

    @Test
    void getNonExistingConjuntoResidencial() throws Exception {
        // Get the conjuntoResidencial
        restConjuntoResidencialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingConjuntoResidencial() throws Exception {
        // Initialize the database
        insertedConjuntoResidencial = conjuntoResidencialRepository.save(conjuntoResidencial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the conjuntoResidencial
        ConjuntoResidencial updatedConjuntoResidencial = conjuntoResidencialRepository.findById(conjuntoResidencial.getId()).orElseThrow();
        updatedConjuntoResidencial.nombreConjunto(UPDATED_NOMBRE_CONJUNTO).direccionConjunto(UPDATED_DIRECCION_CONJUNTO);
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(updatedConjuntoResidencial);

        restConjuntoResidencialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conjuntoResidencialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(conjuntoResidencialDTO))
            )
            .andExpect(status().isOk());

        // Validate the ConjuntoResidencial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedConjuntoResidencialToMatchAllProperties(updatedConjuntoResidencial);
    }

    @Test
    void putNonExistingConjuntoResidencial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conjuntoResidencial.setId(UUID.randomUUID().toString());

        // Create the ConjuntoResidencial
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(conjuntoResidencial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConjuntoResidencialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conjuntoResidencialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(conjuntoResidencialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConjuntoResidencial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchConjuntoResidencial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conjuntoResidencial.setId(UUID.randomUUID().toString());

        // Create the ConjuntoResidencial
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(conjuntoResidencial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConjuntoResidencialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(conjuntoResidencialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConjuntoResidencial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamConjuntoResidencial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conjuntoResidencial.setId(UUID.randomUUID().toString());

        // Create the ConjuntoResidencial
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(conjuntoResidencial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConjuntoResidencialMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(conjuntoResidencialDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConjuntoResidencial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateConjuntoResidencialWithPatch() throws Exception {
        // Initialize the database
        insertedConjuntoResidencial = conjuntoResidencialRepository.save(conjuntoResidencial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the conjuntoResidencial using partial update
        ConjuntoResidencial partialUpdatedConjuntoResidencial = new ConjuntoResidencial();
        partialUpdatedConjuntoResidencial.setId(conjuntoResidencial.getId());

        partialUpdatedConjuntoResidencial.nombreConjunto(UPDATED_NOMBRE_CONJUNTO);

        restConjuntoResidencialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConjuntoResidencial.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConjuntoResidencial))
            )
            .andExpect(status().isOk());

        // Validate the ConjuntoResidencial in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConjuntoResidencialUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedConjuntoResidencial, conjuntoResidencial),
            getPersistedConjuntoResidencial(conjuntoResidencial)
        );
    }

    @Test
    void fullUpdateConjuntoResidencialWithPatch() throws Exception {
        // Initialize the database
        insertedConjuntoResidencial = conjuntoResidencialRepository.save(conjuntoResidencial);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the conjuntoResidencial using partial update
        ConjuntoResidencial partialUpdatedConjuntoResidencial = new ConjuntoResidencial();
        partialUpdatedConjuntoResidencial.setId(conjuntoResidencial.getId());

        partialUpdatedConjuntoResidencial.nombreConjunto(UPDATED_NOMBRE_CONJUNTO).direccionConjunto(UPDATED_DIRECCION_CONJUNTO);

        restConjuntoResidencialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConjuntoResidencial.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConjuntoResidencial))
            )
            .andExpect(status().isOk());

        // Validate the ConjuntoResidencial in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConjuntoResidencialUpdatableFieldsEquals(
            partialUpdatedConjuntoResidencial,
            getPersistedConjuntoResidencial(partialUpdatedConjuntoResidencial)
        );
    }

    @Test
    void patchNonExistingConjuntoResidencial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conjuntoResidencial.setId(UUID.randomUUID().toString());

        // Create the ConjuntoResidencial
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(conjuntoResidencial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConjuntoResidencialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conjuntoResidencialDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(conjuntoResidencialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConjuntoResidencial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchConjuntoResidencial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conjuntoResidencial.setId(UUID.randomUUID().toString());

        // Create the ConjuntoResidencial
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(conjuntoResidencial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConjuntoResidencialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(conjuntoResidencialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConjuntoResidencial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamConjuntoResidencial() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        conjuntoResidencial.setId(UUID.randomUUID().toString());

        // Create the ConjuntoResidencial
        ConjuntoResidencialDTO conjuntoResidencialDTO = conjuntoResidencialMapper.toDto(conjuntoResidencial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConjuntoResidencialMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(conjuntoResidencialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConjuntoResidencial in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteConjuntoResidencial() throws Exception {
        // Initialize the database
        insertedConjuntoResidencial = conjuntoResidencialRepository.save(conjuntoResidencial);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the conjuntoResidencial
        restConjuntoResidencialMockMvc
            .perform(delete(ENTITY_API_URL_ID, conjuntoResidencial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return conjuntoResidencialRepository.count();
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

    protected ConjuntoResidencial getPersistedConjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        return conjuntoResidencialRepository.findById(conjuntoResidencial.getId()).orElseThrow();
    }

    protected void assertPersistedConjuntoResidencialToMatchAllProperties(ConjuntoResidencial expectedConjuntoResidencial) {
        assertConjuntoResidencialAllPropertiesEquals(
            expectedConjuntoResidencial,
            getPersistedConjuntoResidencial(expectedConjuntoResidencial)
        );
    }

    protected void assertPersistedConjuntoResidencialToMatchUpdatableProperties(ConjuntoResidencial expectedConjuntoResidencial) {
        assertConjuntoResidencialAllUpdatablePropertiesEquals(
            expectedConjuntoResidencial,
            getPersistedConjuntoResidencial(expectedConjuntoResidencial)
        );
    }
}
