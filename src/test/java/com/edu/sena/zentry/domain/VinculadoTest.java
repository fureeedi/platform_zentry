package com.edu.sena.zentry.domain;

import static com.edu.sena.zentry.domain.AdministradorConjuntoTestSamples.*;
import static com.edu.sena.zentry.domain.TipoDocumentoTestSamples.*;
import static com.edu.sena.zentry.domain.VinculadoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VinculadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vinculado.class);
        Vinculado vinculado1 = getVinculadoSample1();
        Vinculado vinculado2 = new Vinculado();
        assertThat(vinculado1).isNotEqualTo(vinculado2);

        vinculado2.setId(vinculado1.getId());
        assertThat(vinculado1).isEqualTo(vinculado2);

        vinculado2 = getVinculadoSample2();
        assertThat(vinculado1).isNotEqualTo(vinculado2);
    }

    @Test
    void tipoDocumentoTest() {
        Vinculado vinculado = getVinculadoRandomSampleGenerator();
        TipoDocumento tipoDocumentoBack = getTipoDocumentoRandomSampleGenerator();

        vinculado.setTipoDocumento(tipoDocumentoBack);
        assertThat(vinculado.getTipoDocumento()).isEqualTo(tipoDocumentoBack);

        vinculado.tipoDocumento(null);
        assertThat(vinculado.getTipoDocumento()).isNull();
    }

    @Test
    void administradorConjuntoTest() {
        Vinculado vinculado = getVinculadoRandomSampleGenerator();
        AdministradorConjunto administradorConjuntoBack = getAdministradorConjuntoRandomSampleGenerator();

        vinculado.setAdministradorConjunto(administradorConjuntoBack);
        assertThat(vinculado.getAdministradorConjunto()).isEqualTo(administradorConjuntoBack);

        vinculado.administradorConjunto(null);
        assertThat(vinculado.getAdministradorConjunto()).isNull();
    }
}
