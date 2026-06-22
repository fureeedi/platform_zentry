package com.edu.sena.zentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServicioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicioDTO.class);
        ServicioDTO servicioDTO1 = new ServicioDTO();
        servicioDTO1.setId("id1");
        ServicioDTO servicioDTO2 = new ServicioDTO();
        assertThat(servicioDTO1).isNotEqualTo(servicioDTO2);
        servicioDTO2.setId(servicioDTO1.getId());
        assertThat(servicioDTO1).isEqualTo(servicioDTO2);
        servicioDTO2.setId("id2");
        assertThat(servicioDTO1).isNotEqualTo(servicioDTO2);
        servicioDTO1.setId(null);
        assertThat(servicioDTO1).isNotEqualTo(servicioDTO2);
    }
}
