package org.staygo.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.staygo.entity.Alojamiento;
import org.staygo.entity.Departamento;
import org.staygo.entity.Roles;
import org.staygo.entity.Usuario;

import static org.junit.jupiter.api.Assertions.*;

/**
 * clase de prueba para la clase Alojamiento.
 * esta clase contiene pruebas unitarias para verificar la correcta creacion,
 * disponibilidad y detalles de los alojamientos.
 *
 * @author Lorenzo Lopez
 */

class AlojamientoTest {



    private Alojamiento alojamiento;
    private Usuario arrendatario;


    @BeforeEach
    void setUp() {
        arrendatario = new Usuario(1L, "arrendatario", "contraseña", Roles.ARRENDATARIO);
        alojamiento = new Departamento("Calle Falsa 123", 15000f, "Departamento comodo", 3, arrendatario);
    }

    @Test
    void testCreacionAlojamiento() {
        assertEquals("Calle Falsa 123", alojamiento.getDireccion());
        assertEquals(15000f, alojamiento.getPrecio(), 0.01f);
        assertEquals("Departamento comodo", alojamiento.getDescripcion());
        assertFalse(alojamiento.isOcupado());
        assertEquals("arrendatario", alojamiento.getDueno().getNombre());
    }

    @Test
    void testDisponibilidad() {
        assertFalse(alojamiento.isOcupado());

        alojamiento.setOcupado(true);
        assertTrue(alojamiento.isOcupado());
    }

    @Test
    void testVerDetalles() {
        String detalles = alojamiento.verDetalles();

        System.out.println("Detalles del alojamiento: " + detalles);
        assertTrue(detalles.contains("Departamento:"));
        assertTrue(detalles.contains("Calle Falsa 123"));
        assertTrue(detalles.contains("Precio: 15000.0"));
        assertTrue(detalles.contains("Habitaciones: 3"));
        assertTrue(detalles.contains("Dueño: arrendatario"));
    }
}



