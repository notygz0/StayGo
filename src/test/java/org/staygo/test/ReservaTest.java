package org.staygo.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.staygo.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaTest {
    private Reserva reserva;
    private Usuario usuario;
    private Alojamiento alojamiento;
    private LocalDate hoy;

    @BeforeEach
    void setUp() {
        hoy = LocalDate.now();
        usuario = new Usuario(4354L, "lorenzo", "lorencito1234", Roles.CLIENTE);
        alojamiento = new Departamento("av arturo prat", 25000, "departamento amplio", 1, usuario);
        reserva = new Reserva(usuario, alojamiento, hoy, hoy.plusDays(1));
    }

    @Test
    void testEstadoInicial() {
        assertEquals("PENDIENTE", reserva.getEstadoReserva().name()); // Usar name() si es un enum !!! lorenzo
    }

    @Test
    void testConfirmarReserva() {
        assertTrue(reserva.confirmarReserva());
        assertEquals("CONFIRMADO", reserva.getEstadoReserva().name());
    }

    @Test
    void testCancelarReserva() {
        assertTrue(reserva.cancelarReserva());
        assertEquals("CANCELADA", reserva.getEstadoReserva().name());
        assertFalse(reserva.cancelarReserva());
    }

    @Test
    void noPermitirFechaFinAntesDeInicio() {
        LocalDate inicio = hoy.plusDays(3);
        LocalDate fin = hoy.plusDays(2);
        assertThrows(IllegalArgumentException.class, () -> {
            new Reserva(usuario, alojamiento, inicio, fin);
        });
    }

    @Test
    void noPermiteFechasIguales() {
        LocalDate fecha = hoy.plusDays(1);
        assertThrows(IllegalArgumentException.class, () -> {
            new Reserva(usuario, alojamiento, fecha, fecha);
        });
    }

    @Test
    void noPermiteFechaInicioEnElPasado() {
        LocalDate ayer = hoy.minusDays(1);
        assertThrows(IllegalArgumentException.class, () -> {
            new Reserva(usuario, alojamiento, ayer, hoy);
        });
    }
}


