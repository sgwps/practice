package ru.hse_se_podbel.bot.message.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;
import java.util.Scanner;

@Component
public class AnswerCheckResultMessageBuilder {
    @Value("classpath:correct_answer.txt")
    Resource rightAnswer;


    @Value("classpath:wrong_answer.txt")
    Resource wrongAnswer;



    public SendMessage build(String chatId, boolean isCorrect) throws IOException {
        Resource resourceFile = isCorrect ? rightAnswer : wrongAnswer;
        Scanner scanner = new Scanner(resourceFile.getInputStream());
        String reply = scanner.nextLine().strip();
        return new SendMessage(chatId, reply);
    }
}
