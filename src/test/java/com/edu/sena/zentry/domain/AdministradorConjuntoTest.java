package com.edu.sena.zentry.domain;

import static com.edu.sena.zentry.domain.AdministradorConjuntoTestSamples.*;
import static com.edu.sena.zentry.domain.ConjuntoResidencialTestSamples.*;
import static com.edu.sena.zentry.domain.TipoDocumentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdministradorConjuntoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministradorConjunto.class);
        AdministradorConjunto administradorConjunto1 = getAdministradorConjuntoSample1();
        AdministradorConjunto administradorConjunto2 = new AdministradorConjunto();
        assertThat(administradorConjunto1).isNotEqualTo(administradorConjunto2);

        administradorConjunto2.setId(administradorConjunto1.getId());
        assertThat(administradorConjunto1).isEqualTo(administradorConjunto2);

        administradorConjunto2 = getAdministradorConjuntoSample2();
        assertThat(administradorConjunto1).isNotEqualTo(administradorConjunto2);
    }

    @Test
    void conjuntoResidencialTest() {
        AdministradorConjunto administradorConjunto = getAdministradorConjuntoRandomSampleGenerator();
        ConjuntoResidencial conjuntoResidencialBack = getConjuntoResidencialRandomSampleGenerator();

        administradorConjunto.setConjuntoResidencial(conjuntoResidencialBack);
        assertThat(administradorConjunto.getConjuntoResidencial()).isEqualTo(conjuntoResidencialBack);

        administradorConjunto.conjuntoResidencial(null);
        assertThat(administradorConjunto.getConjuntoResidencial()).isNull();
    }

    @Test
    void tipoDocumentoTest() {
        AdministradorConjunto administradorConjunto = getAdministradorConjuntoRandomSampleGenerator();
        TipoDocumento tipoDocumentoBack = getTipoDocumentoRandomSampleGenerator();

        administradorConjunto.setTipoDocumento(tipoDocumentoBack);
        assertThat(administradorConjunto.getTipoDocumento()).isEqualTo(tipoDocumentoBack);

        administradorConjunto.tipoDocumento(null);
        assertThat(administradorConjunto.getTipoDocumento()).isNull();
    }
}
