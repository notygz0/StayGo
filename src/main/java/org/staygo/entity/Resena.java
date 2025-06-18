package org.staygo.entity;

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
public class Resena implements Serializable{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(name = "texto", length = 1000)
    private String texto;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "puntuacion")
    private float puntuacion;

    public boolean esHotel() {
        return hotel != null;
    }

    public boolean esDepartamento() {
        return departamento != null;
    }

    public Object getAlojamiento() {
        if (hotel != null) return hotel;
        if (departamento != null) return departamento;
        return null;
    }
}