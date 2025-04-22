package org.staygo;

public class Departamento extends Alojamiento {

    private int numHabitaciones;
    private boolean moderno;
    private Usuario dueno;

    public Departamento() {
        super();
    }

    public Departamento(String direccion, float precio, String descripcion, int numHabitaciones, boolean moderno, Usuario dueno) {
        super(direccion, precio, descripcion);
        this.numHabitaciones = numHabitaciones;
        this.moderno = moderno;
        this.dueno = dueno;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public boolean isModerno() {
        return moderno;
    }

    public void setModerno(boolean moderno) {
        this.moderno = moderno;
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
        return "Departamento: " + getDireccion() + ", Precio: " + getPrecio() + ", Habitaciones: " + numHabitaciones + ", Moderno: " + moderno + ", Dueño: " + (dueno != null ? dueno.getNombre() : "Desconocido");
    }
}


