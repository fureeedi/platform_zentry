package com.edu.sena.zentry.domain.enumeration;

/**
 * The TipoVinculo enumeration.
 */
public enum TipoVinculo {
    PROPIETARIO("Propietario"),
    ARRENDATARIO("Arrendatario");

    private final String value;

    TipoVinculo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
