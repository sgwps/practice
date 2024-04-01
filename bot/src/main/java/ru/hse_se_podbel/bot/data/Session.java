package ru.hse_se_podbel.bot.data;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.hse_se_podbel.bot.data.SubjectGroup;

import java.util.List;
import java.util.UUID;

@Document(collection = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    @Id
    String chatId;

    String lastPollId;

    SubjectGroup group;

    List<UUID> tasks;

    int currentTask;

    int rightAnswersCount;

    String lastKeyboardMessageId;  // Если не было сообщений с клавиатурой - просто последнее сообщение
}
