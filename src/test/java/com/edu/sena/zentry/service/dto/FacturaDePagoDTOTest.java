package com.edu.sena.zentry.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.edu.sena.zentry.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FacturaDePagoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacturaDePagoDTO.class);
        FacturaDePagoDTO facturaDePagoDTO1 = new FacturaDePagoDTO();
        facturaDePagoDTO1.setId("id1");
        FacturaDePagoDTO facturaDePagoDTO2 = new FacturaDePagoDTO();
        assertThat(facturaDePagoDTO1).isNotEqualTo(facturaDePagoDTO2);
        facturaDePagoDTO2.setId(facturaDePagoDTO1.getId());
        assertThat(facturaDePagoDTO1).isEqualTo(facturaDePagoDTO2);
        facturaDePagoDTO2.setId("id2");
        assertThat(facturaDePagoDTO1).isNotEqualTo(facturaDePagoDTO2);
        facturaDePagoDTO1.setId(null);
        assertThat(facturaDePagoDTO1).isNotEqualTo(facturaDePagoDTO2);
    }
}
