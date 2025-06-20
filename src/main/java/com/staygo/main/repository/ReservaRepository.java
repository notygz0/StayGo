package com.staygo.main.repository;

import com.staygo.main.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Para reservas de departamento
    @Query("SELECT r FROM Reserva r WHERE r.user.id = :userId AND r.departamento.id = :departamentoId")
    Optional<Reserva> findByUsuarioIdAndDepartamentoId(@Param("userId") Long userId, @Param("departamentoId") Long departamentoId);

    // Para reservas de hotel
    @Query("SELECT r FROM Reserva r WHERE r.user.id = :userId AND r.hotel.id = :hotelId")
    Optional<Reserva> findByUsuarioIdAndHotelId(@Param("userId") Long userId, @Param("hotelId") Long hotelId);
}
