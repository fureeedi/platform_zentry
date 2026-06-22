package com.edu.sena.zentry.domain;

import static com.edu.sena.zentry.domain.AdministradorConjuntoTestSamples.*;
import static com.edu.sena.zentry.domain.AnunciosTestSamples.*;
import static com.edu.sena.zentry.domain.ConjuntoResidencialTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnunciosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anuncios.class);
        Anuncios anuncios1 = getAnunciosSample1();
        Anuncios anuncios2 = new Anuncios();
        assertThat(anuncios1).isNotEqualTo(anuncios2);

        anuncios2.setId(anuncios1.getId());
        assertThat(anuncios1).isEqualTo(anuncios2);

        anuncios2 = getAnunciosSample2();
        assertThat(anuncios1).isNotEqualTo(anuncios2);
    }

    @Test
    void conjuntoResidencialTest() {
        Anuncios anuncios = getAnunciosRandomSampleGenerator();
        ConjuntoResidencial conjuntoResidencialBack = getConjuntoResidencialRandomSampleGenerator();

        anuncios.setConjuntoResidencial(conjuntoResidencialBack);
        assertThat(anuncios.getConjuntoResidencial()).isEqualTo(conjuntoResidencialBack);

        anuncios.conjuntoResidencial(null);
        assertThat(anuncios.getConjuntoResidencial()).isNull();
    }

    @Test
    void administradorConjuntoTest() {
        Anuncios anuncios = getAnunciosRandomSampleGenerator();
        AdministradorConjunto administradorConjuntoBack = getAdministradorConjuntoRandomSampleGenerator();

        anuncios.setAdministradorConjunto(administradorConjuntoBack);
        assertThat(anuncios.getAdministradorConjunto()).isEqualTo(administradorConjuntoBack);

        anuncios.administradorConjunto(null);
        assertThat(anuncios.getAdministradorConjunto()).isNull();
    }
}
