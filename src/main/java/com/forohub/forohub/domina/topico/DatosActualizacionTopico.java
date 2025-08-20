package com.forohub.forohub.domina.topico;

import com.forohub.forohub.domina.modelo.StatusTopico;
import jakarta.validation.constraints.Size;

public record DatosActualizacionTopico(String titulo,
                                       @Size(max = 255)String mensaje,
                                       String autor,
                                       String curso,
                                       StatusTopico status) {
}
