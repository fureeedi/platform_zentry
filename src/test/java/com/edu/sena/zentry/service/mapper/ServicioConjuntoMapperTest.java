package com.edu.sena.zentry.service.mapper;

import static com.edu.sena.zentry.domain.ServicioConjuntoAsserts.*;
import static com.edu.sena.zentry.domain.ServicioConjuntoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServicioConjuntoMapperTest {

    private ServicioConjuntoMapper servicioConjuntoMapper;

    @BeforeEach
    void setUp() {
        servicioConjuntoMapper = new ServicioConjuntoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServicioConjuntoSample1();
        var actual = servicioConjuntoMapper.toEntity(servicioConjuntoMapper.toDto(expected));
        assertServicioConjuntoAllPropertiesEquals(expected, actual);
    }
}
