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

    /**
     * constructor para crear una nueva instancia de un hotel.
     *
     * @param direccion la direccion del hotel.
     * @param precio el precio del hotel por noche.
     * @param descripcion una descripcion del hotel.
     * @param numEstrellas el numero de estrellas del hotel.
     * @param numHabitaciones el numero de habitaciones del hotel.
     */
    public Hotel(String direccion, float precio, String descripcion, int numEstrellas, int numHabitaciones) {
        super(direccion, precio, descripcion);
        this.numEstrellas = numEstrellas;
        this.numHabitaciones = numHabitaciones;
    }

    /**
     * obtiene los detalles del hotel.
     * este metodo sobrescribe el metodo {@link Alojamiento#verDetalles()} para devolver los detalles
     * especificos del hotel, como la direccion, el precio, el numero de estrellas y el numero de habitaciones.
     *
     * @return una cadena con los detalles del hotel.
     */
    @Override
    public String verDetalles() {
        return "Hotel: " + getDireccion() + ", Precio: " + getPrecio() + ", Estrellas: " + numEstrellas + ", Habitaciones: " + numHabitaciones;
    }
}


