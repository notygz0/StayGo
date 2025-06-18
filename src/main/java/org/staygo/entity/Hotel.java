package org.staygo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
/**
 * Entidad que representa un hotel en el sistema
 * Hereda de Alojamiento e incluye número de estrellas y habitaciones
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private float precio;
    private String descripcion;
    private boolean ocupado;

    private int numEstrellas;
    private int numHabitaciones;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas;
}
