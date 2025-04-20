package org.staygo;

import java.time.LocalDate;

public abstract class Alojamiento {

    private boolean ocupado;
    private String direccion;
    private float precio;
    private String descripcion;

    public Alojamiento(String direccion, float precio, String descripcion) {
        this.direccion = direccion;
        this.precio = precio;
        this.descripcion = descripcion;
        this.ocupado=false;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean verificarDisponibilidad(LocalDate fechaInicio, LocalDate fechaFin) {
        return !ocupado;
    }

    public float calcularPrecio(int totalDias) {
        return precio*totalDias;
    }

    public abstract String verDetalles();
}
