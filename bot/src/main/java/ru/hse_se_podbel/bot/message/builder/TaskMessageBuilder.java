package ru.hse_se_podbel.bot.message.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hse_se_podbel.bot.message.TaskMessage;
import ru.hse_se_podbel.bot.structures.Task;

import java.io.IOException;

@Component
public class TaskMessageBuilder {
    @Autowired
    TaskOptionsMessageBuilder taskOptionsMessageBuilder;

    @Autowired
    TaskTextMessageBuilder taskTextMessageBuilder;

    public TaskMessage build(Task task, String chatId) throws IOException {
        if (task.getAnswerOptions().size() > 1) {
            if (task.getAnswerOptions().stream().anyMatch(i -> i.getText().length() > 100))
                return new TaskMessage(taskTextMessageBuilder.build(task, true), taskOptionsMessageBuilder.build(task, true), chatId);
            else
                return new TaskMessage(taskTextMessageBuilder.build(task, false), taskOptionsMessageBuilder.build(task, false), chatId);
        }
        else return new TaskMessage(taskTextMessageBuilder.build(task, false), chatId);
    }
}
