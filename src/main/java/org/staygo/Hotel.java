package org.staygo;
/**
 * clase que representa un hotel dentro del sistema de gestion de alojamientos.
 * esta clase extiende la clase {@link Alojamiento} y agrega atributos especificos relacionados
 * con un hotel, como el numero de estrellas y el numero de habitaciones.
 *
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */

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
        return "Hotel: " + getDireccion() + ", Precio: " + getPrecio() + ", Estrellas: " + numEstrellas + ", Habitaciones: " + numHabitaciones;
    }
}

