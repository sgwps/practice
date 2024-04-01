package ru.hse_se_podbel.bot.structures;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonDeserialize
@JsonSerialize
@NoArgsConstructor
public class Task {
    public String question;
    public String code;
    public UUID id;
    public List<AnswerOption> answerOptions;

    public boolean optionsContainCode() {
        return getAnswerOptions().stream().anyMatch(answerOption -> answerOption.is_code());
    }
}
