package com.edu.sena.zentry.service.dto;

import com.edu.sena.zentry.domain.enumeration.Estado;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.edu.sena.zentry.domain.Reservas} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReservasDTO implements Serializable {

    private String id;

    @NotNull
    private LocalDate fechaSolicitud;

    @NotNull
    private LocalDate fechaReserva;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horafin;

    @Min(value = 1)
    private Integer cuposApartados;

    @NotNull
    private Estado estado;

    @NotNull
    private ServicioConjuntoDTO servicioConjunto;

    @NotNull
    private VinculadoDTO vinculado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHorafin() {
        return horafin;
    }

    public void setHorafin(LocalTime horafin) {
        this.horafin = horafin;
    }

    public Integer getCuposApartados() {
        return cuposApartados;
    }

    public void setCuposApartados(Integer cuposApartados) {
        this.cuposApartados = cuposApartados;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ServicioConjuntoDTO getServicioConjunto() {
        return servicioConjunto;
    }

    public void setServicioConjunto(ServicioConjuntoDTO servicioConjunto) {
        this.servicioConjunto = servicioConjunto;
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
        if (!(o instanceof ReservasDTO)) {
            return false;
        }

        ReservasDTO reservasDTO = (ReservasDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reservasDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservasDTO{" +
            "id='" + getId() + "'" +
            ", fechaSolicitud='" + getFechaSolicitud() + "'" +
            ", fechaReserva='" + getFechaReserva() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horafin='" + getHorafin() + "'" +
            ", cuposApartados=" + getCuposApartados() +
            ", estado='" + getEstado() + "'" +
            ", servicioConjunto=" + getServicioConjunto() +
            ", vinculado=" + getVinculado() +
            "}";
    }
}
