package ru.hse.smart_pro.data.models;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "language-level")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = null;

    @NotNull
    @Size(min = 5, max = 200)
    @Column(unique = true)
    public String name;
}
