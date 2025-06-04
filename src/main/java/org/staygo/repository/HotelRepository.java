package org.staygo.repository;

import org.staygo.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT h FROM Hotel h WHERE (:nombre IS NULL OR h.nombre LIKE %:nombre%) AND (:ocupado IS NULL OR h.ocupado = :ocupado) AND (:numEstrellas IS NULL OR h.numEstrellas = :numEstrellas) AND (:numHabitaciones IS NULL OR h.numHabitaciones = :numHabitaciones)")
    List<Hotel> findByFiltros(
            @Param("nombre") String nombre,
            @Param("ocupado") Boolean ocupado,
            @Param("numEstrellas") Integer numEstrellas,
            @Param("numHabitaciones") Integer numHabitaciones
    );

}
