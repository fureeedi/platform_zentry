package com.edu.sena.zentry.domain.enumeration;

/**
 * The TipoDisponibilidad enumeration.
 */
public enum TipoDisponibilidad {
    ABIERTO("abierto"),
    CERRADO("cerrado"),
    MANTENIMIENTO("en_mantenimiento");

    private final String value;

    TipoDisponibilidad(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
