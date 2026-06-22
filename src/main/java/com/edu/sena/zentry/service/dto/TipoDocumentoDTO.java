package com.edu.sena.zentry.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.TipoDocumento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoDocumentoDTO implements Serializable {

    private String id;

    @Size(max = 50)
    private String nombreTipoDocumento;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = nombreTipoDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDocumentoDTO)) {
            return false;
        }

        TipoDocumentoDTO tipoDocumentoDTO = (TipoDocumentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoDocumentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDocumentoDTO{" +
            "id='" + getId() + "'" +
            ", nombreTipoDocumento='" + getNombreTipoDocumento() + "'" +
            "}";
    }
}
