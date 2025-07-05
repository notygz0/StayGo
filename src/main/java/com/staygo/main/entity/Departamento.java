package com.staygo.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * clase que representa un departamento dentro del sistema de gestion de alojamientos.* Un departamento tiene un dueño, un nombre, un precio, una descripción,
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
public class Departamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private float precio;
    private String descripcion;
    @Lob
    private byte[] imagen;
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




