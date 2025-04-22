package org.staygo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
        if (rol == Roles.CLIENTE){
            reservas.add(reserva);
        }else {
            throw new IllegalStateException("solo los clientes pueden realizar reservas");
        }
    }

    public List<Reserva> obtenerReservas() {
        return reservas;
    }

    public void mostrarDetalles() {
        System.out.println("usuario: " + nombre + " (id: " + id_usuario + ")");
        System.out.println("rol: " + rol);
        System.out.println("numero de reservas: " + reservas.size());
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    @JsonCreator
    public Usuario(@JsonProperty("id_usuario") Long id_usuario,
                   @JsonProperty("nombre") String nombre,
                   @JsonProperty("contrasena") String contrasena,
                   @JsonProperty("rol") Roles rol,
                   @JsonProperty("reservas") List<Reserva> reservas) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
        this.reservas = reservas;
    }
}
