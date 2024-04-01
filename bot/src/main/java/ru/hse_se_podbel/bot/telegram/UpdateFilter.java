package ru.hse_se_podbel.bot.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.hse_se_podbel.bot.exception.InvalidCommandException;
import ru.hse_se_podbel.bot.exception.NotSingleSessionException;
import ru.hse_se_podbel.bot.exception.SessionNotFoundException;
import ru.hse_se_podbel.bot.exception.UnknownMessageException;
import ru.hse_se_podbel.bot.message.TaskMessage;
import ru.hse_se_podbel.bot.updates.ButtonOnClickHandler;
import ru.hse_se_podbel.bot.updates.CommandUpdateHandler;
import ru.hse_se_podbel.bot.updates.PollAnswerHandler;
import ru.hse_se_podbel.bot.updates.TextMessageHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdateFilter {
    @Autowired
    ButtonOnClickHandler buttonOnClickHandler;

    @Autowired
    CommandUpdateHandler commandUpdateHandler;

    @Autowired
    PollAnswerHandler pollAnswerHandler;

    @Autowired
    TextMessageHandler textMessageHandler;

    public static String COMMAND_PREFIX = "/";

    public TaskMessage getReply(Update update) throws NotSingleSessionException, IOException, SessionNotFoundException, InvalidCommandException, UnknownMessageException, URISyntaxException, InterruptedException {

        if (update.hasPoll()) {
            List<String> options = update.getPoll().getOptions().stream().
                    filter(i -> i.getVoterCount() != 0).map(i -> i.getText()).collect(Collectors.toList());
            return pollAnswerHandler.handle(options, update.getPoll().getId());
        }
        if (update.hasCallbackQuery()) {
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            return buttonOnClickHandler.handle(chatId, update.getCallbackQuery().getData(), update.getCallbackQuery().getMessage().getMessageId().toString());
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            if (update.getMessage().isCommand()) {
                return new TaskMessage(commandUpdateHandler.handle(chatId, update.getMessage().getText().split("/")[1].toUpperCase()));
            }
            else {
                return textMessageHandler.handle(update.getMessage().getText(), chatId);
            }
        }
        throw new UnknownMessageException();
    }
}
