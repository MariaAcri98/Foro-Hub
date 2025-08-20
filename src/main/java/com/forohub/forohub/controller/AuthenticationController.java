package com.forohub.forohub.controller;

import com.forohub.forohub.domina.service.TokenService;
import com.forohub.forohub.segurity.DatosAutenticacionUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {

        // Crea un token de autenticación con el login y la contraseña
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.password()
        );

        // Autentica al usuario
        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        // Genera el token JWT para el usuario
        String jwtToken = tokenService.generarToken(usuarioAutenticado.getName());

        // Retorna una respuesta exitosa
        return ResponseEntity.ok(jwtToken);
    }
}