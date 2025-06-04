package org.staygo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
/**
 * clase que representa un departamento dentro del sistema de gestion de alojamientos.
 * esta clase extiende la clase {@link Alojamiento} y agrega atributos especificos relacionados
 * con un departamento, como el numero de habitaciones y el dueno del departamento.
 *
 * @author Lorenzo Lopez
 * @author Felipe Delgado
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="departamento")
public class Departamento extends Alojamiento {
    @ManyToOne
    @JoinColumn(name = "dueno_id", nullable = false)
    private User dueno;
    private int numHabitaciones;
}



