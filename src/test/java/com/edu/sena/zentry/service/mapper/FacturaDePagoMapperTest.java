package com.edu.sena.zentry.service.mapper;

import static com.edu.sena.zentry.domain.FacturaDePagoAsserts.*;
import static com.edu.sena.zentry.domain.FacturaDePagoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FacturaDePagoMapperTest {

    private FacturaDePagoMapper facturaDePagoMapper;

    @BeforeEach
    void setUp() {
        facturaDePagoMapper = new FacturaDePagoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFacturaDePagoSample1();
        var actual = facturaDePagoMapper.toEntity(facturaDePagoMapper.toDto(expected));
        assertFacturaDePagoAllPropertiesEquals(expected, actual);
    }
}
