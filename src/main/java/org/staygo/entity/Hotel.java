package org.staygo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name="Hotel")
public class Hotel extends Alojamiento {
    private int numEstrellas;
    private int numHabitaciones;
}
