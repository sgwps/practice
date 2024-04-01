package ru.hse_se_podbel.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse_se_podbel.data.models.AnswerOption;
import ru.hse_se_podbel.data.models.Task;

import java.util.List;
import java.util.UUID;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, UUID> {
    // List<AnswerOption> findBy
    public int deleteByTaskId(UUID taskId);
}
