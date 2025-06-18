package org.staygo.repository;

import org.staygo.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
    @Query("SELECT d FROM Departamento d WHERE (:nombre IS NULL OR d.nombre LIKE %:nombre%) AND (:ocupado IS NULL OR d.ocupado = :ocupado) AND (:numHabitaciones IS NULL OR d.numHabitaciones = :numHabitaciones) AND (:duenoId IS NULL OR d.dueno.id = :duenoId)")
    List<Departamento> findByFiltros(
            @Param("nombre") String nombre,
            @Param("ocupado") Boolean ocupado,
            @Param("numHabitaciones") Integer numHabitaciones,
            @Param("duenoId") Integer duenoId
    );
}

