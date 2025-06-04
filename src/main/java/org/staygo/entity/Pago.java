package org.staygo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * clase que representa un pago realizado por un usuario en el sistema.
 * contiene la informacion sobre el usuario, el precio del pago, la fecha del pago
 * y el estado del pago (si ha sido realizado o no).
 *
 * @author Felipe Delgado
 */
public class Pago {

    private User usuario;
    private float precio;
    private LocalDateTime fechaPago;
    private boolean estadoPago;

    @JsonCreator
    public Pago(@JsonProperty("usuario") User usuario,
                @JsonProperty("precio") float precio,
                @JsonProperty("fechaPago") LocalDateTime fechaPago,
                @JsonProperty("estadoPago") boolean estadoPago) {
        this.usuario = usuario;
        this.precio = precio;
        this.fechaPago = fechaPago;
        this.estadoPago = estadoPago;
    }

    // Constructor para crear pago nuevo (sin fecha ni estado realizado)
    public static Pago crearPago(User usuario, float precio) {
        if (usuario == null) {
            throw new IllegalArgumentException("usuario no puede ser null");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("precio debe ser mayor que 0");
        }
        return new Pago(usuario, precio, null, false);
    }

    public boolean realizarPago() {
        if (!estadoPago) {
            this.estadoPago = true;
            this.fechaPago = LocalDateTime.now();
            return true;
        }
        return false;
    }

    // Getters
    public User getUsuario() {
        return usuario;
    }

    public float getPrecio() {
        return precio;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public boolean isEstadoPago() {
        return estadoPago;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public void setEstadoPago(boolean estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String obtenerDetallesPago() {
        return String.format(
                "usuario: %s%n" +
                        "precio: %.2f%n" +
                        "estado del pago: %s%n" +
                        "fecha de pago: %s",
                usuario.getNombre(),
                precio,
                estadoPago ? "realizado" : "pendiente",
                fechaPago != null ? fechaPago.toString() : "no realizado"
        );
    }
}
