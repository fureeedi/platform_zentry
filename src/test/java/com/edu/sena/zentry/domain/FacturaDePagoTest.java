package com.edu.sena.zentry.domain;

import static com.edu.sena.zentry.domain.ConjuntoResidencialTestSamples.*;
import static com.edu.sena.zentry.domain.FacturaDePagoTestSamples.*;
import static com.edu.sena.zentry.domain.VinculadoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FacturaDePagoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacturaDePago.class);
        FacturaDePago facturaDePago1 = getFacturaDePagoSample1();
        FacturaDePago facturaDePago2 = new FacturaDePago();
        assertThat(facturaDePago1).isNotEqualTo(facturaDePago2);

        facturaDePago2.setId(facturaDePago1.getId());
        assertThat(facturaDePago1).isEqualTo(facturaDePago2);

        facturaDePago2 = getFacturaDePagoSample2();
        assertThat(facturaDePago1).isNotEqualTo(facturaDePago2);
    }

    @Test
    void conjuntoResidencialTest() {
        FacturaDePago facturaDePago = getFacturaDePagoRandomSampleGenerator();
        ConjuntoResidencial conjuntoResidencialBack = getConjuntoResidencialRandomSampleGenerator();

        facturaDePago.setConjuntoResidencial(conjuntoResidencialBack);
        assertThat(facturaDePago.getConjuntoResidencial()).isEqualTo(conjuntoResidencialBack);

        facturaDePago.conjuntoResidencial(null);
        assertThat(facturaDePago.getConjuntoResidencial()).isNull();
    }

    @Test
    void vinculadoTest() {
        FacturaDePago facturaDePago = getFacturaDePagoRandomSampleGenerator();
        Vinculado vinculadoBack = getVinculadoRandomSampleGenerator();

        facturaDePago.setVinculado(vinculadoBack);
        assertThat(facturaDePago.getVinculado()).isEqualTo(vinculadoBack);

        facturaDePago.vinculado(null);
        assertThat(facturaDePago.getVinculado()).isNull();
    }
}
