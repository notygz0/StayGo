package org.staygo;

import java.time.LocalDateTime;
/**
 * clase que representa un pago realizado por un usuario en el sistema.
 * contiene la informacion sobre el usuario, el precio del pago, la fecha del pago
 * y el estado del pago (si ha sido realizado o no).
 *
 * @author Felipe Delgado
 */
public class Pago {

    private Usuario usuario;
    private float precio;
    private LocalDateTime fechaPago;
    private boolean estadoPago;

    private Pago(Usuario usuario, float precio) {
        this.usuario   = usuario;
        this.precio    = precio;
        this.estadoPago = false;
        this.fechaPago  = null;
    }

    public static Pago crearPago(Usuario usuario, float precio) {
        if (usuario == null) {
            throw new IllegalArgumentException("usuario no puede ser null");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("precio debe ser mayor que 0");
        }
        return new Pago(usuario, precio);
    }

    public boolean realizarPago() {
        if (!estadoPago) {
            this.estadoPago = true;
            this.fechaPago  = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(boolean estadoPago) {
        this.estadoPago = estadoPago;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
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
                fechaPago != null ? fechaPago : "no realizado"
        );
    }

}
