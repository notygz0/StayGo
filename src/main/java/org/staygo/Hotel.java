package org.staygo;

import java.util.List;

public class Hotel extends Alojamiento {

    private int numEstrellas;
    private int numHabitaciones;
    private List<String> servicios;

    public Hotel() {
        super(); // Llamar al constructor de la clase base
    }

    public Hotel(String direccion, float precio, String descripcion, int numEstrellas, int numHabitaciones) {
        super(direccion, precio, descripcion); // Llamar al constructor de la clase base
        this.numEstrellas = numEstrellas;
        this.numHabitaciones = numHabitaciones;
    }

    public int getNumEstrellas() {
        return numEstrellas;
    }

    public void setNumEstrellas(int numEstrellas) {
        this.numEstrellas = numEstrellas;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }

    @Override
    public String verDetalles() {
        return "Hotel: " + getDireccion() + ", Precio: " + getPrecio() + ", Estrellas: " + numEstrellas + ", Habitaciones: " + numHabitaciones + ", Servicios: " + servicios;
    }
}

