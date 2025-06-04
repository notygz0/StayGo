import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entidad que representa una reseña dejada por un usuario sobre un alojamiento.
 * Contiene información sobre el usuario, el alojamiento, el texto de la reseña,
 * la fecha de creación y la puntuación asignada.
 * 
 * Implementa Serializable para facilitar la gestión en JPA.
 * 
 * @author Felipe Delgado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resena")
public class Resena implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "alojamiento_id", nullable = false)
    private Alojamiento alojamiento;

    @Column(name = "texto", length = 1000)
    private String texto;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "puntuacion")
    private float puntuacion;
}