package com.edu.sena.zentry.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.AdministradorConjunto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdministradorConjuntoDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 50)
    private String nombres;

    @NotNull
    @Size(max = 50)
    private String apellidos;

    @NotNull
    @Size(max = 20)
    private String numeroDocumento;

    @Size(max = 15)
    private String telefono;

    @NotNull
    private String correo;

    @NotNull
    private Boolean activo;

    @NotNull
    private UserDTO user;

    @NotNull
    private ConjuntoResidencialDTO conjuntoResidencial;

    @NotNull
    private TipoDocumentoDTO tipoDocumento;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ConjuntoResidencialDTO getConjuntoResidencial() {
        return conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencialDTO conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    public TipoDocumentoDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdministradorConjuntoDTO)) {
            return false;
        }

        AdministradorConjuntoDTO administradorConjuntoDTO = (AdministradorConjuntoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, administradorConjuntoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministradorConjuntoDTO{" +
            "id='" + getId() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", activo='" + getActivo() + "'" +
            ", user=" + getUser() +
            ", conjuntoResidencial=" + getConjuntoResidencial() +
            ", tipoDocumento=" + getTipoDocumento() +
            "}";
    }
}
