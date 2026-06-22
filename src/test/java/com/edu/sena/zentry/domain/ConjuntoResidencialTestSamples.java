package com.edu.sena.zentry.domain;

import java.util.UUID;

public class ConjuntoResidencialTestSamples {

    public static ConjuntoResidencial getConjuntoResidencialSample1() {
        return new ConjuntoResidencial().id("id1").nombreConjunto("nombreConjunto1").direccionConjunto("direccionConjunto1");
    }

    public static ConjuntoResidencial getConjuntoResidencialSample2() {
        return new ConjuntoResidencial().id("id2").nombreConjunto("nombreConjunto2").direccionConjunto("direccionConjunto2");
    }

    public static ConjuntoResidencial getConjuntoResidencialRandomSampleGenerator() {
        return new ConjuntoResidencial()
            .id(UUID.randomUUID().toString())
            .nombreConjunto(UUID.randomUUID().toString())
            .direccionConjunto(UUID.randomUUID().toString());
    }
}
