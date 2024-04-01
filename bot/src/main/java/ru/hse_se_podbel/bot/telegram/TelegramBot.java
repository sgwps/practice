package ru.hse_se_podbel.bot.telegram;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hse_se_podbel.bot.config.BotProperties;
import ru.hse_se_podbel.bot.data.SessionService;
import ru.hse_se_podbel.bot.data.SubjectGroup;
import ru.hse_se_podbel.bot.exception.*;
import ru.hse_se_podbel.bot.message.TaskMessage;
import ru.hse_se_podbel.bot.message.builder.ErrorMessageBuilder;



@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    SessionService sessionService;

    @Autowired
    ErrorMessageBuilder errorMessageBuilder;

    @Autowired
    UpdateFilter updateFilter;

    public BotProperties botProperties = new BotProperties();


    @PostConstruct
    public void configure() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
            try {
                TaskMessage reply = updateFilter.getReply(update);
                sendTaskMessage(reply);
            } catch (SessionException | InvalidCommandException e) {
                if (update.hasMessage()) {
                    send(errorMessageBuilder.build(update.getMessage().getChatId().toString()));
                }
            }

    }

    private void sendTaskMessage(TaskMessage message) throws SessionNotFoundException {
        sendMessage(message.getFirstMessage());
        if (message.getSecondMessage().isPresent()) sendMessage(message.getSecondMessage().get());
        if (message.getPoll().isPresent()) {
            Message sentMessage = send(message.getPoll().get());
            sessionService.updateLastPollId(sentMessage.getPoll().getId(), sentMessage.getChatId().toString());
        }
    }

    private void sendMessage(SendMessage sendMessage) throws SessionNotFoundException {
        Message message = send(sendMessage);
        if (sendMessage.getReplyMarkup() != null) {
            try {
                sessionService.updateLastKeyboardMessageId(message.getMessageId().toString(), sendMessage.getChatId());
            } catch (SessionNotFoundException e) {

            }
        }
    }

    private Message send(BotApiMethod<Message> sendMessage) {
        try {
            return execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}