package com.edu.sena.zentry.domain;

import java.util.UUID;

public class AdministradorConjuntoTestSamples {

    public static AdministradorConjunto getAdministradorConjuntoSample1() {
        return new AdministradorConjunto()
            .id("id1")
            .nombres("nombres1")
            .apellidos("apellidos1")
            .numeroDocumento("numeroDocumento1")
            .telefono("telefono1")
            .correo("correo1");
    }

    public static AdministradorConjunto getAdministradorConjuntoSample2() {
        return new AdministradorConjunto()
            .id("id2")
            .nombres("nombres2")
            .apellidos("apellidos2")
            .numeroDocumento("numeroDocumento2")
            .telefono("telefono2")
            .correo("correo2");
    }

    public static AdministradorConjunto getAdministradorConjuntoRandomSampleGenerator() {
        return new AdministradorConjunto()
            .id(UUID.randomUUID().toString())
            .nombres(UUID.randomUUID().toString())
            .apellidos(UUID.randomUUID().toString())
            .numeroDocumento(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .correo(UUID.randomUUID().toString());
    }
}
