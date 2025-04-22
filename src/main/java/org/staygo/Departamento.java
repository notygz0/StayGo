package org.staygo;

public class Departamento extends Alojamiento {

    private int numHabitaciones;
    private boolean moderno;

    public Departamento() {
        super();
    }

    public Departamento(String direccion, float precio, String descripcion, int numHabitaciones, boolean moderno) {
        super(direccion, precio, descripcion); // Llamar al constructor de la clase base
        this.numHabitaciones = numHabitaciones;
        this.moderno = moderno;
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
    public String verDetalles() {
        return "Departamento: " + getDireccion() + ", Precio: " + getPrecio() + ", Habitaciones: " + numHabitaciones + ", Moderno: " + moderno;
    }
}

