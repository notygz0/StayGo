package org.StayGo;

import java.time.LocalDate;

public abstract class Alojamiento {

    private boolean ocupado;
    private String direccion;
    private float precio;
    private String descripcion;
    private boolean isHotel;

    /**
     *
     * @param fechaInicio
     * @param fechaFin
     */
    public boolean verificarDisponibilidad(LocalDate fechaInicio, LocalDate fechaFin) {
        // TODO - implement Alojamiento.verificarDisponibilidad
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param totalDias
     */
    public float calcularPrecio(int totalDias) {
        // TODO - implement Alojamiento.calcularPrecio
        throw new UnsupportedOperationException();
    }

    public String verDetallesDepartamento() {
        // TODO - implement Alojamiento.verDetallesDepartamento
        throw new UnsupportedOperationException();
    }

}
