package com.edu.sena.zentry.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.ConjuntoResidencial} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConjuntoResidencialDTO implements Serializable {

    private String id;

    @NotNull
    private String nombreConjunto;

    @NotNull
    private String direccionConjunto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreConjunto() {
        return nombreConjunto;
    }

    public void setNombreConjunto(String nombreConjunto) {
        this.nombreConjunto = nombreConjunto;
    }

    public String getDireccionConjunto() {
        return direccionConjunto;
    }

    public void setDireccionConjunto(String direccionConjunto) {
        this.direccionConjunto = direccionConjunto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConjuntoResidencialDTO)) {
            return false;
        }

        ConjuntoResidencialDTO conjuntoResidencialDTO = (ConjuntoResidencialDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, conjuntoResidencialDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConjuntoResidencialDTO{" +
            "id='" + getId() + "'" +
            ", nombreConjunto='" + getNombreConjunto() + "'" +
            ", direccionConjunto='" + getDireccionConjunto() + "'" +
            "}";
    }
}
