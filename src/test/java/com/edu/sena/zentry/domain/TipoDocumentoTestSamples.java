package com.edu.sena.zentry.domain;

import java.util.UUID;

public class TipoDocumentoTestSamples {

    public static TipoDocumento getTipoDocumentoSample1() {
        return new TipoDocumento().id("id1").nombreTipoDocumento("nombreTipoDocumento1");
    }

    public static TipoDocumento getTipoDocumentoSample2() {
        return new TipoDocumento().id("id2").nombreTipoDocumento("nombreTipoDocumento2");
    }

    public static TipoDocumento getTipoDocumentoRandomSampleGenerator() {
        return new TipoDocumento().id(UUID.randomUUID().toString()).nombreTipoDocumento(UUID.randomUUID().toString());
    }
}
