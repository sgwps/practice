package ru.hse_se_podbel.bot.message.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;
import java.util.Scanner;

@Component
public class ErrorMessageBuilder {
    @Value("classpath:error.txt")
    Resource resourceFile;

    public SendMessage build(String chatId) throws IOException {
        Scanner scanner = new Scanner(resourceFile.getInputStream());
        scanner.useDelimiter("\\Z");
        String reply = scanner.next();

        return new SendMessage(chatId, reply);
    }
}
