package com.edu.sena.zentry.domain;

import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ConjuntoResidencial.
 */
@Document(collection = "conjunto_residencial")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConjuntoResidencial implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("nombre_conjunto")
    private String nombreConjunto;

    @NotNull
    @Field("direccion_conjunto")
    private String direccionConjunto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ConjuntoResidencial id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreConjunto() {
        return this.nombreConjunto;
    }

    public ConjuntoResidencial nombreConjunto(String nombreConjunto) {
        this.setNombreConjunto(nombreConjunto);
        return this;
    }

    public void setNombreConjunto(String nombreConjunto) {
        this.nombreConjunto = nombreConjunto;
    }

    public String getDireccionConjunto() {
        return this.direccionConjunto;
    }

    public ConjuntoResidencial direccionConjunto(String direccionConjunto) {
        this.setDireccionConjunto(direccionConjunto);
        return this;
    }

    public void setDireccionConjunto(String direccionConjunto) {
        this.direccionConjunto = direccionConjunto;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConjuntoResidencial)) {
            return false;
        }
        return getId() != null && getId().equals(((ConjuntoResidencial) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConjuntoResidencial{" +
            "id=" + getId() +
            ", nombreConjunto='" + getNombreConjunto() + "'" +
            ", direccionConjunto='" + getDireccionConjunto() + "'" +
            "}";
    }
}
