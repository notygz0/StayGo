package org.staygo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * clase que representa un departamento dentro del sistema de gestion de alojamientos.
 * esta clase extiende la clase {@link Alojamiento} y agrega atributos especificos relacionados
 * con un departamento, como el numero de habitaciones y el dueno del departamento.
 *
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="departamento")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private float precio;
    private String descripcion;
    private boolean ocupado;

    @ManyToOne
    @JoinColumn(name = "dueno_id", nullable = false)
    private User dueno;

    private int numHabitaciones;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas;
}




