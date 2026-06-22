package com.edu.sena.zentry.domain;

import static com.edu.sena.zentry.domain.ConjuntoResidencialTestSamples.*;
import static com.edu.sena.zentry.domain.InmuebleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InmuebleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inmueble.class);
        Inmueble inmueble1 = getInmuebleSample1();
        Inmueble inmueble2 = new Inmueble();
        assertThat(inmueble1).isNotEqualTo(inmueble2);

        inmueble2.setId(inmueble1.getId());
        assertThat(inmueble1).isEqualTo(inmueble2);

        inmueble2 = getInmuebleSample2();
        assertThat(inmueble1).isNotEqualTo(inmueble2);
    }

    @Test
    void conjuntoResidencialTest() {
        Inmueble inmueble = getInmuebleRandomSampleGenerator();
        ConjuntoResidencial conjuntoResidencialBack = getConjuntoResidencialRandomSampleGenerator();

        inmueble.setConjuntoResidencial(conjuntoResidencialBack);
        assertThat(inmueble.getConjuntoResidencial()).isEqualTo(conjuntoResidencialBack);

        inmueble.conjuntoResidencial(null);
        assertThat(inmueble.getConjuntoResidencial()).isNull();
    }
}
