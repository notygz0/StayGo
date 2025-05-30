package com.staygo.main.repository;

import com.staygo.main.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("SELECT r FROM Reserva r WHERE r.user.id = :userId AND r.alojamiento.id = :alojamientoId")
    Optional<Reserva> findByUsuarioIdAndAlojamientoId(@Param("userId") Long userId, @Param("alojamientoId") Long alojamientoId);
}
