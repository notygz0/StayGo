package org.staygo;

import java.time.LocalDateTime;

public class Resena {

    private Usuario usuario;
    private Alojamiento alojamiento;
    private String texto;
    private LocalDateTime fechaCreacion;
    private float calificacion;

    public Resena(Usuario usuario, Alojamiento alojamiento, String texto, LocalDateTime fechaCreacion, float calificacion) {
        this.usuario = usuario;
        this.alojamiento = alojamiento;
        this.texto = texto;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        if (calificacion < 1.0f || calificacion > 5.0f) {
            throw new IllegalArgumentException("la calificacion tiene que estar entre 1.0 y 5.0");
        }
        this.calificacion = calificacion;
    }

    public void editarResena(String nuevoTexto) {
        this.texto = nuevoTexto;
    }
    public String obtenerDetallesResena() {
        return String.format(
                "resena de %s sobre %s%n" +
                        "fecha: %s%n" +
                        "calificacion: %.1f/5.0%n" +
                        "texto: %s",
                usuario.getNombre(),
                alojamiento.verDetalles(),
                fechaCreacion,
                calificacion,
                texto
        );
    }
}