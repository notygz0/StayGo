package org.staygo;

public class Departamento extends Alojamiento {

    private int numHabitaciones;

    private Usuario dueno;

    public Departamento() {
        super();
    }

    public Departamento(String direccion, float precio, String descripcion, int numHabitaciones, Usuario dueno) {
        super(direccion, precio, descripcion);
        this.numHabitaciones = numHabitaciones;

        this.dueno = dueno;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }


    @Override
    public Usuario getDueno() {
        return dueno;
    }

    public void setDueno(Usuario dueno) {
        this.dueno = dueno;
    }

    @Override
    public String verDetalles() {
        return "Departamento: " + getDireccion() + ", Precio: " + getPrecio() + ", Habitaciones: " + numHabitaciones + ", Dueño: " + (dueno != null ? dueno.getNombre() : "Desconocido");
    }
}


