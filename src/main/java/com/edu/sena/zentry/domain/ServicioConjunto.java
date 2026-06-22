package com.edu.sena.zentry.domain;

import com.edu.sena.zentry.domain.enumeration.TipoDisponibilidad;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ServicioConjunto.
 */
@Document(collection = "servicio_conjunto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicioConjunto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("disponible")
    private TipoDisponibilidad disponible;

    @NotNull
    @Min(value = 1)
    @Field("aforo_maximo")
    private Integer aforoMaximo;

    @DBRef
    @Field("conjuntoResidencial")
    private ConjuntoResidencial conjuntoResidencial;

    @DBRef
    @Field("servicio")
    private Servicio servicio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ServicioConjunto id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoDisponibilidad getDisponible() {
        return this.disponible;
    }

    public ServicioConjunto disponible(TipoDisponibilidad disponible) {
        this.setDisponible(disponible);
        return this;
    }

    public void setDisponible(TipoDisponibilidad disponible) {
        this.disponible = disponible;
    }

    public Integer getAforoMaximo() {
        return this.aforoMaximo;
    }

    public ServicioConjunto aforoMaximo(Integer aforoMaximo) {
        this.setAforoMaximo(aforoMaximo);
        return this;
    }

    public void setAforoMaximo(Integer aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public ConjuntoResidencial getConjuntoResidencial() {
        return this.conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    public ServicioConjunto conjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        this.setConjuntoResidencial(conjuntoResidencial);
        return this;
    }

    public Servicio getServicio() {
        return this.servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public ServicioConjunto servicio(Servicio servicio) {
        this.setServicio(servicio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicioConjunto)) {
            return false;
        }
        return getId() != null && getId().equals(((ServicioConjunto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicioConjunto{" +
            "id=" + getId() +
            ", disponible='" + getDisponible() + "'" +
            ", aforoMaximo=" + getAforoMaximo() +
            "}";
    }
}
