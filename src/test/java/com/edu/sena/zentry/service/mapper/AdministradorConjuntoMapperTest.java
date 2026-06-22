package com.edu.sena.zentry.service.mapper;

import static com.edu.sena.zentry.domain.AdministradorConjuntoAsserts.*;
import static com.edu.sena.zentry.domain.AdministradorConjuntoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdministradorConjuntoMapperTest {

    private AdministradorConjuntoMapper administradorConjuntoMapper;

    @BeforeEach
    void setUp() {
        administradorConjuntoMapper = new AdministradorConjuntoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdministradorConjuntoSample1();
        var actual = administradorConjuntoMapper.toEntity(administradorConjuntoMapper.toDto(expected));
        assertAdministradorConjuntoAllPropertiesEquals(expected, actual);
    }
}
