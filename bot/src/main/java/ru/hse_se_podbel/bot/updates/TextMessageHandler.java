package ru.hse_se_podbel.bot.updates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hse_se_podbel.bot.data.Session;
import ru.hse_se_podbel.bot.data.SessionService;
import ru.hse_se_podbel.bot.exception.NotSingleSessionException;
import ru.hse_se_podbel.bot.exception.SessionNotFoundException;
import ru.hse_se_podbel.bot.exception.UnknownMessageException;
import ru.hse_se_podbel.bot.message.TaskMessage;
import ru.hse_se_podbel.bot.parcer.CheckAnswerParser;
import ru.hse_se_podbel.bot.parcer.TaskParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class TextMessageHandler {
    @Autowired
    SessionService sessionService;

    @Autowired
    CheckAnswerParser checkAnswerParser;

    @Autowired
    NextTaskOrFinishBuilder nextTaskOrFinishBuilder;

    @Autowired
    TaskParser taskParser;

    public TaskMessage handle(String answer, String chatId) throws SessionNotFoundException, IOException, UnknownMessageException, URISyntaxException, InterruptedException {
        Session session = sessionService.findByChatId(chatId);
        if (taskParser.parse(session.getTasks().get(session.getCurrentTask())).getAnswerOptions().size() != 0) {   // TODO: можно убрать лишний запрос,
            throw new UnknownMessageException();
        }
        boolean result = checkAnswerParser.checkAnswer(answer, session.getTasks().get(session.getCurrentTask()));
        return nextTaskOrFinishBuilder.build(result, session.getChatId());
    }
}
