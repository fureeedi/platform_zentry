package com.edu.sena.zentry.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.Servicio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicioDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 200)
    private String disponibilidad;

    @NotNull
    @Size(max = 50)
    private String nombreZonaComun;

    @Size(max = 200)
    private String descripcion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getNombreZonaComun() {
        return nombreZonaComun;
    }

    public void setNombreZonaComun(String nombreZonaComun) {
        this.nombreZonaComun = nombreZonaComun;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicioDTO)) {
            return false;
        }

        ServicioDTO servicioDTO = (ServicioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, servicioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicioDTO{" +
            "id='" + getId() + "'" +
            ", disponibilidad='" + getDisponibilidad() + "'" +
            ", nombreZonaComun='" + getNombreZonaComun() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
