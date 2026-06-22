package com.edu.sena.zentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VinculadoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VinculadoDTO.class);
        VinculadoDTO vinculadoDTO1 = new VinculadoDTO();
        vinculadoDTO1.setId("id1");
        VinculadoDTO vinculadoDTO2 = new VinculadoDTO();
        assertThat(vinculadoDTO1).isNotEqualTo(vinculadoDTO2);
        vinculadoDTO2.setId(vinculadoDTO1.getId());
        assertThat(vinculadoDTO1).isEqualTo(vinculadoDTO2);
        vinculadoDTO2.setId("id2");
        assertThat(vinculadoDTO1).isNotEqualTo(vinculadoDTO2);
        vinculadoDTO1.setId(null);
        assertThat(vinculadoDTO1).isNotEqualTo(vinculadoDTO2);
    }
}
