package com.edu.sena.zentry.domain.enumeration;

/**
 * The TipoInmueble enumeration.
 */
public enum TipoInmueble {
    APARTAMENTO("Apartamento"),
    CASA("Casa");

    private final String value;

    TipoInmueble(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
