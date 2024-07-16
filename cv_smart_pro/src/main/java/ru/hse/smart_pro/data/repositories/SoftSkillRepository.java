package ru.hse.smart_pro.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.smart_pro.data.models.SoftSkill;

import java.util.List;
import java.util.UUID;

public interface SoftSkillRepository extends JpaRepository<SoftSkill, UUID> {
    List<SoftSkill> findByName(String name);

}
