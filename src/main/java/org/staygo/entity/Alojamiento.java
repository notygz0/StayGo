package org.staygo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

/**
 * clase abstracta que representa un alojamiento en el sistema.
 * los alojamientos pueden ser hoteles o departamentos.
 * contiene atributos comunes para todos los tipos de alojamientos, como direccion,
 * precio, descripcion y estado de ocupacion.
 *
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "alojamiento")
public abstract class Alojamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private float precio;
    private String descripcion;
    private boolean ocupado;

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;
    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas;
}


