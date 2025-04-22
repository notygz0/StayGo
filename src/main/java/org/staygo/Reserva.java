package org.staygo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.Objects;

public class Reserva {

    private Usuario usuario;
    private Alojamiento alojamiento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoReserva estadoReserva;

    @JsonCreator
    public Reserva(@JsonProperty("usuario") Usuario usuario,
                   @JsonProperty("alojamiento") Alojamiento alojamiento,
                   @JsonProperty("fechaInicio") LocalDate fechaInicio,
                   @JsonProperty("fechaFin") LocalDate fechaFin,
                   @JsonProperty("estadoReserva") EstadoReserva estadoReserva) {
        Objects.requireNonNull(usuario, "usuario no puede ser null");
        Objects.requireNonNull(alojamiento, "alojamiento no puede ser null");
        Objects.requireNonNull(fechaInicio, "fecha de inicio no puede ser null");
        Objects.requireNonNull(fechaFin, "fecha de fin no puede ser null");

        if (!fechaFin.isAfter(fechaInicio)) {
            throw new IllegalArgumentException("la fecha de fin debe ser posterior a la fecha de inicio");
        }

        if (fechaInicio.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede estar en el pasado");
        }
        this.usuario = usuario;
        this.alojamiento = alojamiento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoReserva = (estadoReserva != null) ? estadoReserva : EstadoReserva.PENDIENTE;
    }

    public boolean confirmarReserva() {
        if (estadoReserva == EstadoReserva.PENDIENTE) {
            estadoReserva = EstadoReserva.CONFIRMADO;
            return true;
        }
        return false;
    }

    public boolean cancelarReserva() {
        if (estadoReserva != EstadoReserva.CANCELADA) {
            estadoReserva = EstadoReserva.CANCELADA;
            return true;
        }
        return false;
    }

    public String obtenerDetallesReserva() {
        return String.format(
                "usuario: %s%n" +
                        "alojamiento: %s%n" +
                        "desde: %s  hasta: %s%n" +
                        "estado: %s",
                usuario.getNombre(),
                alojamiento.verDetalles(),
                fechaInicio,
                fechaFin,
                estadoReserva.name().toLowerCase()
        );
    }

    public Reserva(Usuario usuario, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin) {
        Objects.requireNonNull(usuario, "usuario no puede ser null");
        Objects.requireNonNull(alojamiento, "alojamiento no puede ser null");
        Objects.requireNonNull(fechaInicio, "fecha de inicio no puede ser null");
        Objects.requireNonNull(fechaFin, "fecha de fin no puede ser null");

        if (!fechaFin.isAfter(fechaInicio)) {
            throw new IllegalArgumentException("la fecha de fin debe ser posterior a la fecha de inicio");
        }

        if (fechaInicio.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede estar en el pasado");
        }
        this.usuario = usuario;
        this.alojamiento = alojamiento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoReserva = EstadoReserva.PENDIENTE;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

}
