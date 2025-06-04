package org.staygo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

/**
 * clase que representa una reserva realizada por un usuario en un alojamiento.
 * contiene informacion sobre el usuario que realiza la reserva, el alojamiento,
 * las fechas de inicio y fin de la reserva y el estado de la reserva.
 *
 * @author Felipe Delgado
 */
public class Reserva {

    private User usuario;
    private Alojamiento alojamiento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoReserva estadoReserva;

    /**
     * Constructor que crea una nueva reserva con los parámetros proporcionados.
     * Valida que las fechas sean correctas y que no haya conflictos con reservas existentes.
     *
     * @param usuario el usuario que realiza la reserva.
     * @param alojamiento el alojamiento que se está reservando.
     * @param fechaInicio la fecha de inicio de la reserva.
     * @param fechaFin la fecha de fin de la reserva.
     * @param estadoReserva el estado inicial de la reserva.
     * @throws IllegalArgumentException si las fechas son inválidas o si hay conflictos con otras reservas.
     */
    @JsonCreator
    public Reserva(@JsonProperty("usuario") User usuario,
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

    /**
     * Confirma la reserva, cambiando su estado a CONFIRMADO.
     *
     * @return true si la reserva fue confirmada correctamente, false si ya estaba confirmada.
     */
    public boolean confirmarReserva() {
        if (estadoReserva == EstadoReserva.PENDIENTE) {
            estadoReserva = EstadoReserva.CONFIRMADO;
            return true;
        }
        return false;
    }

    /**
     * Cancela la reserva, cambiando su estado a CANCELADA.
     *
     * @return true si la reserva fue cancelada correctamente, false si ya estaba cancelada.
     */
    public boolean cancelarReserva() {
        if (estadoReserva != EstadoReserva.CANCELADA) {
            estadoReserva = EstadoReserva.CANCELADA;
            return true;
        }
        return false;
    }

    /**
     * Obtiene los detalles de la reserva como una cadena de texto.
     *
     * @return una cadena que contiene los detalles de la reserva.
     */
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

    /**
     * Constructor alternativo que valida que no existan reservas superpuestas en las fechas.
     *
     * @param usuario el usuario que realiza la reserva.
     * @param alojamiento el alojamiento que se está reservando.
     * @param fechaInicio la fecha de inicio de la reserva.
     * @param fechaFin la fecha de fin de la reserva.
     * @throws IllegalArgumentException si las fechas son inválidas o si hay un conflicto de reserva.
     */
    public Reserva(User usuario, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin) {
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

        for (Reserva reserva : usuario.obtenerReservas()) {
            if (reserva.getAlojamiento().equals(alojamiento) &&
                    (fechaInicio.isBefore(reserva.getFechaFin()) && fechaFin.isAfter(reserva.getFechaInicio()))) {
                throw new IllegalArgumentException("Las fechas de reserva se sobreponen con una reserva existente.");
            }
        }

        this.usuario = usuario;
        this.alojamiento = alojamiento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoReserva = EstadoReserva.PENDIENTE;
    }

    /**
     * Obtiene el usuario que realiza la reserva.
     *
     * @return el usuario de la reserva.
     */
    public User getUsuario() {
        return usuario;
    }

    /**
     * Obtiene el alojamiento que se está reservando.
     *
     * @return el alojamiento de la reserva.
     */
    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    /**
     * Obtiene la fecha de inicio de la reserva.
     *
     * @return la fecha de inicio de la reserva.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Obtiene la fecha de fin de la reserva.
     *
     * @return la fecha de fin de la reserva.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Obtiene el estado de la reserva.
     *
     * @return el estado de la reserva.
     */
    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }
}

