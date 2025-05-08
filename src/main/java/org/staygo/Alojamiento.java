package org.staygo;
/**
 * clase abstracta que representa un alojamiento en el sistema.
 * los alojamientos pueden ser hoteles o departamentos.
 * contiene atributos comunes para todos los tipos de alojamientos, como direccion,
 * precio, descripcion y estado de ocupacion.
 *
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */

public abstract class Alojamiento {

    private String direccion;
    private float precio;
    private String descripcion;
    private boolean ocupado;

    protected Alojamiento() {
    }

    protected Alojamiento(String direccion, float precio, String descripcion) {
        this.direccion = direccion;
        this.precio = precio;
        this.descripcion = descripcion;
        this.ocupado = false;
    }

    public String getDireccion() {
        return direccion;
    }



    public float getPrecio() {
        return precio;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public abstract String verDetalles();

    public Usuario getDueno() {
        return null;
    }
}

