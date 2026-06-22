package com.edu.sena.zentry.domain;

import com.edu.sena.zentry.domain.enumeration.TipoInmueble;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Inmueble.
 */
@Document(collection = "inmueble")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inmueble implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("tipo_inmueble")
    private TipoInmueble tipoInmueble;

    @Size(max = 10)
    @Field("torre")
    private String torre;

    @NotNull
    @Size(max = 10)
    @Field("numero_inmueble")
    private String numeroInmueble;

    @Field("piso")
    private Integer piso;

    @DBRef
    @Field("conjuntoResidencial")
    private ConjuntoResidencial conjuntoResidencial;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Inmueble id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoInmueble getTipoInmueble() {
        return this.tipoInmueble;
    }

    public Inmueble tipoInmueble(TipoInmueble tipoInmueble) {
        this.setTipoInmueble(tipoInmueble);
        return this;
    }

    public void setTipoInmueble(TipoInmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public String getTorre() {
        return this.torre;
    }

    public Inmueble torre(String torre) {
        this.setTorre(torre);
        return this;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    public String getNumeroInmueble() {
        return this.numeroInmueble;
    }

    public Inmueble numeroInmueble(String numeroInmueble) {
        this.setNumeroInmueble(numeroInmueble);
        return this;
    }

    public void setNumeroInmueble(String numeroInmueble) {
        this.numeroInmueble = numeroInmueble;
    }

    public Integer getPiso() {
        return this.piso;
    }

    public Inmueble piso(Integer piso) {
        this.setPiso(piso);
        return this;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public ConjuntoResidencial getConjuntoResidencial() {
        return this.conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    public Inmueble conjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        this.setConjuntoResidencial(conjuntoResidencial);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inmueble)) {
            return false;
        }
        return getId() != null && getId().equals(((Inmueble) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inmueble{" +
            "id=" + getId() +
            ", tipoInmueble='" + getTipoInmueble() + "'" +
            ", torre='" + getTorre() + "'" +
            ", numeroInmueble='" + getNumeroInmueble() + "'" +
            ", piso=" + getPiso() +
            "}";
    }
}
