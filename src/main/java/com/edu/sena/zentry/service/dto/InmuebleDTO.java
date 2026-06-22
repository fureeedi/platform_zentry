package com.edu.sena.zentry.service.dto;

import com.edu.sena.zentry.domain.enumeration.TipoInmueble;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.Inmueble} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InmuebleDTO implements Serializable {

    private String id;

    @NotNull
    private TipoInmueble tipoInmueble;

    @Size(max = 10)
    private String torre;

    @NotNull
    @Size(max = 10)
    private String numeroInmueble;

    private Integer piso;

    @NotNull
    private ConjuntoResidencialDTO conjuntoResidencial;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoInmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(TipoInmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public String getTorre() {
        return torre;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    public String getNumeroInmueble() {
        return numeroInmueble;
    }

    public void setNumeroInmueble(String numeroInmueble) {
        this.numeroInmueble = numeroInmueble;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public ConjuntoResidencialDTO getConjuntoResidencial() {
        return conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencialDTO conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InmuebleDTO)) {
            return false;
        }

        InmuebleDTO inmuebleDTO = (InmuebleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inmuebleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InmuebleDTO{" +
            "id='" + getId() + "'" +
            ", tipoInmueble='" + getTipoInmueble() + "'" +
            ", torre='" + getTorre() + "'" +
            ", numeroInmueble='" + getNumeroInmueble() + "'" +
            ", piso=" + getPiso() +
            ", conjuntoResidencial=" + getConjuntoResidencial() +
            "}";
    }
}
