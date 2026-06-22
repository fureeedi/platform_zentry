package com.edu.sena.zentry.service.mapper;

import static com.edu.sena.zentry.domain.VinculadoInmuebleAsserts.*;
import static com.edu.sena.zentry.domain.VinculadoInmuebleTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VinculadoInmuebleMapperTest {

    private VinculadoInmuebleMapper vinculadoInmuebleMapper;

    @BeforeEach
    void setUp() {
        vinculadoInmuebleMapper = new VinculadoInmuebleMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVinculadoInmuebleSample1();
        var actual = vinculadoInmuebleMapper.toEntity(vinculadoInmuebleMapper.toDto(expected));
        assertVinculadoInmuebleAllPropertiesEquals(expected, actual);
    }
}
