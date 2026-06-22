package com.edu.sena.zentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConjuntoResidencialDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConjuntoResidencialDTO.class);
        ConjuntoResidencialDTO conjuntoResidencialDTO1 = new ConjuntoResidencialDTO();
        conjuntoResidencialDTO1.setId("id1");
        ConjuntoResidencialDTO conjuntoResidencialDTO2 = new ConjuntoResidencialDTO();
        assertThat(conjuntoResidencialDTO1).isNotEqualTo(conjuntoResidencialDTO2);
        conjuntoResidencialDTO2.setId(conjuntoResidencialDTO1.getId());
        assertThat(conjuntoResidencialDTO1).isEqualTo(conjuntoResidencialDTO2);
        conjuntoResidencialDTO2.setId("id2");
        assertThat(conjuntoResidencialDTO1).isNotEqualTo(conjuntoResidencialDTO2);
        conjuntoResidencialDTO1.setId(null);
        assertThat(conjuntoResidencialDTO1).isNotEqualTo(conjuntoResidencialDTO2);
    }
}
