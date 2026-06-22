package com.edu.sena.zentry.domain;

import java.util.UUID;

public class FacturaDePagoTestSamples {

    public static FacturaDePago getFacturaDePagoSample1() {
        return new FacturaDePago().id("id1");
    }

    public static FacturaDePago getFacturaDePagoSample2() {
        return new FacturaDePago().id("id2");
    }

    public static FacturaDePago getFacturaDePagoRandomSampleGenerator() {
        return new FacturaDePago().id(UUID.randomUUID().toString());
    }
}
