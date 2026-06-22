package com.edu.sena.zentry.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.Vinculado} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VinculadoDTO implements Serializable {

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
    @Size(max = 100)
    private String correo;

    @NotNull
    private Boolean activo;

    @NotNull
    private UserDTO user;

    @NotNull
    private TipoDocumentoDTO tipoDocumento;

    @NotNull
    private AdministradorConjuntoDTO administradorConjunto;

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

    public TipoDocumentoDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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
        if (!(o instanceof VinculadoDTO)) {
            return false;
        }

        VinculadoDTO vinculadoDTO = (VinculadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vinculadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VinculadoDTO{" +
            "id='" + getId() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", activo='" + getActivo() + "'" +
            ", user=" + getUser() +
            ", tipoDocumento=" + getTipoDocumento() +
            ", administradorConjunto=" + getAdministradorConjunto() +
            "}";
    }
}
