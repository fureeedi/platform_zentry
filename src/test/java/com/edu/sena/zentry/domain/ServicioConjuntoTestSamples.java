package com.edu.sena.zentry.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ServicioConjuntoTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ServicioConjunto getServicioConjuntoSample1() {
        return new ServicioConjunto().id("id1").aforoMaximo(1);
    }

    public static ServicioConjunto getServicioConjuntoSample2() {
        return new ServicioConjunto().id("id2").aforoMaximo(2);
    }

    public static ServicioConjunto getServicioConjuntoRandomSampleGenerator() {
        return new ServicioConjunto().id(UUID.randomUUID().toString()).aforoMaximo(intCount.incrementAndGet());
    }
}
