package com.staygo.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserva")
public class Reserva {
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

    @Column(name = "fecha_inicio")
    private LocalDate fecha_inicio;

    @Column(name = "fecha_final")
    private LocalDate fecha_final;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva = EstadoReserva.PENDIENTE;

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