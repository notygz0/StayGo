package org.staygo;

import java.time.LocalDateTime;

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
