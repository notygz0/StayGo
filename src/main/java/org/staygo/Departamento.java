package org.staygo;
/**
 * clase que representa un departamento dentro del sistema de gestion de alojamientos.
 * esta clase extiende la clase {@link Alojamiento} y agrega atributos especificos relacionados
 * con un departamento, como el numero de habitaciones y el dueno del departamento.
 *
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 *
 */
public class Departamento extends Alojamiento {

    private int numHabitaciones;

    private Usuario dueno;

    /**
     * constructor para crear una nueva instancia de un departamento.
     *
     * @param direccion la direccion del departamento.
     * @param precio el precio del departamento por noche.
     * @param descripcion una descripcion del departamento.
     * @param numHabitaciones el numero de habitaciones que tiene el departamento.
     * @param dueno el dueno del departamento.
     */
    public Departamento(String direccion, float precio, String descripcion, int numHabitaciones, Usuario dueno) {
        super(direccion, precio, descripcion);
        this.numHabitaciones = numHabitaciones;

        this.dueno = dueno;
    }





    @Override
    public Usuario getDueno() {
        return dueno;
    }
    

    @Override
    public String verDetalles() {
        return "Departamento: " + getDireccion() + ", Precio: " + getPrecio() + ", Habitaciones: " + numHabitaciones + ", Dueño: " + (dueno != null ? dueno.getNombre() : "Desconocido");
    }
}


