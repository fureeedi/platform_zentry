package com.edu.sena.zentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServicioConjuntoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicioConjuntoDTO.class);
        ServicioConjuntoDTO servicioConjuntoDTO1 = new ServicioConjuntoDTO();
        servicioConjuntoDTO1.setId("id1");
        ServicioConjuntoDTO servicioConjuntoDTO2 = new ServicioConjuntoDTO();
        assertThat(servicioConjuntoDTO1).isNotEqualTo(servicioConjuntoDTO2);
        servicioConjuntoDTO2.setId(servicioConjuntoDTO1.getId());
        assertThat(servicioConjuntoDTO1).isEqualTo(servicioConjuntoDTO2);
        servicioConjuntoDTO2.setId("id2");
        assertThat(servicioConjuntoDTO1).isNotEqualTo(servicioConjuntoDTO2);
        servicioConjuntoDTO1.setId(null);
        assertThat(servicioConjuntoDTO1).isNotEqualTo(servicioConjuntoDTO2);
    }
}
