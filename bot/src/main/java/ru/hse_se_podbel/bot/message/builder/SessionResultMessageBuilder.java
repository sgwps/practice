package ru.hse_se_podbel.bot.message.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;
import java.util.Scanner;

@Component
public class SessionResultMessageBuilder {
        @Value("classpath:results.txt")
        Resource resourceFile;

    public SendMessage build(String chatId, int rightCount, int allCount) throws IOException {
        long percent = Math.round((rightCount * 100.0d) / allCount);
        Scanner scanner = new Scanner(resourceFile.getInputStream());
        String template = scanner.nextLine().strip();
        return new SendMessage(chatId, String.format(template, rightCount, allCount, percent));
    }

}
