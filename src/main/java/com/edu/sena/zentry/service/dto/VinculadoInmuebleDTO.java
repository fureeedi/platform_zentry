package com.edu.sena.zentry.service.dto;

import com.edu.sena.zentry.domain.enumeration.TipoVinculo;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.VinculadoInmueble} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VinculadoInmuebleDTO implements Serializable {

    private String id;

    @NotNull
    private TipoVinculo tipoVinculo;

    @NotNull
    private VinculadoDTO vinculado;

    @NotNull
    private InmuebleDTO inmueble;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoVinculo getTipoVinculo() {
        return tipoVinculo;
    }

    public void setTipoVinculo(TipoVinculo tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }

    public VinculadoDTO getVinculado() {
        return vinculado;
    }

    public void setVinculado(VinculadoDTO vinculado) {
        this.vinculado = vinculado;
    }

    public InmuebleDTO getInmueble() {
        return inmueble;
    }

    public void setInmueble(InmuebleDTO inmueble) {
        this.inmueble = inmueble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VinculadoInmuebleDTO)) {
            return false;
        }

        VinculadoInmuebleDTO vinculadoInmuebleDTO = (VinculadoInmuebleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vinculadoInmuebleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VinculadoInmuebleDTO{" +
            "id='" + getId() + "'" +
            ", tipoVinculo='" + getTipoVinculo() + "'" +
            ", vinculado=" + getVinculado() +
            ", inmueble=" + getInmueble() +
            "}";
    }
}
