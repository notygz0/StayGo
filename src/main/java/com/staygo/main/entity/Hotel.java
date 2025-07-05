package com.staygo.main.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
/**
 * Entidad que representa un hotel en el sistema
 * contiene información sobre el nombre, precio, descripción,
 * ocupación, número de estrellas, número de habitaciones,
 * reservas y reseñas asociadas al hotel.
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="hotel")
public class Hotel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private float precio;
    private String descripcion;
    @Lob
    private byte[] imagen;
    private boolean ocupado;

    private int numEstrellas;
    private int numHabitaciones;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas;
}
