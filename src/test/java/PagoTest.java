import org.junit.jupiter.api.Test;
import org.staygo.*;

import static org.junit.jupiter.api.Assertions.*;

public class PagoTest {

    @Test
    void testCrearPagoValido() {
        Usuario u = new Usuario(132L, "felipe", "felipe123", Roles.CLIENTE);
        Pago p = Pago.crearPago(u, 1000);
        assertNotNull(p);
        assertEquals(1000f, p.getPrecio(), 0.001f);
        assertFalse(p.isEstadoPago());
        assertNull(p.getFechaPago());
    }
    @Test
    void testRealizarPago() {
        Pago p = Pago.crearPago(new Usuario(1L, "x", "pw", Roles.CLIENTE), 500f);
        assertTrue(p.realizarPago());
        assertTrue(p.isEstadoPago());
        assertNotNull(p.getFechaPago());
        // no se permitir pagar 2 veces
        assertFalse(p.realizarPago());
    }
}
