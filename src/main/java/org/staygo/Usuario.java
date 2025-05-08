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

    public boolean esAdmin() {
        return rol == Roles.ADMIN;
    }

    public boolean esArrendatario() {
        return rol == Roles.ARRENDATARIO;
    }

    public boolean esCliente() {
        return rol == Roles.CLIENTE;
    }

    public boolean iniciarSesion(String nombre, String contrasena) {
        return this.nombre.equals(nombre) && this.contrasena.equals(contrasena);
    }

    public void realizarReserva(Reserva reserva) {
        if (rol == Roles.CLIENTE) {
            reservas.add(reserva);
        } else {
            throw new IllegalStateException("solo los clientes pueden realizar reservas");
        }
    }

    public List<Reserva> obtenerReservas() {
        return reservas;
    }

    public void publicarAlojamiento(Alojamiento alojamiento) {
        if (rol == Roles.ARRENDATARIO) {
        } else {
            throw new IllegalStateException("solo los arrendatarios pueden publicar alojamientos");
        }
    }

    public void gestionarSistema() {
        if (rol == Roles.ADMIN) {
        } else {
            throw new IllegalStateException("solo los administradores pueden gestionar el sistema");
        }
    }

    public void mostrarDetalles() {
        System.out.println("usuario: " + nombre + " (id: " + id_usuario + ")");
        System.out.println("rol: " + rol);
        System.out.println("numero de reservas: " + reservas.size());
    }

    public Long getIdUsuario() {
        return id_usuario;
    }

    @JsonCreator
    public Usuario(@JsonProperty("idUsuario") Long idUsuario,
                   @JsonProperty("nombre") String nombre,
                   @JsonProperty("contrasena") String contrasena,
                   @JsonProperty("rol") Roles rol,
                   @JsonProperty("reservas") List<Reserva> reservas) {
        this.id_usuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
        this.reservas = reservas;
    }
}
