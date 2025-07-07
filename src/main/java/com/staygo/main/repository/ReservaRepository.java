package com.staygo.main.repository;

import com.staygo.main.entity.Departamento;
import com.staygo.main.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    // Para buscar todas las reservas de un usuario
    @Query("SELECT r FROM Reserva r WHERE r.user.id = :userId")
    List<Reserva> findAllByUsuarioId(@Param("userId") Integer userId);
    // Para reservas de departamento
    @Query("SELECT r FROM Reserva r WHERE r.user.id = :userId AND r.departamento.id = :departamentoId")
    Optional<Reserva> findByUsuarioIdAndDepartamentoId(@Param("userId") Integer userId, @Param("departamentoId") Integer departamentoId);

    // Para reservas de hotel
    @Query("SELECT r FROM Reserva r WHERE r.user.id = :userId AND r.hotel.id = :hotelId")
    Optional<Reserva> findByUsuarioIdAndHotelId(@Param("userId") Integer userId, @Param("hotelId") Integer hotelId);

    // Para reservas de departamento
    @Query("SELECT r FROM Reserva r WHERE r.departamento.id = :departamentoId")
    List<Reserva> findAllByDepartamentoId(@Param("departamentoId") Integer departamentoId);

    // Para reservas de hotel
    @Query("SELECT r FROM Reserva r WHERE r.hotel.id = :hotelId")
    List<Reserva> findAllByHotelId(@Param("hotelId") Integer hotelId);

    @Query("SELECT r FROM Reserva r WHERE r.departamento.id = :departamentoId")
    Optional<Reserva> findReservaByDepartamentoId(@Param("departamentoId") Integer departamentoId);
}