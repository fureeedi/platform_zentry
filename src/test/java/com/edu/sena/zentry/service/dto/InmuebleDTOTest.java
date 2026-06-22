package com.edu.sena.zentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InmuebleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InmuebleDTO.class);
        InmuebleDTO inmuebleDTO1 = new InmuebleDTO();
        inmuebleDTO1.setId("id1");
        InmuebleDTO inmuebleDTO2 = new InmuebleDTO();
        assertThat(inmuebleDTO1).isNotEqualTo(inmuebleDTO2);
        inmuebleDTO2.setId(inmuebleDTO1.getId());
        assertThat(inmuebleDTO1).isEqualTo(inmuebleDTO2);
        inmuebleDTO2.setId("id2");
        assertThat(inmuebleDTO1).isNotEqualTo(inmuebleDTO2);
        inmuebleDTO1.setId(null);
        assertThat(inmuebleDTO1).isNotEqualTo(inmuebleDTO2);
    }
}
