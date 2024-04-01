package ru.hse_se_podbel.bot.message.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.hse_se_podbel.bot.structures.AnswerOption;
import ru.hse_se_podbel.bot.structures.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class TaskOptionsMessageBuilder {
    @Value("classpath:poll_question.txt")
    Resource resourceFile;





    public SendPoll build(Task task, boolean numbersOnly) throws IOException {
        Scanner scanner = new Scanner(resourceFile.getInputStream());
        SendPoll sendPoll = new SendPoll();
        sendPoll.setAllowMultipleAnswers(true);
        sendPoll.setQuestion(scanner.nextLine().strip());
        List<String> options = new ArrayList<>();
        if (task.optionsContainCode() || numbersOnly) {
            for (int i = 1; i <= task.getAnswerOptions().size(); i++) {
                options.add(Integer.toString(i));
            }
        }
        else {
            for (AnswerOption answerOption : task.getAnswerOptions()) {
                options.add(String.format("%d. %s", answerOption.getIndex(), answerOption.getText()));
            }
        }
        sendPoll.setOptions(options);
        return sendPoll;
    }
}
