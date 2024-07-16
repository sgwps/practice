package ru.hse.smart_pro.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.smart_pro.data.models.LanguageLevel;

import java.util.List;
import java.util.UUID;

public interface LanguageLevelRepository extends JpaRepository<LanguageLevel, UUID> {
    List<LanguageLevel> findByName(String name);

}
