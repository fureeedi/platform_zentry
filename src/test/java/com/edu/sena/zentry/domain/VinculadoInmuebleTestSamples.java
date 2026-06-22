package com.edu.sena.zentry.domain;

import java.util.UUID;

public class VinculadoInmuebleTestSamples {

    public static VinculadoInmueble getVinculadoInmuebleSample1() {
        return new VinculadoInmueble().id("id1");
    }

    public static VinculadoInmueble getVinculadoInmuebleSample2() {
        return new VinculadoInmueble().id("id2");
    }

    public static VinculadoInmueble getVinculadoInmuebleRandomSampleGenerator() {
        return new VinculadoInmueble().id(UUID.randomUUID().toString());
    }
}
