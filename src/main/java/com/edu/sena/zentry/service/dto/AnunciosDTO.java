package com.edu.sena.zentry.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.Anuncios} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnunciosDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 50)
    private String titulo;

    @NotNull
    @Size(max = 200)
    private String descripcion;

    @NotNull
    private ZonedDateTime fecha;

    private byte[] imagen;

    private String imagenContentType;

    @NotNull
    private ConjuntoResidencialDTO conjuntoResidencial;

    @NotNull
    private AdministradorConjuntoDTO administradorConjunto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public ConjuntoResidencialDTO getConjuntoResidencial() {
        return conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencialDTO conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    public AdministradorConjuntoDTO getAdministradorConjunto() {
        return administradorConjunto;
    }

    public void setAdministradorConjunto(AdministradorConjuntoDTO administradorConjunto) {
        this.administradorConjunto = administradorConjunto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnunciosDTO)) {
            return false;
        }

        AnunciosDTO anunciosDTO = (AnunciosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anunciosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnunciosDTO{" +
            "id='" + getId() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", conjuntoResidencial=" + getConjuntoResidencial() +
            ", administradorConjunto=" + getAdministradorConjunto() +
            "}";
    }
}
