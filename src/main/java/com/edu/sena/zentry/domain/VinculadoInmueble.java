package com.edu.sena.zentry.domain;

import com.edu.sena.zentry.domain.enumeration.TipoVinculo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A VinculadoInmueble.
 */
@Document(collection = "vinculado_inmueble")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VinculadoInmueble implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("tipo_vinculo")
    private TipoVinculo tipoVinculo;

    @DBRef
    @Field("vinculado")
    @JsonIgnoreProperties(value = { "user", "tipoDocumento", "administradorConjunto" }, allowSetters = true)
    private Vinculado vinculado;

    @DBRef
    @Field("inmueble")
    @JsonIgnoreProperties(value = { "conjuntoResidencial" }, allowSetters = true)
    private Inmueble inmueble;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public VinculadoInmueble id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoVinculo getTipoVinculo() {
        return this.tipoVinculo;
    }

    public VinculadoInmueble tipoVinculo(TipoVinculo tipoVinculo) {
        this.setTipoVinculo(tipoVinculo);
        return this;
    }

    public void setTipoVinculo(TipoVinculo tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }

    public Vinculado getVinculado() {
        return this.vinculado;
    }

    public void setVinculado(Vinculado vinculado) {
        this.vinculado = vinculado;
    }

    public VinculadoInmueble vinculado(Vinculado vinculado) {
        this.setVinculado(vinculado);
        return this;
    }

    public Inmueble getInmueble() {
        return this.inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public VinculadoInmueble inmueble(Inmueble inmueble) {
        this.setInmueble(inmueble);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VinculadoInmueble)) {
            return false;
        }
        return getId() != null && getId().equals(((VinculadoInmueble) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VinculadoInmueble{" +
            "id=" + getId() +
            ", tipoVinculo='" + getTipoVinculo() + "'" +
            "}";
    }
}
