package com.edu.sena.zentry.web.rest;

import static com.edu.sena.zentry.domain.ReservasAsserts.*;
import static com.edu.sena.zentry.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.edu.sena.zentry.IntegrationTest;
import com.edu.sena.zentry.domain.Reservas;
import com.edu.sena.zentry.domain.ServicioConjunto;
import com.edu.sena.zentry.domain.Vinculado;
import com.edu.sena.zentry.domain.enumeration.Estado;
import com.edu.sena.zentry.repository.ReservasRepository;
import com.edu.sena.zentry.service.ReservasService;
import com.edu.sena.zentry.service.dto.ReservasDTO;
import com.edu.sena.zentry.service.mapper.ReservasMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
 * Integration tests for the {@link ReservasResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ReservasResourceIT {

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final LocalDate DEFAULT_FECHA_SOLICITUD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SOLICITUD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_RESERVA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_RESERVA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalTime DEFAULT_HORA_INICIO = LocalTime.NOON;
    private static final LocalTime UPDATED_HORA_INICIO = LocalTime.MAX.withNano(0);

    private static final LocalTime DEFAULT_HORAFIN = LocalTime.NOON;
    private static final LocalTime UPDATED_HORAFIN = LocalTime.MAX.withNano(0);

    private static final Integer DEFAULT_CUPOS_APARTADOS = 1;
    private static final Integer UPDATED_CUPOS_APARTADOS = 2;

    private static final Estado DEFAULT_ESTADO = Estado.APROBADO;
    private static final Estado UPDATED_ESTADO = Estado.RECHAZADO;

    private static final String ENTITY_API_URL = "/api/reservas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReservasRepository reservasRepository;

    @Mock
    private ReservasRepository reservasRepositoryMock;

    @Autowired
    private ReservasMapper reservasMapper;

    @Mock
    private ReservasService reservasServiceMock;

    @Autowired
    private MockMvc restReservasMockMvc;

    private Reservas reservas;

    private Reservas insertedReservas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservas createEntity() {
        Reservas reservas = new Reservas()
            .fechaSolicitud(DEFAULT_FECHA_SOLICITUD)
            .fechaReserva(DEFAULT_FECHA_RESERVA)
            .horaInicio(DEFAULT_HORA_INICIO)
            .horafin(DEFAULT_HORAFIN)
            .cuposApartados(DEFAULT_CUPOS_APARTADOS)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        ServicioConjunto servicioConjunto;
        servicioConjunto = ServicioConjuntoResourceIT.createEntity();
        servicioConjunto.setId("fixed-id-for-tests");
        reservas.setServicioConjunto(servicioConjunto);
        // Add required entity
        Vinculado vinculado;
        vinculado = VinculadoResourceIT.createEntity();
        vinculado.setId("fixed-id-for-tests");
        reservas.setVinculado(vinculado);
        return reservas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservas createUpdatedEntity() {
        Reservas updatedReservas = new Reservas()
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD)
            .fechaReserva(UPDATED_FECHA_RESERVA)
            .horaInicio(UPDATED_HORA_INICIO)
            .horafin(UPDATED_HORAFIN)
            .cuposApartados(UPDATED_CUPOS_APARTADOS)
            .estado(UPDATED_ESTADO);
        // Add required entity
        ServicioConjunto servicioConjunto;
        servicioConjunto = ServicioConjuntoResourceIT.createUpdatedEntity();
        servicioConjunto.setId("fixed-id-for-tests");
        updatedReservas.setServicioConjunto(servicioConjunto);
        // Add required entity
        Vinculado vinculado;
        vinculado = VinculadoResourceIT.createUpdatedEntity();
        vinculado.setId("fixed-id-for-tests");
        updatedReservas.setVinculado(vinculado);
        return updatedReservas;
    }

    @BeforeEach
    void initTest() {
        reservas = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedReservas != null) {
            reservasRepository.delete(insertedReservas);
            insertedReservas = null;
        }
    }

    @Test
    void createReservas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Reservas
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);
        var returnedReservasDTO = om.readValue(
            restReservasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservasDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ReservasDTO.class
        );

        // Validate the Reservas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedReservas = reservasMapper.toEntity(returnedReservasDTO);
        assertReservasUpdatableFieldsEquals(returnedReservas, getPersistedReservas(returnedReservas));

        insertedReservas = returnedReservas;
    }

    @Test
    void createReservasWithExistingId() throws Exception {
        // Create the Reservas with an existing ID
        reservas.setId("existing_id");
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reservas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkFechaSolicitudIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reservas.setFechaSolicitud(null);

        // Create the Reservas, which fails.
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        restReservasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkFechaReservaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reservas.setFechaReserva(null);

        // Create the Reservas, which fails.
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        restReservasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkHoraInicioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reservas.setHoraInicio(null);

        // Create the Reservas, which fails.
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        restReservasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkHorafinIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reservas.setHorafin(null);

        // Create the Reservas, which fails.
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        restReservasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reservas.setEstado(null);

        // Create the Reservas, which fails.
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        restReservasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllReservases() throws Exception {
        // Initialize the database
        insertedReservas = reservasRepository.save(reservas);

        // Get all the reservasList
        restReservasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservas.getId())))
            .andExpect(jsonPath("$.[*].fechaSolicitud").value(hasItem(DEFAULT_FECHA_SOLICITUD.toString())))
            .andExpect(jsonPath("$.[*].fechaReserva").value(hasItem(DEFAULT_FECHA_RESERVA.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].horafin").value(hasItem(DEFAULT_HORAFIN.format(LOCAL_DATE_TIME_FORMAT))))
            .andExpect(jsonPath("$.[*].cuposApartados").value(hasItem(DEFAULT_CUPOS_APARTADOS)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReservasesWithEagerRelationshipsIsEnabled() throws Exception {
        when(reservasServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReservasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(reservasServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReservasesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(reservasServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReservasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(reservasRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getReservas() throws Exception {
        // Initialize the database
        insertedReservas = reservasRepository.save(reservas);

        // Get the reservas
        restReservasMockMvc
            .perform(get(ENTITY_API_URL_ID, reservas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservas.getId()))
            .andExpect(jsonPath("$.fechaSolicitud").value(DEFAULT_FECHA_SOLICITUD.toString()))
            .andExpect(jsonPath("$.fechaReserva").value(DEFAULT_FECHA_RESERVA.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.horafin").value(DEFAULT_HORAFIN.format(LOCAL_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.cuposApartados").value(DEFAULT_CUPOS_APARTADOS))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingReservas() throws Exception {
        // Get the reservas
        restReservasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingReservas() throws Exception {
        // Initialize the database
        insertedReservas = reservasRepository.save(reservas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reservas
        Reservas updatedReservas = reservasRepository.findById(reservas.getId()).orElseThrow();
        updatedReservas
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD)
            .fechaReserva(UPDATED_FECHA_RESERVA)
            .horaInicio(UPDATED_HORA_INICIO)
            .horafin(UPDATED_HORAFIN)
            .cuposApartados(UPDATED_CUPOS_APARTADOS)
            .estado(UPDATED_ESTADO);
        ReservasDTO reservasDTO = reservasMapper.toDto(updatedReservas);

        restReservasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservasDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reservasDTO))
            )
            .andExpect(status().isOk());

        // Validate the Reservas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReservasToMatchAllProperties(updatedReservas);
    }

    @Test
    void putNonExistingReservas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservas.setId(UUID.randomUUID().toString());

        // Create the Reservas
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservasDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reservasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchReservas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservas.setId(UUID.randomUUID().toString());

        // Create the Reservas
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reservasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamReservas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservas.setId(UUID.randomUUID().toString());

        // Create the Reservas
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservasDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reservas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateReservasWithPatch() throws Exception {
        // Initialize the database
        insertedReservas = reservasRepository.save(reservas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reservas using partial update
        Reservas partialUpdatedReservas = new Reservas();
        partialUpdatedReservas.setId(reservas.getId());

        partialUpdatedReservas
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD)
            .horaInicio(UPDATED_HORA_INICIO)
            .cuposApartados(UPDATED_CUPOS_APARTADOS)
            .estado(UPDATED_ESTADO);

        restReservasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReservas))
            )
            .andExpect(status().isOk());

        // Validate the Reservas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReservasUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedReservas, reservas), getPersistedReservas(reservas));
    }

    @Test
    void fullUpdateReservasWithPatch() throws Exception {
        // Initialize the database
        insertedReservas = reservasRepository.save(reservas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reservas using partial update
        Reservas partialUpdatedReservas = new Reservas();
        partialUpdatedReservas.setId(reservas.getId());

        partialUpdatedReservas
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD)
            .fechaReserva(UPDATED_FECHA_RESERVA)
            .horaInicio(UPDATED_HORA_INICIO)
            .horafin(UPDATED_HORAFIN)
            .cuposApartados(UPDATED_CUPOS_APARTADOS)
            .estado(UPDATED_ESTADO);

        restReservasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReservas))
            )
            .andExpect(status().isOk());

        // Validate the Reservas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReservasUpdatableFieldsEquals(partialUpdatedReservas, getPersistedReservas(partialUpdatedReservas));
    }

    @Test
    void patchNonExistingReservas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservas.setId(UUID.randomUUID().toString());

        // Create the Reservas
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reservasDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reservasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchReservas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservas.setId(UUID.randomUUID().toString());

        // Create the Reservas
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reservasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamReservas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservas.setId(UUID.randomUUID().toString());

        // Create the Reservas
        ReservasDTO reservasDTO = reservasMapper.toDto(reservas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(reservasDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reservas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteReservas() throws Exception {
        // Initialize the database
        insertedReservas = reservasRepository.save(reservas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the reservas
        restReservasMockMvc
            .perform(delete(ENTITY_API_URL_ID, reservas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return reservasRepository.count();
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

    protected Reservas getPersistedReservas(Reservas reservas) {
        return reservasRepository.findById(reservas.getId()).orElseThrow();
    }

    protected void assertPersistedReservasToMatchAllProperties(Reservas expectedReservas) {
        assertReservasAllPropertiesEquals(expectedReservas, getPersistedReservas(expectedReservas));
    }

    protected void assertPersistedReservasToMatchUpdatableProperties(Reservas expectedReservas) {
        assertReservasAllUpdatablePropertiesEquals(expectedReservas, getPersistedReservas(expectedReservas));
    }
}
