package ru.hse_se_podbel.bot.updates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hse_se_podbel.bot.data.Session;
import ru.hse_se_podbel.bot.data.SessionRepository;
import ru.hse_se_podbel.bot.data.SessionService;
import ru.hse_se_podbel.bot.exception.SessionNotFoundException;
import ru.hse_se_podbel.bot.message.TaskMessage;
import ru.hse_se_podbel.bot.message.builder.AnswerCheckResultMessageBuilder;
import ru.hse_se_podbel.bot.message.builder.SessionResultMessageBuilder;
import ru.hse_se_podbel.bot.message.builder.TaskMessageBuilder;
import ru.hse_se_podbel.bot.parcer.TaskParser;
import ru.hse_se_podbel.bot.structures.Task;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class NextTaskOrFinishBuilder {
    @Autowired
    SessionService sessionService;

    @Autowired
    TaskParser taskParser;

    @Autowired
    TaskMessageBuilder taskMessageBuilder;

    @Autowired
    SessionResultMessageBuilder sessionResultMessageBuilder;

    @Autowired
    AnswerCheckResultMessageBuilder answerCheckResultMessageBuilder;

    public TaskMessage build(String chatId) throws SessionNotFoundException, IOException, URISyntaxException, InterruptedException {
        Session session = sessionService.findByChatId(chatId);
        if (session.getCurrentTask() < session.getTasks().size()) {
            Task task = taskParser.parse(session.getTasks().get(session.getCurrentTask()));
            return taskMessageBuilder.build(task, chatId);
        }
        TaskMessage message = new TaskMessage(sessionResultMessageBuilder.build(chatId, session.getRightAnswersCount(), session.getTasks().size()), chatId);
        sessionService.delete(session);
        return message;

    }

    public TaskMessage build(boolean isPreviousCorrect, String chatId) throws SessionNotFoundException, IOException, URISyntaxException, InterruptedException {
        sessionService.updateAnswer(chatId, isPreviousCorrect);
        return build(chatId).addAnswerToPrevious(answerCheckResultMessageBuilder.build(chatId, isPreviousCorrect));
    }
}
