package ru.hse.smart_pro.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.smart_pro.data.models.LanguageToStudent;
import ru.hse.smart_pro.data.models.User;

import java.util.List;
import java.util.UUID;

public interface LanguageToStudentRepository extends JpaRepository<LanguageToStudent, UUID> {
    List<LanguageToStudent> findByUser(User user);

}
