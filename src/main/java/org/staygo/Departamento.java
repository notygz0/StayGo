package org.staygo;

public class Departamento extends Alojamiento {

    private int numeroHabitaciones;
    private boolean amoblado;

    public Departamento(String direccion, float precio, String descripcion, int numeroHabitaciones, boolean amoblado) {
        super(direccion, precio, descripcion);
        this.numeroHabitaciones = numeroHabitaciones;
        this.amoblado = amoblado;
    }
    public boolean verificarAmoblado() {
        return amoblado;
    }
    public int getNumeroHabitaciones() {
        return numeroHabitaciones;
    }

    public void setNumeroHabitaciones(int numeroHabitaciones) {
        this.numeroHabitaciones = numeroHabitaciones;
    }

    public void setAmoblado(boolean amoblado) {
        this.amoblado = amoblado;
    }

    @Override
    public String verDetalles() {
        return String.format(
                "departamento: %s%n" +
                        "direccion: %s%n" +
                        "precio: %.2f%n" +
                        "habitaciones: %d%n" +
                        "amoblado: %s",
                getDescripcion(),
                getDireccion(),
                getPrecio(),
                numeroHabitaciones,
                amoblado ? "si" : "no"
        );
    }
}
