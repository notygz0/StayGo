package org.staygo;

public class Departamento extends Alojamiento {

    private int numHabitaciones;

    private Usuario dueno;


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


