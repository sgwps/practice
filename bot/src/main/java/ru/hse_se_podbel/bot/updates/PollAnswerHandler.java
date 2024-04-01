package ru.hse_se_podbel.bot.updates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hse_se_podbel.bot.data.Session;
import ru.hse_se_podbel.bot.data.SessionService;
import ru.hse_se_podbel.bot.exception.NotSingleSessionException;
import ru.hse_se_podbel.bot.exception.SessionNotFoundException;
import ru.hse_se_podbel.bot.message.TaskMessage;
import ru.hse_se_podbel.bot.parcer.CheckAnswerParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PollAnswerHandler {
    @Autowired
    SessionService sessionService;

    @Autowired
    CheckAnswerParser checkAnswerParser;

    @Autowired
    NextTaskOrFinishBuilder nextTaskOrFinishBuilder;

    public TaskMessage handle(List<String> options, String pollId) throws NotSingleSessionException, SessionNotFoundException, IOException, URISyntaxException, InterruptedException {
        options = options.stream().map(i -> i.substring(i.indexOf(" ") + 1)).collect(Collectors.toList());
        Session session = sessionService.findByLastPollId(pollId);
        boolean result = checkAnswerParser.checkAnswer(options, session.getTasks().get(session.getCurrentTask()));
        return nextTaskOrFinishBuilder.build(result, session.getChatId());

    }
}
