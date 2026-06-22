package com.edu.sena.zentry.service.mapper;

import static com.edu.sena.zentry.domain.ReservasAsserts.*;
import static com.edu.sena.zentry.domain.ReservasTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservasMapperTest {

    private ReservasMapper reservasMapper;

    @BeforeEach
    void setUp() {
        reservasMapper = new ReservasMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getReservasSample1();
        var actual = reservasMapper.toEntity(reservasMapper.toDto(expected));
        assertReservasAllPropertiesEquals(expected, actual);
    }
}
