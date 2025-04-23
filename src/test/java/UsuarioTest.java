import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.staygo.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    private Usuario usuario;

    @BeforeEach
    void setUp(){
        usuario = new Usuario(1L, "felipe", "1245!!", Roles.CLIENTE);
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
        Alojamiento depto = new Departamento("av caupolican 3622", 5000, "vip", 2, true, usuario);
        Reserva reserva = new Reserva(usuario, depto, LocalDate.now(), LocalDate.now().plusDays(1));

        usuario.realizarReserva(reserva);
        List<Reserva> reservas = usuario.obtenerReservas();

        assertEquals(1, reservas.size());
        assertSame(reserva, reservas.get(0));
    }
}
