package com.edu.sena.zentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservasDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservasDTO.class);
        ReservasDTO reservasDTO1 = new ReservasDTO();
        reservasDTO1.setId("id1");
        ReservasDTO reservasDTO2 = new ReservasDTO();
        assertThat(reservasDTO1).isNotEqualTo(reservasDTO2);
        reservasDTO2.setId(reservasDTO1.getId());
        assertThat(reservasDTO1).isEqualTo(reservasDTO2);
        reservasDTO2.setId("id2");
        assertThat(reservasDTO1).isNotEqualTo(reservasDTO2);
        reservasDTO1.setId(null);
        assertThat(reservasDTO1).isNotEqualTo(reservasDTO2);
    }
}
