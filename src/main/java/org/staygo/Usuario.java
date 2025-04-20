package org.staygo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private Long id_usuario;
    private String nombre;
    private String contrasena;
    private Roles rol;
    private List<Reserva> reservas;

    public Usuario(Long id_usuario, String nombre, String contrasena, Roles rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
        this.reservas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Roles getRol() {
        return rol;
    }

    public boolean iniciarSesion(String nombre, String contrasena) {
        return this.nombre.equals(nombre) && this.contrasena.equals(contrasena);
    }

    public void realizarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public List<Reserva> obtenerReservas() {
        return reservas;
    }

    public void mostrarDetalles() {
        System.out.println("usuario: " + nombre + " (id: " + id_usuario + ")");
        System.out.println("rol: " + rol);
        System.out.println("numero de reservas: " + reservas.size());
    }
}
