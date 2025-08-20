package com.forohub.forohub.controller;

import com.forohub.forohub.domina.modelo.Topico;
import com.forohub.forohub.domina.topico.DatosActualizacionTopico;
import com.forohub.forohub.domina.topico.DatosListadoTopico;
import com.forohub.forohub.domina.topico.DatosRegistroTopico;
import com.forohub.forohub.domina.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    //Post registrar nuevo topico
    @PostMapping
    public ResponseEntity<?> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            UriComponentsBuilder uriBuider) {
        try {
            Topico topico = topicoService.registrarTopico(datos);

            URI url = uriBuider.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

            return ResponseEntity.created(url).body(topico);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //GET metodo para endpoint de consulta por ID
    @GetMapping("/{id}")
    public  ResponseEntity<Topico> obtenerTopicoPorId(@PathVariable Long id){
        //LOGICA DE BUSQUEDA
        Optional<Topico> topico = topicoService.getTopicoPorId(id);
        //valida si el topico existe y retorna la respues adecuada
        if (topico.isPresent()){
            return ResponseEntity.ok(topico.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT: Actualizar un topico
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos){
        try {
            Optional<Topico> topicoActualizado = topicoService.actualizarTopico(id, datos);
            if (topicoActualizado.isPresent()){
                return ResponseEntity.ok(topicoActualizado.get());
            } else{
                return ResponseEntity.notFound().build();
            }
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE: METODO PARA LA ELIMINACION DE TIPICO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id){
        boolean eliminado = topicoService.eliminarTopico(id);
        if (eliminado){
            // Si la eliminacion fue exitosa, devuelve un 204 No Content
            return ResponseEntity.noContent().build();
        }else {
            //si el topico no existe, devuelve un 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    //GET - listar todos lod topicos (REQUERIDO)
    @GetMapping
    public ResponseEntity<List<DatosListadoTopico>> listarTopico() {
        return ResponseEntity.ok(topicoService.listarTopicos());
    }

    // GET - Opcionales

    @GetMapping("/paginados")
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicosPaginados(
            @PageableDefault(size = 10, sort = "fechaCreacion")
            Pageable pageable) {
        return ResponseEntity.ok(topicoService.listarConPaginacion(pageable));
    }

    // GET - Primeros ordenados por fecha

    @GetMapping("/recientes")
    public ResponseEntity<List<DatosListadoTopico>> listarRecientes(){
        return  ResponseEntity.ok(topicoService.listarPrimeros10());
    }

    //Buscar por curso

    @GetMapping("/curso/{curso}")
    public ResponseEntity<List<DatosListadoTopico>> listarCurso(@PathVariable String curso){
        return  ResponseEntity.ok(topicoService.listarPorCurso(curso));
    }

    //Buscar por curso y año

    @GetMapping("/buscar")
    public  ResponseEntity<List<DatosListadoTopico>> buscarPorCursoYYear(
            @RequestParam String curso,
            @RequestParam int year){
        return ResponseEntity.ok(topicoService.listarPorCursoYYear(curso, year));
    }

    // Buscar con filtros opcionales

    @GetMapping("/filtros")
    public ResponseEntity<List<DatosListadoTopico>> buscarConFiltros(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer year) {
                return ResponseEntity.ok(topicoService.buscarConFiltros(curso, year));
    }


}
