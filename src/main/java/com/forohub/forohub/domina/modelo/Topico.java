package com.forohub.forohub.domina.modelo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String titulo;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(nullable = false, length = 64, name = "mensaje_hash")
    private String mensajeHash;


    @Column(nullable = false, name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTopico status = StatusTopico.NO_RESPONDIDO;

    @NotBlank
    @Column(nullable = false)
    private String autor;

    @NotBlank
    @Column(nullable = false)
    private String curso;

    //Contructor para crear nuevos Topicos

    public Topico(String titulo, String mensaje, String autor, String curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
        this.mensajeHash = calcularHashMensaje(mensaje);
    }

    //Metodo para calcular el mensaje Hash del mensaje (para evitar duplicacos)
    private String calcularHashMensaje(String mensaje) {
        String contenidoCompleto = titulo + "|" + mensaje + "|"  + autor;
        //Implementacion simple de hash - puede mejorarse segun las necesidades
        return Integer.toString(mensaje.hashCode());
    }

}
