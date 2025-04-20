package org.staygo;

import java.time.LocalDate;

public class Reserva {

    private Usuario usuario;
    private Alojamiento alojamiento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estadoReserva;

    public Reserva(Usuario usuario, Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin) {
        this.usuario = usuario;
        this.alojamiento = alojamiento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoReserva = "pendiente";
    }

    public boolean confirmarReserva() {
        if ("pendiente".equals(estadoReserva)) {
            estadoReserva = "confirmada";
            return true;
        }
        return false;
    }

    public boolean cancelarReserva() {
        if (!"cancelada".equals(estadoReserva)) {
            estadoReserva = "cancelada";
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
                estadoReserva
        );
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

    public String getEstadoReserva() {
        return estadoReserva;
    }
}