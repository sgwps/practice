package ru.hse.smart_pro.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.smart_pro.data.models.HardSkill;

import java.util.List;
import java.util.UUID;

public interface HardSkillRepository extends JpaRepository<HardSkill, UUID> {

    List<HardSkill> findByName(String name);
}
