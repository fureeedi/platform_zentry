package com.edu.sena.zentry.domain;

import static com.edu.sena.zentry.domain.ConjuntoResidencialTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConjuntoResidencialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConjuntoResidencial.class);
        ConjuntoResidencial conjuntoResidencial1 = getConjuntoResidencialSample1();
        ConjuntoResidencial conjuntoResidencial2 = new ConjuntoResidencial();
        assertThat(conjuntoResidencial1).isNotEqualTo(conjuntoResidencial2);

        conjuntoResidencial2.setId(conjuntoResidencial1.getId());
        assertThat(conjuntoResidencial1).isEqualTo(conjuntoResidencial2);

        conjuntoResidencial2 = getConjuntoResidencialSample2();
        assertThat(conjuntoResidencial1).isNotEqualTo(conjuntoResidencial2);
    }
}
