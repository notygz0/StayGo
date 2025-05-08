package org.staygo.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.staygo.Alojamiento;
import org.staygo.Departamento;
import org.staygo.Roles;
import org.staygo.Usuario;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AlojamientoTest {

    private Alojamiento alojamiento;
    private Usuario arrendatario;
    private LocalDate hoy;

    @BeforeEach
    void setUp() {
        hoy = LocalDate.now();
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

        // Marcar el alojamiento como ocupado
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
        assertTrue(detalles.contains("Moderno: true"));
        assertTrue(detalles.contains("Dueño: arrendatario"));
    }
}



