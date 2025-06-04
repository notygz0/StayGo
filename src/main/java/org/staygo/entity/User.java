package org.staygo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un usuario en el sistema.
 * Esta clase contiene los atributos y métodos necesarios para gestionar
 * un usuario dentro de la aplicación.
 *
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */
public class User {
    private Long idUsuario;
    private String nombre;
    private String contrasena;
    private Role rol;
    private List<Reserva> reservas;
    private List<Pago> pagos;  // Nueva lista para pagos

    /**
     * Constructor que inicializa un nuevo usuario con los valores proporcionados.
     *
     * @param idUsuario el identificador del usuario.
     * @param nombre el nombre del usuario.
     * @param contrasena la contraseña del usuario.
     * @param rol el rol asignado al usuario.
     */
    public User(Long idUsuario, String nombre, String contrasena, Role rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
        this.reservas = new ArrayList<>();
        this.pagos = new ArrayList<>();  // Inicializar lista de pagos
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return el nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return el rol del usuario.
     */
    public Role getRol() {
        return rol;
    }

    /**
     * Inicia sesión con el nombre de usuario y la contraseña proporcionados.
     * Verifica si el nombre de usuario y la contraseña coinciden con los del usuario.
     *
     * @param nombre el nombre de usuario a verificar.
     * @param contrasena la contraseña a verificar.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean iniciarSesion(String nombre, String contrasena) {
        return this.nombre.equals(nombre) && this.contrasena.equals(contrasena);
    }

    /**
     * Realiza una reserva y la agrega a la lista de reservas del usuario.
     * Solo los usuarios con rol CLIENTE pueden realizar reservas.
     *
     * @param reserva la reserva a agregar.
     * @throws IllegalStateException si el usuario no es un CLIENTE.
     */
    public void realizarReserva(Reserva reserva) {
        if (rol == Role.CLIENTE) {
            reservas.add(reserva);
        } else {
            throw new IllegalStateException("solo los clientes pueden realizar reservas");
        }
    }

    /**
     * Obtiene la lista de reservas del usuario.
     *
     * @return la lista de reservas del usuario.
     */
    public List<Reserva> obtenerReservas() {
        return reservas;
    }

    /**
     * Obtiene la lista de pagos realizados por el usuario.
     *
     * @return la lista de pagos.
     */
    public List<Pago> getPagos() {
        return pagos;
    }

    /**
     * Agrega un pago a la lista de pagos del usuario.
     *
     * @param pago el pago a agregar.
     */
    public void agregarPago(Pago pago) {
        pagos.add(pago);
    }

    /**
     * Constructor para la deserialización de un usuario desde un archivo JSON.
     * Este constructor es utilizado por Jackson para mapear un objeto JSON a un objeto Usuario.
     *
     * @param idUsuario el identificador del usuario.
     * @param nombre el nombre del usuario.
     * @param contrasena la contraseña del usuario.
     * @param rol el rol asignado al usuario.
     * @param reservas la lista de reservas del usuario.
     * @param pagos la lista de pagos del usuario.
     */
    @JsonCreator
    public User(@JsonProperty("idUsuario") Long idUsuario,
                   @JsonProperty("nombre") String nombre,
                   @JsonProperty("contrasena") String contrasena,
                   @JsonProperty("rol") Role rol,
                   @JsonProperty("reservas") List<Reserva> reservas,
                   @JsonProperty("pagos") List<Pago> pagos) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
        this.reservas = reservas != null ? reservas : new ArrayList<>();
        this.pagos = pagos != null ? pagos : new ArrayList<>();
    }
}
