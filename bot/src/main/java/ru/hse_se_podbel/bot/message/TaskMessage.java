package ru.hse_se_podbel.bot.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class TaskMessage {
    private SendMessage firstMessage;
    private Optional<SendMessage> secondMessage = Optional.empty();
    private Optional<SendPoll> poll = Optional.empty();

    public void setChatId(String chatId) {
        firstMessage.setChatId(chatId);
        if (secondMessage.isPresent())
            secondMessage.get().setChatId(chatId);
        if (poll.isPresent())
            poll.get().setChatId(chatId);
    }

    public TaskMessage(SendMessage sendMessage, String chatId) {
        firstMessage = sendMessage;
        setChatId(chatId);
    }

    public TaskMessage(SendMessage first, SendMessage second, String chatId) {
        firstMessage = first;
        secondMessage = Optional.of(second);
        setChatId(chatId);
    }

    public TaskMessage(SendMessage first, SendMessage second, SendPoll sendPoll, String chatId) {
        firstMessage = first;
        secondMessage = Optional.of(second);
        poll = Optional.of(sendPoll);
        setChatId(chatId);
    }

    public TaskMessage(SendMessage sendMessage) {
        firstMessage = sendMessage;
        if (firstMessage.getChatId() == null) {
            throw new IllegalArgumentException();
        }
    }


    public TaskMessage addAnswerToPrevious(SendMessage sendMessage) {
        secondMessage = Optional.of(firstMessage);
        firstMessage = sendMessage;
        return this;
    }

    public TaskMessage(SendMessage sendMessage, SendPoll sendPoll, String chatId) {
        firstMessage = sendMessage;
        poll = Optional.of(sendPoll);
        setChatId(chatId);
    }



}
