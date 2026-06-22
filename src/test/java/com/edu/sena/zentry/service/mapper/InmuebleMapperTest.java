package com.edu.sena.zentry.service.mapper;

import static com.edu.sena.zentry.domain.InmuebleAsserts.*;
import static com.edu.sena.zentry.domain.InmuebleTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InmuebleMapperTest {

    private InmuebleMapper inmuebleMapper;

    @BeforeEach
    void setUp() {
        inmuebleMapper = new InmuebleMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInmuebleSample1();
        var actual = inmuebleMapper.toEntity(inmuebleMapper.toDto(expected));
        assertInmuebleAllPropertiesEquals(expected, actual);
    }
}
