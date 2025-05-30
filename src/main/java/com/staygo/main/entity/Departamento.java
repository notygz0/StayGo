package com.staygo.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="departamento")
public class Departamento extends Alojamiento{
    @ManyToOne
    @JoinColumn(name = "dueno_id", nullable = false)
    private User dueno;
    private int numHabitaciones;
}
