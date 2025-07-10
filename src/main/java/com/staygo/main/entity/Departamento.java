package com.staygo.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private String direccion;
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
