package ru.hse_se_podbel.bot.updates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.hse_se_podbel.bot.exception.InvalidCommandException;
import ru.hse_se_podbel.bot.message.builder.StartMessageBuilder;
import ru.hse_se_podbel.bot.message.builder.SubjectGroupMessageBuilder;
import ru.hse_se_podbel.bot.telegram.CommandName;

import java.io.IOException;

@Component
public class CommandUpdateHandler {
    @Autowired
    StartMessageBuilder startMessageBuilder;

    @Autowired
    SubjectGroupMessageBuilder subjectGroupMessageBuilder;

    public SendMessage handle(String chatId, String text) throws IOException, InvalidCommandException {
        String commandIdentifier = text.split("/")[0].toUpperCase();
        CommandName commandName = CommandName.valueOf(commandIdentifier);
        if (commandName.equals(CommandName.START)) {
            return startMessageBuilder.build(chatId);
        }
        else if (commandName.equals(CommandName.TEST)) {
            return subjectGroupMessageBuilder.build(chatId);
        } else {
            throw new InvalidCommandException();
        }
    }
}
