package com.edu.sena.zentry.service.mapper;

import static com.edu.sena.zentry.domain.ConjuntoResidencialAsserts.*;
import static com.edu.sena.zentry.domain.ConjuntoResidencialTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConjuntoResidencialMapperTest {

    private ConjuntoResidencialMapper conjuntoResidencialMapper;

    @BeforeEach
    void setUp() {
        conjuntoResidencialMapper = new ConjuntoResidencialMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getConjuntoResidencialSample1();
        var actual = conjuntoResidencialMapper.toEntity(conjuntoResidencialMapper.toDto(expected));
        assertConjuntoResidencialAllPropertiesEquals(expected, actual);
    }
}
