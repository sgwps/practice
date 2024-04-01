package ru.hse_se_podbel.bot.data;

import com.fasterxml.jackson.annotation.JsonValue;
import ru.hse_se_podbel.bot.exception.SubjectGroupNotFoundException;

import java.util.Optional;

public enum SubjectGroup {
    FIRST_MODULE_EXAM("ЭКЗАМЕН ПЕРВОГО МОДУЛЯ", 1),
    SECOND_MODULE_EXAM("ЭКЗАМЕН ВТОРОГО МОДУЛЯ", 2),
    THIRD_MODULE_EXAM("ЭКЗАМЕН ТРЕТЬЕГО МОДУЛЯ", 3);

    private final String subjectGroupName;
    private final int module;

    SubjectGroup(String subjectGroupName, int module) {
        this.subjectGroupName = subjectGroupName;
        this.module = module;
    }

    public String getSubjectGroupName() {
        return subjectGroupName;
    }

    @JsonValue
    public String getValue() {
        return this.toString();
    }

    public int getModule() {
        return this.module;
    }

    public static Optional<SubjectGroup> find(String value) {
        for (SubjectGroup subjectGroup : values()){
            if (subjectGroup.getSubjectGroupName().equals(value)){
                return Optional.of(subjectGroup);
            }
        }
        return Optional.empty();
    }
}
