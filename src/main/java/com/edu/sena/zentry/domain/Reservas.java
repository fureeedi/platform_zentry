package com.edu.sena.zentry.domain;

import com.edu.sena.zentry.domain.enumeration.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Reservas.
 */
@Document(collection = "reservas")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reservas implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("fecha_solicitud")
    private LocalDate fechaSolicitud;

    @NotNull
    @Field("fecha_reserva")
    private LocalDate fechaReserva;

    @NotNull
    @Field("hora_inicio")
    private LocalTime horaInicio;

    @NotNull
    @Field("horafin")
    private LocalTime horafin;

    @Min(value = 1)
    @Field("cupos_apartados")
    private Integer cuposApartados;

    @NotNull
    @Field("estado")
    private Estado estado;

    @DBRef
    @Field("servicioConjunto")
    @JsonIgnoreProperties(value = { "conjuntoResidencial", "servicio" }, allowSetters = true)
    private ServicioConjunto servicioConjunto;

    @DBRef
    @Field("vinculado")
    @JsonIgnoreProperties(value = { "user", "tipoDocumento", "administradorConjunto" }, allowSetters = true)
    private Vinculado vinculado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Reservas id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaSolicitud() {
        return this.fechaSolicitud;
    }

    public Reservas fechaSolicitud(LocalDate fechaSolicitud) {
        this.setFechaSolicitud(fechaSolicitud);
        return this;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaReserva() {
        return this.fechaReserva;
    }

    public Reservas fechaReserva(LocalDate fechaReserva) {
        this.setFechaReserva(fechaReserva);
        return this;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraInicio() {
        return this.horaInicio;
    }

    public Reservas horaInicio(LocalTime horaInicio) {
        this.setHoraInicio(horaInicio);
        return this;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHorafin() {
        return this.horafin;
    }

    public Reservas horafin(LocalTime horafin) {
        this.setHorafin(horafin);
        return this;
    }

    public void setHorafin(LocalTime horafin) {
        this.horafin = horafin;
    }

    public Integer getCuposApartados() {
        return this.cuposApartados;
    }

    public Reservas cuposApartados(Integer cuposApartados) {
        this.setCuposApartados(cuposApartados);
        return this;
    }

    public void setCuposApartados(Integer cuposApartados) {
        this.cuposApartados = cuposApartados;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public Reservas estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ServicioConjunto getServicioConjunto() {
        return this.servicioConjunto;
    }

    public void setServicioConjunto(ServicioConjunto servicioConjunto) {
        this.servicioConjunto = servicioConjunto;
    }

    public Reservas servicioConjunto(ServicioConjunto servicioConjunto) {
        this.setServicioConjunto(servicioConjunto);
        return this;
    }

    public Vinculado getVinculado() {
        return this.vinculado;
    }

    public void setVinculado(Vinculado vinculado) {
        this.vinculado = vinculado;
    }

    public Reservas vinculado(Vinculado vinculado) {
        this.setVinculado(vinculado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservas)) {
            return false;
        }
        return getId() != null && getId().equals(((Reservas) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservas{" +
            "id=" + getId() +
            ", fechaSolicitud='" + getFechaSolicitud() + "'" +
            ", fechaReserva='" + getFechaReserva() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horafin='" + getHorafin() + "'" +
            ", cuposApartados=" + getCuposApartados() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
