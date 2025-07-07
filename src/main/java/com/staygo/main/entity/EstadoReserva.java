package com.staygo.main.entity;
/**
 * enum que representa el estado de una reserva en el sistema.
 * los posibles estados son: pendiente, confirmado y cancelada.
 *
 * @author Felipe Delgado
 */

public enum EstadoReserva {
    PENDIENTE,
    CONFIRMADO,
    CANCELADA, OCUPADO, DISPONIBLE;
}

