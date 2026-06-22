package com.edu.sena.zentry.domain;

import static com.edu.sena.zentry.domain.ReservasTestSamples.*;
import static com.edu.sena.zentry.domain.ServicioConjuntoTestSamples.*;
import static com.edu.sena.zentry.domain.VinculadoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reservas.class);
        Reservas reservas1 = getReservasSample1();
        Reservas reservas2 = new Reservas();
        assertThat(reservas1).isNotEqualTo(reservas2);

        reservas2.setId(reservas1.getId());
        assertThat(reservas1).isEqualTo(reservas2);

        reservas2 = getReservasSample2();
        assertThat(reservas1).isNotEqualTo(reservas2);
    }

    @Test
    void servicioConjuntoTest() {
        Reservas reservas = getReservasRandomSampleGenerator();
        ServicioConjunto servicioConjuntoBack = getServicioConjuntoRandomSampleGenerator();

        reservas.setServicioConjunto(servicioConjuntoBack);
        assertThat(reservas.getServicioConjunto()).isEqualTo(servicioConjuntoBack);

        reservas.servicioConjunto(null);
        assertThat(reservas.getServicioConjunto()).isNull();
    }

    @Test
    void vinculadoTest() {
        Reservas reservas = getReservasRandomSampleGenerator();
        Vinculado vinculadoBack = getVinculadoRandomSampleGenerator();

        reservas.setVinculado(vinculadoBack);
        assertThat(reservas.getVinculado()).isEqualTo(vinculadoBack);

        reservas.vinculado(null);
        assertThat(reservas.getVinculado()).isNull();
    }
}
