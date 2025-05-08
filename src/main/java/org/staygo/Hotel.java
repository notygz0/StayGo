package org.staygo;

import java.util.List;

public class Hotel extends Alojamiento {

    private int numEstrellas;
    private int numHabitaciones;


    public Hotel(String direccion, float precio, String descripcion, int numEstrellas, int numHabitaciones) {
        super(direccion, precio, descripcion);
        this.numEstrellas = numEstrellas;
        this.numHabitaciones = numHabitaciones;
    }


    @Override
    public String verDetalles() {
        return "Hotel: " + getDireccion() + ", Precio: " + getPrecio() + ", Estrellas: " + numEstrellas + ", Habitaciones: " + numHabitaciones + ", Servicios: " + servicios;
    }
}

