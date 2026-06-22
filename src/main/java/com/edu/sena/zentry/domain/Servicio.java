package com.edu.sena.zentry.domain;

import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Servicio.
 */
@Document(collection = "servicio")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Servicio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 200)
    @Field("disponibilidad")
    private String disponibilidad;

    @NotNull
    @Size(max = 50)
    @Field("nombre_zona_comun")
    private String nombreZonaComun;

    @Size(max = 200)
    @Field("descripcion")
    private String descripcion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Servicio id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisponibilidad() {
        return this.disponibilidad;
    }

    public Servicio disponibilidad(String disponibilidad) {
        this.setDisponibilidad(disponibilidad);
        return this;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getNombreZonaComun() {
        return this.nombreZonaComun;
    }

    public Servicio nombreZonaComun(String nombreZonaComun) {
        this.setNombreZonaComun(nombreZonaComun);
        return this;
    }

    public void setNombreZonaComun(String nombreZonaComun) {
        this.nombreZonaComun = nombreZonaComun;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Servicio descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Servicio)) {
            return false;
        }
        return getId() != null && getId().equals(((Servicio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Servicio{" +
            "id=" + getId() +
            ", disponibilidad='" + getDisponibilidad() + "'" +
            ", nombreZonaComun='" + getNombreZonaComun() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
