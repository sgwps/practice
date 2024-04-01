package ru.hse_se_podbel.data.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import ru.hse_se_podbel.data.models.enums.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = null;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Override
    public Collection<Role> getAuthorities() {
        if (role == Role.ADMIN) {
            return Stream.of(Role.ADMIN, Role.USER).collect(Collectors.toList());
        }
        return Collections.singleton(getAuthority());
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Role getAuthority() {
        return role;
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

    @Override
    public String getUsername() {
        return username;
    }


}

