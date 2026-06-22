package com.edu.sena.zentry.domain;

import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A AdministradorConjunto.
 */
@Document(collection = "administrador_conjunto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdministradorConjunto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 50)
    @Field("nombres")
    private String nombres;

    @NotNull
    @Size(max = 50)
    @Field("apellidos")
    private String apellidos;

    @NotNull
    @Size(max = 20)
    @Field("numero_documento")
    private String numeroDocumento;

    @Size(max = 15)
    @Field("telefono")
    private String telefono;

    @NotNull
    @Field("correo")
    private String correo;

    @NotNull
    @Field("activo")
    private Boolean activo;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("conjuntoResidencial")
    private ConjuntoResidencial conjuntoResidencial;

    @DBRef
    @Field("tipoDocumento")
    private TipoDocumento tipoDocumento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public AdministradorConjunto id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return this.nombres;
    }

    public AdministradorConjunto nombres(String nombres) {
        this.setNombres(nombres);
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public AdministradorConjunto apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

    public AdministradorConjunto numeroDocumento(String numeroDocumento) {
        this.setNumeroDocumento(numeroDocumento);
        return this;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public AdministradorConjunto telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return this.correo;
    }

    public AdministradorConjunto correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public AdministradorConjunto activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AdministradorConjunto user(User user) {
        this.setUser(user);
        return this;
    }

    public ConjuntoResidencial getConjuntoResidencial() {
        return this.conjuntoResidencial;
    }

    public void setConjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        this.conjuntoResidencial = conjuntoResidencial;
    }

    public AdministradorConjunto conjuntoResidencial(ConjuntoResidencial conjuntoResidencial) {
        this.setConjuntoResidencial(conjuntoResidencial);
        return this;
    }

    public TipoDocumento getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public AdministradorConjunto tipoDocumento(TipoDocumento tipoDocumento) {
        this.setTipoDocumento(tipoDocumento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdministradorConjunto)) {
            return false;
        }
        return getId() != null && getId().equals(((AdministradorConjunto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdministradorConjunto{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", activo='" + getActivo() + "'" +
            "}";
    }
}
