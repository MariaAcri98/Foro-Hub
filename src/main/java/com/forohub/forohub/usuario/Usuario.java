package com.forohub.forohub.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Un rol simplemente para este ejemplo
        return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return login;
    }

    //Metodo de la interfaz UserDetails

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    public Usuario() {}
}
