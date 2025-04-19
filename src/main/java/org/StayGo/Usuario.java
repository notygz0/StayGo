package org.StayGo;

import java.util.List;

public class Usuario {

    private Long id_usuario;
    private String nombre;
    private String contrasena;
    private Reserva reserva;
    private Roles rol;

    public Usuario() {
        // TODO - implement Usuario.Usuario
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param nombre
     * @param contrasena
     */
    public boolean iniciarSesion(String nombre, String contrasena) {
        // TODO - implement Usuario.iniciarSesion
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param reserva
     */
    public void realizarReserva(Reserva reserva) {
        // TODO - implement Usuario.realizarReserva
        throw new UnsupportedOperationException();
    }

    public List<Reserva> verReservas() {
        // TODO - implement Usuario.verReservas
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param resena
     */
    public void crearResena(Resena resena) {
        // TODO - implement Usuario.crearResena
        throw new UnsupportedOperationException();
    }

}