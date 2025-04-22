

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.staygo.Alojamiento;
import org.staygo.Departamento;
import org.staygo.Hotel;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlojamientoTest {

    private Alojamiento alojamiento;

    private LocalDate hoy;

    @BeforeEach
    void setUp() {
        hoy = LocalDate.now();
        // Crear un Alojamiento de tipo Departamento
        alojamiento = new Departamento("Calle Falsa 123", 15000f, "Departamento cómodo", 3, true);
    }

    @Test
    void testCreacionAlojamiento() {
        // Verificar que los detalles del alojamiento se inicialicen correctamente
        assertEquals("Calle Falsa 123", alojamiento.getDireccion());
        assertEquals(15000f, alojamiento.getPrecio());
        assertEquals("Departamento cómodo", alojamiento.getDescripcion());
        assertFalse(alojamiento.isOcupado());  // Inicialmente no ocupado
    }

    @Test
    void testDisponibilidad() {
        // Verificar si el alojamiento está disponible (es decir, no está ocupado)
        assertFalse(alojamiento.isOcupado());  // El alojamiento no debe estar ocupado

        // Marcar el alojamiento como ocupado
        alojamiento.setOcupado(true);
        assertTrue(alojamiento.isOcupado());  // Ahora el alojamiento debe estar ocupado
    }


    @Test
    void testVerDetalles() {
        // Verificar que el método `verDetalles()` del Departamento devuelve la información correcta
        String detalles = alojamiento.verDetalles();
        assertTrue(detalles.contains("Calle Falsa 123"));
        assertTrue(detalles.contains("15000.0"));
        assertTrue(detalles.contains("Departamento cómodo"));
    }
}

