package com.edu.sena.zentry.web.rest;

import static com.edu.sena.zentry.domain.FacturaDePagoAsserts.*;
import static com.edu.sena.zentry.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.domain.ConjuntoResidencial;
import com.edu.sena.zentry.domain.FacturaDePago;
import com.edu.sena.zentry.domain.Vinculado;
import com.edu.sena.zentry.repository.FacturaDePagoRepository;
import com.edu.sena.zentry.service.FacturaDePagoService;
import com.edu.sena.zentry.service.dto.FacturaDePagoDTO;
import com.edu.sena.zentry.service.mapper.FacturaDePagoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FacturaDePagoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FacturaDePagoResourceIT {

    private static final LocalDate DEFAULT_FECHA_ENVIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENVIO = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_IMAGEN_FACTURA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_FACTURA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_FACTURA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_FACTURA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/factura-de-pagos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FacturaDePagoRepository facturaDePagoRepository;

    @Mock
    private FacturaDePagoRepository facturaDePagoRepositoryMock;

    @Autowired
    private FacturaDePagoMapper facturaDePagoMapper;

    @Mock
    private FacturaDePagoService facturaDePagoServiceMock;

    @Autowired
    private MockMvc restFacturaDePagoMockMvc;

    private FacturaDePago facturaDePago;

    private FacturaDePago insertedFacturaDePago;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacturaDePago createEntity() {
        FacturaDePago facturaDePago = new FacturaDePago()
            .fechaEnvio(DEFAULT_FECHA_ENVIO)
            .imagenFactura(DEFAULT_IMAGEN_FACTURA)
            .imagenFacturaContentType(DEFAULT_IMAGEN_FACTURA_CONTENT_TYPE);
        // Add required entity
        ConjuntoResidencial conjuntoResidencial;
        conjuntoResidencial = ConjuntoResidencialResourceIT.createEntity();
        conjuntoResidencial.setId("fixed-id-for-tests");
        facturaDePago.setConjuntoResidencial(conjuntoResidencial);
        // Add required entity
        Vinculado vinculado;
        vinculado = VinculadoResourceIT.createEntity();
        vinculado.setId("fixed-id-for-tests");
        facturaDePago.setVinculado(vinculado);
        return facturaDePago;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacturaDePago createUpdatedEntity() {
        FacturaDePago updatedFacturaDePago = new FacturaDePago()
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .imagenFactura(UPDATED_IMAGEN_FACTURA)
            .imagenFacturaContentType(UPDATED_IMAGEN_FACTURA_CONTENT_TYPE);
        // Add required entity
        ConjuntoResidencial conjuntoResidencial;
        conjuntoResidencial = ConjuntoResidencialResourceIT.createUpdatedEntity();
        conjuntoResidencial.setId("fixed-id-for-tests");
        updatedFacturaDePago.setConjuntoResidencial(conjuntoResidencial);
        // Add required entity
        Vinculado vinculado;
        vinculado = VinculadoResourceIT.createUpdatedEntity();
        vinculado.setId("fixed-id-for-tests");
        updatedFacturaDePago.setVinculado(vinculado);
        return updatedFacturaDePago;
    }

    @BeforeEach
    void initTest() {
        facturaDePago = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFacturaDePago != null) {
            facturaDePagoRepository.delete(insertedFacturaDePago);
            insertedFacturaDePago = null;
        }
    }

    @Test
    void createFacturaDePago() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FacturaDePago
        FacturaDePagoDTO facturaDePagoDTO = facturaDePagoMapper.toDto(facturaDePago);
        var returnedFacturaDePagoDTO = om.readValue(
            restFacturaDePagoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(facturaDePagoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FacturaDePagoDTO.class
        );

        // Validate the FacturaDePago in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFacturaDePago = facturaDePagoMapper.toEntity(returnedFacturaDePagoDTO);
        assertFacturaDePagoUpdatableFieldsEquals(returnedFacturaDePago, getPersistedFacturaDePago(returnedFacturaDePago));

        insertedFacturaDePago = returnedFacturaDePago;
    }

    @Test
    void createFacturaDePagoWithExistingId() throws Exception {
        // Create the FacturaDePago with an existing ID
        facturaDePago.setId("existing_id");
        FacturaDePagoDTO facturaDePagoDTO = facturaDePagoMapper.toDto(facturaDePago);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacturaDePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(facturaDePagoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FacturaDePago in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaEnvioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        facturaDePago.setFechaEnvio(null);

        // Create the FacturaDePago, which fails.
        FacturaDePagoDTO facturaDePagoDTO = facturaDePagoMapper.toDto(facturaDePago);

        restFacturaDePagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(facturaDePagoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllFacturaDePagos() throws Exception {
        // Initialize the database
        insertedFacturaDePago = facturaDePagoRepository.save(facturaDePago);

        // Get all the facturaDePagoList
        restFacturaDePagoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facturaDePago.getId())))
            .andExpect(jsonPath("$.[*].fechaEnvio").value(hasItem(DEFAULT_FECHA_ENVIO.toString())))
            .andExpect(jsonPath("$.[*].imagenFacturaContentType").value(hasItem(DEFAULT_IMAGEN_FACTURA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenFactura").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_FACTURA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFacturaDePagosWithEagerRelationshipsIsEnabled() throws Exception {
        when(facturaDePagoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFacturaDePagoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(facturaDePagoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFacturaDePagosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(facturaDePagoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFacturaDePagoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(facturaDePagoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getFacturaDePago() throws Exception {
        // Initialize the database
        insertedFacturaDePago = facturaDePagoRepository.save(facturaDePago);

        // Get the facturaDePago
        restFacturaDePagoMockMvc
            .perform(get(ENTITY_API_URL_ID, facturaDePago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facturaDePago.getId()))
            .andExpect(jsonPath("$.fechaEnvio").value(DEFAULT_FECHA_ENVIO.toString()))
            .andExpect(jsonPath("$.imagenFacturaContentType").value(DEFAULT_IMAGEN_FACTURA_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenFactura").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_FACTURA)));
    }

    @Test
    void getNonExistingFacturaDePago() throws Exception {
        // Get the facturaDePago
        restFacturaDePagoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFacturaDePago() throws Exception {
        // Initialize the database
        insertedFacturaDePago = facturaDePagoRepository.save(facturaDePago);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the facturaDePago
        FacturaDePago updatedFacturaDePago = facturaDePagoRepository.findById(facturaDePago.getId()).orElseThrow();
        updatedFacturaDePago
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .imagenFactura(UPDATED_IMAGEN_FACTURA)
            .imagenFacturaContentType(UPDATED_IMAGEN_FACTURA_CONTENT_TYPE);
        FacturaDePagoDTO facturaDePagoDTO = facturaDePagoMapper.toDto(updatedFacturaDePago);

        restFacturaDePagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facturaDePagoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(facturaDePagoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FacturaDePago in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFacturaDePagoToMatchAllProperties(updatedFacturaDePago);
    }

    @Test
    void putNonExistingFacturaDePago() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        facturaDePago.setId(UUID.randomUUID().toString());

        // Create the FacturaDePago
        FacturaDePagoDTO facturaDePagoDTO = facturaDePagoMapper.toDto(facturaDePago);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacturaDePagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facturaDePagoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(facturaDePagoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FacturaDePago in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFacturaDePago() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        facturaDePago.setId(UUID.randomUUID().toString());

        // Create the FacturaDePago
        FacturaDePagoDTO facturaDePagoDTO = facturaDePagoMapper.toDto(facturaDePago);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacturaDePagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(facturaDePagoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FacturaDePago in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFacturaDePago() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        facturaDePago.setId(UUID.randomUUID().toString());

        // Create the FacturaDePago
        FacturaDePagoDTO facturaDePagoDTO = facturaDePagoMapper.toDto(facturaDePago);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacturaDePagoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(facturaDePagoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FacturaDePago in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFacturaDePagoWithPatch() throws Exception {
        // Initialize the database
        insertedFacturaDePago = facturaDePagoRepository.save(facturaDePago);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the facturaDePago using partial update
        FacturaDePago partialUpdatedFacturaDePago = new FacturaDePago();
        partialUpdatedFacturaDePago.setId(facturaDePago.getId());

        restFacturaDePagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacturaDePago.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFacturaDePago))
            )
            .andExpect(status().isOk());

        // Validate the FacturaDePago in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFacturaDePagoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFacturaDePago, facturaDePago),
            getPersistedFacturaDePago(facturaDePago)
        );
    }

    @Test
    void fullUpdateFacturaDePagoWithPatch() throws Exception {
        // Initialize the database
        insertedFacturaDePago = facturaDePagoRepository.save(facturaDePago);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the facturaDePago using partial update
        FacturaDePago partialUpdatedFacturaDePago = new FacturaDePago();
        partialUpdatedFacturaDePago.setId(facturaDePago.getId());

        partialUpdatedFacturaDePago
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .imagenFactura(UPDATED_IMAGEN_FACTURA)
            .imagenFacturaContentType(UPDATED_IMAGEN_FACTURA_CONTENT_TYPE);

        restFacturaDePagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacturaDePago.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFacturaDePago))
            )
            .andExpect(status().isOk());

        // Validate the FacturaDePago in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFacturaDePagoUpdatableFieldsEquals(partialUpdatedFacturaDePago, getPersistedFacturaDePago(partialUpdatedFacturaDePago));
    }

    @Test
    void patchNonExistingFacturaDePago() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        facturaDePago.setId(UUID.randomUUID().toString());

        // Create the FacturaDePago
        FacturaDePagoDTO facturaDePagoDTO = facturaDePagoMapper.toDto(facturaDePago);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacturaDePagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, facturaDePagoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(facturaDePagoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FacturaDePago in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFacturaDePago() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        facturaDePago.setId(UUID.randomUUID().toString());

        // Create the FacturaDePago
        FacturaDePagoDTO facturaDePagoDTO = facturaDePagoMapper.toDto(facturaDePago);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacturaDePagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(facturaDePagoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FacturaDePago in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFacturaDePago() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        facturaDePago.setId(UUID.randomUUID().toString());

        // Create the FacturaDePago
        FacturaDePagoDTO facturaDePagoDTO = facturaDePagoMapper.toDto(facturaDePago);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacturaDePagoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(facturaDePagoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FacturaDePago in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFacturaDePago() throws Exception {
        // Initialize the database
        insertedFacturaDePago = facturaDePagoRepository.save(facturaDePago);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the facturaDePago
        restFacturaDePagoMockMvc
            .perform(delete(ENTITY_API_URL_ID, facturaDePago.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return facturaDePagoRepository.count();
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

    protected FacturaDePago getPersistedFacturaDePago(FacturaDePago facturaDePago) {
        return facturaDePagoRepository.findById(facturaDePago.getId()).orElseThrow();
    }

    protected void assertPersistedFacturaDePagoToMatchAllProperties(FacturaDePago expectedFacturaDePago) {
        assertFacturaDePagoAllPropertiesEquals(expectedFacturaDePago, getPersistedFacturaDePago(expectedFacturaDePago));
    }

    protected void assertPersistedFacturaDePagoToMatchUpdatableProperties(FacturaDePago expectedFacturaDePago) {
        assertFacturaDePagoAllUpdatablePropertiesEquals(expectedFacturaDePago, getPersistedFacturaDePago(expectedFacturaDePago));
    }
}
