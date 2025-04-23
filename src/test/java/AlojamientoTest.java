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


        alojamiento = new Departamento("Calle Falsa 123", 15000f, "Departamento cómodo", 3, true, arrendatario);
    }

    @Test
    void testCreacionAlojamiento() {

        assertEquals("Calle Falsa 123", alojamiento.getDireccion());
        assertEquals(15000f, alojamiento.getPrecio());
        assertEquals("Departamento cómodo", alojamiento.getDescripcion());
        assertFalse(alojamiento.isOcupado());  // Inicialmente no ocupado


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
        assertTrue(detalles.contains("Calle Falsa 123"));
        assertTrue(detalles.contains("15000.0"));
        assertTrue(detalles.contains("Departamento cómodo"));
        assertTrue(detalles.contains("arrendatario"));
    }
}


