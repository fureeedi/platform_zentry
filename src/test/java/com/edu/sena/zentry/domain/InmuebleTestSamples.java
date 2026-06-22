package com.edu.sena.zentry.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class InmuebleTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Inmueble getInmuebleSample1() {
        return new Inmueble().id("id1").torre("torre1").numeroInmueble("numeroInmueble1").piso(1);
    }

    public static Inmueble getInmuebleSample2() {
        return new Inmueble().id("id2").torre("torre2").numeroInmueble("numeroInmueble2").piso(2);
    }

    public static Inmueble getInmuebleRandomSampleGenerator() {
        return new Inmueble()
            .id(UUID.randomUUID().toString())
            .torre(UUID.randomUUID().toString())
            .numeroInmueble(UUID.randomUUID().toString())
            .piso(intCount.incrementAndGet());
    }
}
