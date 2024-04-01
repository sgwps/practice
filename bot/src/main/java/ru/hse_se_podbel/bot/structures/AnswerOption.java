package ru.hse_se_podbel.bot.structures;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonDeserialize
@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOption implements Comparable<AnswerOption>{
    int index;  // 1, 2, 3, 4...
    boolean is_code;  // в api сортируем по id
    String text;

    @Override
    public int compareTo(AnswerOption option) {
        return index - option.index;
    }
}
