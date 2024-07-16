package ru.hse.smart_pro.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.smart_pro.data.models.Language;

import java.util.List;
import java.util.UUID;

public interface LanguageRepository extends JpaRepository<Language, UUID> {
    List<Language> findByName(String name);

}
