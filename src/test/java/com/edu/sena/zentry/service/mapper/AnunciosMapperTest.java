package com.edu.sena.zentry.service.mapper;

import static com.edu.sena.zentry.domain.AnunciosAsserts.*;
import static com.edu.sena.zentry.domain.AnunciosTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnunciosMapperTest {

    private AnunciosMapper anunciosMapper;

    @BeforeEach
    void setUp() {
        anunciosMapper = new AnunciosMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAnunciosSample1();
        var actual = anunciosMapper.toEntity(anunciosMapper.toDto(expected));
        assertAnunciosAllPropertiesEquals(expected, actual);
    }
}
