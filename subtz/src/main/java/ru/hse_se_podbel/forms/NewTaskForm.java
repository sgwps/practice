package ru.hse_se_podbel.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;
import ru.hse_se_podbel.data.models.AnswerOption;
import ru.hse_se_podbel.data.models.Subject;
import ru.hse_se_podbel.data.models.Task;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class NewTaskForm  {


    private String text;
    private String code;
    private SubjectCheckboxForm[] subjects = new SubjectCheckboxForm[0];
    private AnswerOption[] options = new AnswerOption[0];
    private long number;
    @ReadOnlyProperty
    private UUID id = null;



    public boolean isNew() { return id == null; }

    public void fill(Task task) {
        text = task.getQuestion();
        code = task.getCode();
        for (SubjectCheckboxForm subjectCheckboxForm : subjects) {   // TODO: если поменять массив на словарь, можно оптимизировать
            if (task.getSubjects().stream().anyMatch(subject -> subject.getId() == subjectCheckboxForm.getKey().getId())) {
                subjectCheckboxForm.setValue(true);
            }
        }
        options = new AnswerOption[task.getAnswerOptions().size()];
        task.getAnswerOptions().toArray(options);
        number = task.getNumber();
        id = task.getId();
    }
}
