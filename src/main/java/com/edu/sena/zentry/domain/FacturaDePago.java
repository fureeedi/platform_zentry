package com.edu.sena.zentry.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A FacturaDePago.
 */
@Document(collection = "factura_de_pago")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FacturaDePago implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("fecha_envio")
    private LocalDate fechaEnvio;

    @Field("imagen_factura")
    private byte[] imagenFactura;

    @NotNull
    @Field("imagen_factura_content_type")
    private String imagenFacturaContentType;

    @DBRef
    @Field("conjuntoResidencial")
    private ConjuntoResidencial conjuntoResidencial;

    @DBRef
    @Field("vinculado")
    @JsonIgnoreProperties(value = { "user", "tipoDocumento", "administradorConjunto" }, allowSetters = true)
    private Vinculado vinculado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public FacturaDePago id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaEnvio() {
        return this.fechaEnvio;
    }

    public FacturaDePago fechaEnvio(LocalDate fechaEnvio) {
        this.setFechaEnvio(fechaEnvio);
        return this;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public byte[] getImagenFactura() {
        return this.imagenFactura;
    }

    public FacturaDePago imagenFactura(byte[] imagenFactura) {
        this.setImagenFactura(imagenFactura);
        return this;
    }

    public void setImagenFactura(byte[] imagenFactura) {
        this.imagenFactura = imagenFactura;
    }

    public String getImagenFacturaContentType() {
        return this.imagenFacturaContentType;
    }

    public FacturaDePago imagenFacturaContentType(String imagenFacturaContentType) {
        this.imagenFacturaContentType = imagenFacturaContentType;
        return this;
    }

    public void setImagenFacturaContentType(String imagenFacturaContentType) {
        this.imagenFacturaContentType = imagenFacturaContentType;
    }

    public ConjuntoResidencial getConjuntoResidencial() {
        return this.conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    public FacturaDePago conjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        this.setConjuntoResidencial(conjuntoResidencial);
        return this;
    }

    public Vinculado getVinculado() {
        return this.vinculado;
    }

    public void setVinculado(Vinculado vinculado) {
        this.vinculado = vinculado;
    }

    public FacturaDePago vinculado(Vinculado vinculado) {
        this.setVinculado(vinculado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacturaDePago)) {
            return false;
        }
        return getId() != null && getId().equals(((FacturaDePago) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacturaDePago{" +
            "id=" + getId() +
            ", fechaEnvio='" + getFechaEnvio() + "'" +
            ", imagenFactura='" + getImagenFactura() + "'" +
            ", imagenFacturaContentType='" + getImagenFacturaContentType() + "'" +
            "}";
    }
}
