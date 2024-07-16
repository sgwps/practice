package ru.hse.smart_pro.data.models;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "language_to_student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageToStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = null;


    @ManyToOne()
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne()
    @JoinColumn(name = "language_id")
    @NotNull
    private Language language;

    @ManyToOne()
    @JoinColumn(name = "language_level_id")
    @NotNull
    private LanguageLevel languageLevel;


}
