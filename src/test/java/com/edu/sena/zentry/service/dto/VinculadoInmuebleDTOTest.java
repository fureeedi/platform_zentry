package com.edu.sena.zentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VinculadoInmuebleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VinculadoInmuebleDTO.class);
        VinculadoInmuebleDTO vinculadoInmuebleDTO1 = new VinculadoInmuebleDTO();
        vinculadoInmuebleDTO1.setId("id1");
        VinculadoInmuebleDTO vinculadoInmuebleDTO2 = new VinculadoInmuebleDTO();
        assertThat(vinculadoInmuebleDTO1).isNotEqualTo(vinculadoInmuebleDTO2);
        vinculadoInmuebleDTO2.setId(vinculadoInmuebleDTO1.getId());
        assertThat(vinculadoInmuebleDTO1).isEqualTo(vinculadoInmuebleDTO2);
        vinculadoInmuebleDTO2.setId("id2");
        assertThat(vinculadoInmuebleDTO1).isNotEqualTo(vinculadoInmuebleDTO2);
        vinculadoInmuebleDTO1.setId(null);
        assertThat(vinculadoInmuebleDTO1).isNotEqualTo(vinculadoInmuebleDTO2);
    }
}
