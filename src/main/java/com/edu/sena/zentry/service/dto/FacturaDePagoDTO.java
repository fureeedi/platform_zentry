package com.edu.sena.zentry.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.FacturaDePago} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FacturaDePagoDTO implements Serializable {

    private String id;

    @NotNull
    private LocalDate fechaEnvio;

    private byte[] imagenFactura;

    private String imagenFacturaContentType;

    @NotNull
    private ConjuntoResidencialDTO conjuntoResidencial;

    @NotNull
    private VinculadoDTO vinculado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public byte[] getImagenFactura() {
        return imagenFactura;
    }

    public void setImagenFactura(byte[] imagenFactura) {
        this.imagenFactura = imagenFactura;
    }

    public String getImagenFacturaContentType() {
        return imagenFacturaContentType;
    }

    public void setImagenFacturaContentType(String imagenFacturaContentType) {
        this.imagenFacturaContentType = imagenFacturaContentType;
    }

    public ConjuntoResidencialDTO getConjuntoResidencial() {
        return conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencialDTO conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    public VinculadoDTO getVinculado() {
        return vinculado;
    }

    public void setVinculado(VinculadoDTO vinculado) {
        this.vinculado = vinculado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacturaDePagoDTO)) {
            return false;
        }

        FacturaDePagoDTO facturaDePagoDTO = (FacturaDePagoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, facturaDePagoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacturaDePagoDTO{" +
            "id='" + getId() + "'" +
            ", fechaEnvio='" + getFechaEnvio() + "'" +
            ", imagenFactura='" + getImagenFactura() + "'" +
            ", conjuntoResidencial=" + getConjuntoResidencial() +
            ", vinculado=" + getVinculado() +
            "}";
    }
}
