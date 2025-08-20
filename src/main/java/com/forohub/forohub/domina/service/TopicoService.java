package com.forohub.forohub.domina.service;


import com.forohub.forohub.domina.modelo.Topico;
import com.forohub.forohub.domina.topico.DatosActualizacionTopico;
import com.forohub.forohub.domina.topico.DatosListadoTopico;
import com.forohub.forohub.domina.topico.DatosRegistroTopico;
import com.forohub.forohub.domina.topico.TopicoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicoService {
    @Autowired
    private TopicoRespository topicoRespository;

    public Topico registrarTopico(DatosRegistroTopico datos){
        // Verificar si ya existe un topico con el mismo mensaje y titulo
        if(topicoRespository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())){
            throw new IllegalArgumentException("Ya existe un tópico con el mismo título y mensaje");
        }

        //crear y guardar el nuevo topico
        Topico topico = new Topico(
                datos.titulo(),
                datos.mensaje(),
                datos.autor(),
                datos.curso()
        );

        return topicoRespository.save(topico);
    }
    //Metodo para obtener un topico por id
    public Optional<Topico> getTopicoPorId(Long id){
        return topicoRespository.findById(id);
    }

    // ====METODO DE ACTUALIZACION ====

    public  Optional<Topico> actualizarTopico(Long id, DatosActualizacionTopico datos){
        // 1. buscar por su ID
        Optional<Topico> topicoOptional = topicoRespository.findById(id);
        // 2. si existe imprenta la siguente regla
        if(topicoOptional.isPresent()){
            Topico topico = topicoOptional.get();

        // 3. Regla de negocio: Evitar duplicados, pero permite actualizar el topico
        if (datos.titulo() != null && datos.mensaje() != null) {
            boolean existeOtroTopico = topicoRespository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
            // compara con los datos del topico originnal para permitir la auto-actualizacion
            if (existeOtroTopico && (!topico.getTitulo().equals(datos.titulo()) || !topico.getMensaje().equals(datos.mensaje()))) {
                throw new IllegalArgumentException("Ya existe otro tópico con el mismo título y mensaje.");
            }
        }
        // 4. Actualiza solo los campos que no son nulos en el DTO de actualizacion
            if (datos.titulo() != null){
                topico.setTitulo(datos.titulo());
            }
            if (datos.mensaje() != null){
                topico.setMensaje(datos.mensaje());
            }
            if (datos.autor() != null){
                topico.setAutor(datos.autor());
            }
            if(datos.curso() != null){
                topico.setCurso(datos.curso());
            }
            if (datos.status() != null){
                topico.setStatus(datos.status());
            }

        // 5. Guarda el topico actualizado en la base de datos
        return Optional.of(topicoRespository.save(topico));
        }
        // 6. si el topico no se encuentra, devuelve un Optional vacio
        return Optional.empty();
    }

    // ====== METODO PARA ELIMINAR TOPICOS ====
    public boolean eliminarTopico(Long id){
        // 1. buscar el topico por ID y verificar su existencia
        if(topicoRespository.existsById(id)){
            // 2. si existe, lo elimina
            topicoRespository.deleteById(id);
            return true; // Indica q la eliminacion fue exitosa
        }
        // 3. Si no existe, devuelve falso
        return false;
    }

    //=====MOSTRAR TOPICOS POR LISTA Y BUSQUEDA EXPECIFICA=====

    //Listar todos los topicos
    public List<DatosListadoTopico> listarTopicos(){
        return topicoRespository.findAll().stream()
                .map(DatosListadoTopico::new)
                .collect(Collectors.toList());
    }

    //Listar con paginacion

    public Page<DatosListadoTopico> listarConPaginacion(Pageable pageable){
        return topicoRespository.findAll(pageable)
                .map(DatosListadoTopico::new);
    }

    //Primeros 10 ordenados por fecha ascendente

    public List<DatosListadoTopico> listarPrimeros10(){
        return topicoRespository.findFirst10ByOrderByFechaCreacionAsc().stream()
                .map(DatosListadoTopico::new)
                .collect(Collectors.toList());
    }

    //Buscar por curso

    public List<DatosListadoTopico> listarPorCurso(String curso){
        return topicoRespository.findByCurso(curso).stream()
                .map(DatosListadoTopico::new)
                .collect(Collectors.toList());
    }

    //Buscar por curso y año

    public List<DatosListadoTopico> listarPorCursoYYear(String curso, int year){
        return topicoRespository.findByCursoAndYear(curso, year).stream()
                .map(DatosListadoTopico::new)
                .collect(Collectors.toList());
    }

    //Busqueda con filtros opcionales
    public List<DatosListadoTopico> buscarConFiltros(String curso, Integer year){
        return topicoRespository.findByOptionalFilters(curso, year).stream()
                .map(DatosListadoTopico::new)
                .collect(Collectors.toList());
    }
}
