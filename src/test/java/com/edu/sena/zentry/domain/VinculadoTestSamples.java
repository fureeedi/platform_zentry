package com.edu.sena.zentry.domain;

import java.util.UUID;

public class VinculadoTestSamples {

    public static Vinculado getVinculadoSample1() {
        return new Vinculado()
            .id("id1")
            .nombres("nombres1")
            .apellidos("apellidos1")
            .numeroDocumento("numeroDocumento1")
            .telefono("telefono1")
            .correo("correo1");
    }

    public static Vinculado getVinculadoSample2() {
        return new Vinculado()
            .id("id2")
            .nombres("nombres2")
            .apellidos("apellidos2")
            .numeroDocumento("numeroDocumento2")
            .telefono("telefono2")
            .correo("correo2");
    }

    public static Vinculado getVinculadoRandomSampleGenerator() {
        return new Vinculado()
            .id(UUID.randomUUID().toString())
            .nombres(UUID.randomUUID().toString())
            .apellidos(UUID.randomUUID().toString())
            .numeroDocumento(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .correo(UUID.randomUUID().toString());
    }
}
