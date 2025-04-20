import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.staygo.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaTest {
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        Usuario l = new Usuario(4354L, "lorenzo", "lorencito1234", Roles.CLIENTE);
        Alojamiento d = new Departamento("av arturo prat", 25000f, "departamento amplio", 1, false);
        reserva = new Reserva(l, d, LocalDate.now(), LocalDate.now().plusDays(1));
    }
    @Test
    void testEstadoInicial (){
        assertEquals("pendiente", reserva.getEstadoReserva());
    }
    @Test
    void testConfirmarReserva() {
        assertTrue(reserva.confirmarReserva());
        assertEquals("confirmada", reserva.getEstadoReserva());
        assertFalse(reserva.confirmarReserva());
    }
}
