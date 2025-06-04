package org.staygo.repository;

import org.staygo.entity.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlojamientoRepository extends JpaRepository<Alojamiento, Long> {
    @Query("SELECT a FROM Alojamiento a WHERE TYPE(a) = Hotel")
    List<Alojamiento> findAllHoteles();

    @Query("SELECT a FROM Alojamiento a WHERE TYPE(a) = Departamento")
    List<Alojamiento> findAllDepartamentos();
}
