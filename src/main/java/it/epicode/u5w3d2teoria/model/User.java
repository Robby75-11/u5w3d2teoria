package it.epicode.u5w3d2teoria.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import it.epicode.u5w3d2teoria.enumeration.Role;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private String cognome;
    @Column(unique = true) //creerà il campo username univoco sul db, cioè non potrà
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    //questo metodo ritorna tutti i ruoli che l'utente ha
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

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
}
