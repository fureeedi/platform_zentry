package com.edu.sena.zentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdministradorConjuntoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministradorConjuntoDTO.class);
        AdministradorConjuntoDTO administradorConjuntoDTO1 = new AdministradorConjuntoDTO();
        administradorConjuntoDTO1.setId("id1");
        AdministradorConjuntoDTO administradorConjuntoDTO2 = new AdministradorConjuntoDTO();
        assertThat(administradorConjuntoDTO1).isNotEqualTo(administradorConjuntoDTO2);
        administradorConjuntoDTO2.setId(administradorConjuntoDTO1.getId());
        assertThat(administradorConjuntoDTO1).isEqualTo(administradorConjuntoDTO2);
        administradorConjuntoDTO2.setId("id2");
        assertThat(administradorConjuntoDTO1).isNotEqualTo(administradorConjuntoDTO2);
        administradorConjuntoDTO1.setId(null);
        assertThat(administradorConjuntoDTO1).isNotEqualTo(administradorConjuntoDTO2);
    }
}
