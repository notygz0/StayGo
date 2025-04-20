package org.staygo;

import java.util.List;

public class Hotel extends Alojamiento {

    private int numeroEstrellas;
    private int numeroHabitaciones;
    private List<String> servicios;

    public Hotel(String direccion, float precio, String descripcion, int numeroEstrellas, int numeroHabitaciones) {
        super(direccion, precio, descripcion);
        this.numeroEstrellas = numeroEstrellas;
        this.numeroHabitaciones = numeroHabitaciones;
        this.servicios = servicios;
    }

    public int getNumeroEstrellas() {
        return numeroEstrellas;
    }

    public void setNumeroEstrellas(int numeroEstrellas) {
        this.numeroEstrellas = numeroEstrellas;
    }

    public int getNumeroHabitaciones() {
        return numeroHabitaciones;
    }

    public void setNumeroHabitaciones(int numeroHabitaciones) {
        this.numeroHabitaciones = numeroHabitaciones;
    }

    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }

    @Override
    public String verDetalles() {
        return String.format(
                "hotel: %s%n" +
                        "direccion: %s%n" +
                        "precio por noche: %.2f%n" +
                        "estrellas: %d%n" +
                        "habitaciones: %d%n" +
                        "servicios: %s",
                getDescripcion(),
                getDireccion(),
                getPrecio(),
                numeroEstrellas,
                numeroHabitaciones,
                String.join(", ", servicios)
        );
    }
}
