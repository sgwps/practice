package ru.hse_se_podbel.bot.updates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hse_se_podbel.bot.data.Session;
import ru.hse_se_podbel.bot.data.SessionService;
import ru.hse_se_podbel.bot.data.SubjectGroup;
import ru.hse_se_podbel.bot.exception.SessionNotFoundException;
import ru.hse_se_podbel.bot.exception.UnknownMessageException;
import ru.hse_se_podbel.bot.message.TaskMessage;
import ru.hse_se_podbel.bot.message.builder.TaskCountChoiceMessageBuilder;
import ru.hse_se_podbel.bot.parcer.MaxTaskCountParser;
import ru.hse_se_podbel.bot.parcer.TaskSequenceParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Component
public class ButtonOnClickHandler {

    @Autowired
    SessionService sessionService;

    @Autowired
    TaskCountChoiceMessageBuilder taskCountChoiceMessageBuilder;

    @Autowired
    MaxTaskCountParser maxTaskCountParser;

    @Autowired
    TaskSequenceParser taskSequenceParser;

    @Autowired
    NextTaskOrFinishBuilder nextTaskOrFinishBuilder;

    public TaskMessage handle(String chatId, String message, String messageId) throws SessionNotFoundException, IOException, UnknownMessageException, URISyntaxException, InterruptedException {

        Optional<SubjectGroup> subjectGroup = SubjectGroup.find(message);
        if (!subjectGroup.isEmpty()) {  // Ввел тему
            sessionService.createNew(chatId, subjectGroup.get());
            return new TaskMessage(taskCountChoiceMessageBuilder.build(chatId, maxTaskCountParser.maxTaskCount(subjectGroup.get())));
        }
        else {  // Ввел количество
            Session session = sessionService.findByChatId(chatId);
            if (!session.getLastKeyboardMessageId().equals(messageId)) {
                throw new UnknownMessageException();  // Точно к этой сессии
            }
            sessionService.setTasks(chatId, taskSequenceParser.getTasks(session.getGroup(), Integer.parseInt(message)));
            return nextTaskOrFinishBuilder.build(chatId);
        }

    }

}
