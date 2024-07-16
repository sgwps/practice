package ru.hse.smart_pro.data.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

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

    public String getName() {
        return "Иван";
    }

    public String getSirName(){
        return "Иванов";
    }

    public String getFaculty(){
        return "ФКН";

    }

    public String getProgram(){
        return "Програмная инженерия";

    }

    public int getYearOfStudy() {
        return 2;
    }

    public double getGPA(){
        return 5.0;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    @ManyToMany()
    Set<SoftSkill> softSkills = new HashSet<>();

    @ManyToMany()
    Set<HardSkill> hardSkills = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<LanguageToStudent> languageToStudentList = new ArrayList<>();


    public String getFullName() {
        return getName().concat(" ").concat(getSirName());
    }

    public String getFullProgram() {
        return getFaculty().concat("; ").concat(getProgram()).concat("; курс ").concat(String.valueOf(getYearOfStudy()));
    }
}

