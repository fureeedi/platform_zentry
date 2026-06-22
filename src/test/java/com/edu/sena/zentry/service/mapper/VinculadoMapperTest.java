package com.edu.sena.zentry.service.mapper;

import static com.edu.sena.zentry.domain.VinculadoAsserts.*;
import static com.edu.sena.zentry.domain.VinculadoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VinculadoMapperTest {

    private VinculadoMapper vinculadoMapper;

    @BeforeEach
    void setUp() {
        vinculadoMapper = new VinculadoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVinculadoSample1();
        var actual = vinculadoMapper.toEntity(vinculadoMapper.toDto(expected));
        assertVinculadoAllPropertiesEquals(expected, actual);
    }
}
