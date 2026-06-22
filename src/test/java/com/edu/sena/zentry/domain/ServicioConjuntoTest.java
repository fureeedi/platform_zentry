package com.edu.sena.zentry.domain;

import static com.edu.sena.zentry.domain.ConjuntoResidencialTestSamples.*;
import static com.edu.sena.zentry.domain.ServicioConjuntoTestSamples.*;
import static com.edu.sena.zentry.domain.ServicioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServicioConjuntoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicioConjunto.class);
        ServicioConjunto servicioConjunto1 = getServicioConjuntoSample1();
        ServicioConjunto servicioConjunto2 = new ServicioConjunto();
        assertThat(servicioConjunto1).isNotEqualTo(servicioConjunto2);

        servicioConjunto2.setId(servicioConjunto1.getId());
        assertThat(servicioConjunto1).isEqualTo(servicioConjunto2);

        servicioConjunto2 = getServicioConjuntoSample2();
        assertThat(servicioConjunto1).isNotEqualTo(servicioConjunto2);
    }

    @Test
    void conjuntoResidencialTest() {
        ServicioConjunto servicioConjunto = getServicioConjuntoRandomSampleGenerator();
        ConjuntoResidencial conjuntoResidencialBack = getConjuntoResidencialRandomSampleGenerator();

        servicioConjunto.setConjuntoResidencial(conjuntoResidencialBack);
        assertThat(servicioConjunto.getConjuntoResidencial()).isEqualTo(conjuntoResidencialBack);

        servicioConjunto.conjuntoResidencial(null);
        assertThat(servicioConjunto.getConjuntoResidencial()).isNull();
    }

    @Test
    void servicioTest() {
        ServicioConjunto servicioConjunto = getServicioConjuntoRandomSampleGenerator();
        Servicio servicioBack = getServicioRandomSampleGenerator();

        servicioConjunto.setServicio(servicioBack);
        assertThat(servicioConjunto.getServicio()).isEqualTo(servicioBack);

        servicioConjunto.servicio(null);
        assertThat(servicioConjunto.getServicio()).isNull();
    }
}
