
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.staygo.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ResenaTest {

    private Resena resena;
    private Usuario usuario;
    private Alojamiento alojamiento;
    private LocalDateTime fechaCreacion;

    @BeforeEach
    void setUp() {
        // Inicialización de los datos para la prueba
        usuario = new Usuario(1L, "Juan Perez", "password", Roles.CLIENTE);
        alojamiento = new Departamento("Calle Falsa 123", 100.0f, "Departamento confortable", 2, true, usuario);
        fechaCreacion = LocalDateTime.now();

        // Crear una reseña válida
        resena = new Resena(usuario, alojamiento, "Excelente lugar", fechaCreacion, 4.5f);
    }

    @Test
    void testCalificacionValida() {

        assertEquals(4.5f, resena.getCalificacion());
    }

    @Test
    void testCalificacionInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            resena.setCalificacion(6.0f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            resena.setCalificacion(0.5f);
        });
    }

    @Test
    void testEditarResena() {

        String nuevoTexto = "Nuevo texto de reseña";
        resena.editarResena(nuevoTexto);
        assertEquals(nuevoTexto, resena.getTexto());
    }

    @Test
    void testObtenerDetallesResena() {
        String detallesEsperados = String.format(
                "resena de %s sobre %s%n" +
                        "fecha: %s%n" +
                        "calificacion: %.1f/5.0%n" +
                        "texto: %s",
                usuario.getNombre(),
                alojamiento.verDetalles(),
                fechaCreacion,
                resena.getCalificacion(),
                resena.getTexto()
        );
        assertEquals(detallesEsperados, resena.obtenerDetallesResena());
    }

    @Test
    void testCalificacionLimites() {
        resena.setCalificacion(5.0f); // Calificación máxima
        assertEquals(5.0f, resena.getCalificacion());

        resena.setCalificacion(1.0f); // Calificación mínima
        assertEquals(1.0f, resena.getCalificacion());
    }
}

