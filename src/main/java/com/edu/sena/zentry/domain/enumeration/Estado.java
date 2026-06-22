package com.edu.sena.zentry.domain.enumeration;

/**
 * The Estado enumeration.
 */
public enum Estado {
    APROBADO("Aprobado"),
    RECHAZADO("Rechazado"),
    PENDIENTE("Pendiente");

    private final String value;

    Estado(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
