package com.edu.sena.zentry.service.dto;

import com.edu.sena.zentry.domain.enumeration.TipoDisponibilidad;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.ServicioConjunto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicioConjuntoDTO implements Serializable {

    private String id;

    @NotNull
    private TipoDisponibilidad disponible;

    @NotNull
    @Min(value = 1)
    private Integer aforoMaximo;

    @NotNull
    private ConjuntoResidencialDTO conjuntoResidencial;

    @NotNull
    private ServicioDTO servicio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoDisponibilidad getDisponible() {
        return disponible;
    }

    public void setDisponible(TipoDisponibilidad disponible) {
        this.disponible = disponible;
    }

    public Integer getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(Integer aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public ConjuntoResidencialDTO getConjuntoResidencial() {
        return conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencialDTO conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    public ServicioDTO getServicio() {
        return servicio;
    }

    public void setServicio(ServicioDTO servicio) {
        this.servicio = servicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicioConjuntoDTO)) {
            return false;
        }

        ServicioConjuntoDTO servicioConjuntoDTO = (ServicioConjuntoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, servicioConjuntoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicioConjuntoDTO{" +
            "id='" + getId() + "'" +
            ", disponible='" + getDisponible() + "'" +
            ", aforoMaximo=" + getAforoMaximo() +
            ", conjuntoResidencial=" + getConjuntoResidencial() +
            ", servicio=" + getServicio() +
            "}";
    }
}
