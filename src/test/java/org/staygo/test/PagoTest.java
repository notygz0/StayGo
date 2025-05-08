package org.staygo.test;


import org.junit.jupiter.api.Test;
import org.staygo.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * clase de prueba para la clase Pago.
 * esta clase contiene pruebas unitarias para verificar la correcta creacion y el correcto
 * funcionamiento del metodo realizarPago de la clase Pago.
 *
 * @author Felipe Delgado
 */

class PagoTest {

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
