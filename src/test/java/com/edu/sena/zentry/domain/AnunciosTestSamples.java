package com.edu.sena.zentry.domain;

import java.util.UUID;

public class AnunciosTestSamples {

    public static Anuncios getAnunciosSample1() {
        return new Anuncios().id("id1").titulo("titulo1").descripcion("descripcion1");
    }

    public static Anuncios getAnunciosSample2() {
        return new Anuncios().id("id2").titulo("titulo2").descripcion("descripcion2");
    }

    public static Anuncios getAnunciosRandomSampleGenerator() {
        return new Anuncios()
            .id(UUID.randomUUID().toString())
            .titulo(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
