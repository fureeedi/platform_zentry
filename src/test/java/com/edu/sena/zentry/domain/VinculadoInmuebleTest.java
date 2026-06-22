package com.edu.sena.zentry.domain;

import static com.edu.sena.zentry.domain.InmuebleTestSamples.*;
import static com.edu.sena.zentry.domain.VinculadoInmuebleTestSamples.*;
import static com.edu.sena.zentry.domain.VinculadoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VinculadoInmuebleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VinculadoInmueble.class);
        VinculadoInmueble vinculadoInmueble1 = getVinculadoInmuebleSample1();
        VinculadoInmueble vinculadoInmueble2 = new VinculadoInmueble();
        assertThat(vinculadoInmueble1).isNotEqualTo(vinculadoInmueble2);

        vinculadoInmueble2.setId(vinculadoInmueble1.getId());
        assertThat(vinculadoInmueble1).isEqualTo(vinculadoInmueble2);

        vinculadoInmueble2 = getVinculadoInmuebleSample2();
        assertThat(vinculadoInmueble1).isNotEqualTo(vinculadoInmueble2);
    }

    @Test
    void vinculadoTest() {
        VinculadoInmueble vinculadoInmueble = getVinculadoInmuebleRandomSampleGenerator();
        Vinculado vinculadoBack = getVinculadoRandomSampleGenerator();

        vinculadoInmueble.setVinculado(vinculadoBack);
        assertThat(vinculadoInmueble.getVinculado()).isEqualTo(vinculadoBack);

        vinculadoInmueble.vinculado(null);
        assertThat(vinculadoInmueble.getVinculado()).isNull();
    }

    @Test
    void inmuebleTest() {
        VinculadoInmueble vinculadoInmueble = getVinculadoInmuebleRandomSampleGenerator();
        Inmueble inmuebleBack = getInmuebleRandomSampleGenerator();

        vinculadoInmueble.setInmueble(inmuebleBack);
        assertThat(vinculadoInmueble.getInmueble()).isEqualTo(inmuebleBack);

        vinculadoInmueble.inmueble(null);
        assertThat(vinculadoInmueble.getInmueble()).isNull();
    }
}
