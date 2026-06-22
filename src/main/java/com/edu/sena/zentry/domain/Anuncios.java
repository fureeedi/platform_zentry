package com.edu.sena.zentry.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Anuncios.
 */
@Document(collection = "anuncios")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Anuncios implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 50)
    @Field("titulo")
    private String titulo;

    @NotNull
    @Size(max = 200)
    @Field("descripcion")
    private String descripcion;

    @NotNull
    @Field("fecha")
    private ZonedDateTime fecha;

    @Field("imagen")
    private byte[] imagen;

    @Field("imagen_content_type")
    private String imagenContentType;

    @DBRef
    @Field("conjuntoResidencial")
    private ConjuntoResidencial conjuntoResidencial;

    @DBRef
    @Field("administradorConjunto")
    @JsonIgnoreProperties(value = { "user", "conjuntoResidencial", "tipoDocumento" }, allowSetters = true)
    private AdministradorConjunto administradorConjunto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Anuncios id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Anuncios titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Anuncios descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getFecha() {
        return this.fecha;
    }

    public Anuncios fecha(ZonedDateTime fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public byte[] getImagen() {
        return this.imagen;
    }

    public Anuncios imagen(byte[] imagen) {
        this.setImagen(imagen);
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return this.imagenContentType;
    }

    public Anuncios imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public ConjuntoResidencial getConjuntoResidencial() {
        return this.conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    public Anuncios conjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        this.setConjuntoResidencial(conjuntoResidencial);
        return this;
    }

    public AdministradorConjunto getAdministradorConjunto() {
        return this.administradorConjunto;
    }

    public void setAdministradorConjunto(AdministradorConjunto administradorConjunto) {
        this.administradorConjunto = administradorConjunto;
    }

    public Anuncios administradorConjunto(AdministradorConjunto administradorConjunto) {
        this.setAdministradorConjunto(administradorConjunto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Anuncios)) {
            return false;
        }
        return getId() != null && getId().equals(((Anuncios) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Anuncios{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
