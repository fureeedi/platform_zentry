package com.edu.sena.zentry.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservasTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Reservas getReservasSample1() {
        return new Reservas().id("id1").cuposApartados(1);
    }

    public static Reservas getReservasSample2() {
        return new Reservas().id("id2").cuposApartados(2);
    }

    public static Reservas getReservasRandomSampleGenerator() {
        return new Reservas().id(UUID.randomUUID().toString()).cuposApartados(intCount.incrementAndGet());
    }
}
