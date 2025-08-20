package com.forohub.forohub.segurity;

import com.forohub.forohub.domina.service.TokenService;
import com.forohub.forohub.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException{

        //Obetner el token del encabezado de la peticion

        var authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token); // EXTRAE EL SUBJECT DEL TOKEN

            if (nombreUsuario != null) {
                //El token es valido, procede con la auntenticacion
                //Usar la instancia inyectada 'usuarioRepository'
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                //Asegura de que el usuario no sea nulo antes de continuar
                if (usuario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(nombreUsuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        }

        // CONTINUA CON LA CADENA DE FILTROS
        filterChain.doFilter(request, response);
    }

}
