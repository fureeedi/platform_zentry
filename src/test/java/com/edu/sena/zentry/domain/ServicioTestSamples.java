package com.edu.sena.zentry.domain;

import java.util.UUID;

public class ServicioTestSamples {

    public static Servicio getServicioSample1() {
        return new Servicio().id("id1").disponibilidad("disponibilidad1").nombreZonaComun("nombreZonaComun1").descripcion("descripcion1");
    }

    public static Servicio getServicioSample2() {
        return new Servicio().id("id2").disponibilidad("disponibilidad2").nombreZonaComun("nombreZonaComun2").descripcion("descripcion2");
    }

    public static Servicio getServicioRandomSampleGenerator() {
        return new Servicio()
            .id(UUID.randomUUID().toString())
            .disponibilidad(UUID.randomUUID().toString())
            .nombreZonaComun(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
