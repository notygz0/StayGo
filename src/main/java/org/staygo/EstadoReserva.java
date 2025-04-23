package org.staygo;

public enum EstadoReserva {
    PENDIENTE,
    CONFIRMADO,
    CANCELADA;

    @Override
    public String toString() {
        return name();
    }
}

