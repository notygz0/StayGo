package org.staygo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Departamento.class, name = "departamento"),
        @JsonSubTypes.Type(value = Hotel.class, name = "hotel")
})
public abstract class Alojamiento {

    private String direccion;
    private float precio;
    private String descripcion;
    private boolean ocupado;

    public Alojamiento() {
        // Constructor vacío necesario para la deserialización
    }

    public Alojamiento(String direccion, float precio, String descripcion) {
        this.direccion = direccion;
        this.precio = precio;
        this.descripcion = descripcion;
        this.ocupado = false;
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

