package org.staygo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un usuario en el sistema.
 * Esta clase contiene los atributos y métodos necesarios para gestionar
 * un usuario dentro de la aplicación.
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */

public class Usuario {
    private Long idUsuario;
    private String nombre;
    private String contrasena;
    private Roles rol;
    private List<Reserva> reservas;

    public Usuario(Long idUsuario, String nombre, String contrasena, Roles rol) {
        this.idUsuario = idUsuario;
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
        if (rol == Roles.CLIENTE) {
            reservas.add(reserva);
        } else {
            throw new IllegalStateException("solo los clientes pueden realizar reservas");
        }
    }

    public List<Reserva> obtenerReservas() {
        return reservas;
    }

    @JsonCreator
    public Usuario(@JsonProperty("idUsuario") Long idUsuario,
                   @JsonProperty("nombre") String nombre,
                   @JsonProperty("contrasena") String contrasena,
                   @JsonProperty("rol") Roles rol,
                   @JsonProperty("reservas") List<Reserva> reservas) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
        this.reservas = reservas;
    }
}
