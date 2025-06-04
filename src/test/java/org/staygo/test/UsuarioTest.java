package org.staygo.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.staygo.*;
import org.staygo.entity.Alojamiento;
import org.staygo.entity.Departamento;
import org.staygo.entity.Reserva;
import org.staygo.entity.Role;
import org.staygo.entity.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * clase de prueba para la clase Usuario.
 * esta clase contiene pruebas unitarias para verificar el correcto funcionamiento
 * del metodo iniciarSesion, la validacion de credenciales y la gestion de reservas
 * realizadas por el usuario.
 *
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */

class UsuarioTest {
    private User usuario;

    @BeforeEach
    void setUp(){
        usuario = new User(1L, "felipe", "1245!!", Role.CLIENTE);
    }
    @Test
    void testIniciarSesion_correcto() {
        assertTrue(usuario.iniciarSesion("felipe", "1245!!"));
    }
    @Test
    void testIniciarSesion_incorrecto() {
        assertFalse(usuario.iniciarSesion("felipe", "1244!!"));
        assertFalse(usuario.iniciarSesion("lorenzo", "1285!!!"));
    }
    @Test
    void testRealizarYObtenerReservas() {
        Alojamiento depto = new Departamento("av caupolican 3622", 5000, "vip", 2, usuario);
        Reserva reserva = new Reserva(usuario, depto, LocalDate.now(), LocalDate.now().plusDays(1));

        usuario.realizarReserva(reserva);
        List<Reserva> reservas = usuario.obtenerReservas();

        assertEquals(1, reservas.size());
        assertSame(reserva, reservas.get(0));
    }
}
