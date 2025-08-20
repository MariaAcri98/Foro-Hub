package com.forohub.forohub.domina.topico;

import com.forohub.forohub.domina.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRespository extends JpaRepository<Topico, Long> {

    //Metodo basico para encontrar todos
    List<Topico> findAll();

    //Metodo para encontrar todos con paginacion
    Page<Topico> findAll(Pageable pageable);

    //Primeros 10 ordenados por fehca ascendente
    List <Topico> findFirst10ByOrderByFechaCreacionAsc();

    //metodo para buscar por curso
    List<Topico> findByCurso(String curso);

    //Metodo para buscar por curso y año especifico
    @Query("SELECT t FROM Topico t WHERE t.curso = :curso AND YEAR(t.fechaCreacion) = :year")
    List<Topico> findByCursoAndYear(@Param("curso") String curso, @Param("year") int year);

    // buscar con criterios opcionales
    @Query("SELECT t FROM Topico t WHERE " +
    "(:curso IS NULL OR t.curso = :curso) AND " +
    "(:year IS NULL OR YEAR(t.fechaCreacion) = :year)")
    List<Topico> findByOptionalFilters(@Param("curso") String curso, @Param("year") int year);

    //Evitar duplicados
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
