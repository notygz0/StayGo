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
        alojamiento = new Departamento("av arturo prat", 25000f, "departamento amplio", 1, false);
        // No es necesario pasar EstadoReserva ya que es asignado por defecto
        reserva = new Reserva(usuario, alojamiento, hoy, hoy.plusDays(1));
    }

    @Test
    void testEstadoInicial() {
        assertEquals("pendiente", reserva.getEstadoReserva());
    }

    @Test
    void testConfirmarReserva() {
        assertTrue(reserva.confirmarReserva());
        assertEquals("confirmado", reserva.getEstadoReserva());
        assertFalse(reserva.confirmarReserva());
    }

    @Test
    void testCancelarReserva() {
        assertTrue(reserva.cancelarReserva());
        assertEquals("cancelada", reserva.getEstadoReserva());
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

