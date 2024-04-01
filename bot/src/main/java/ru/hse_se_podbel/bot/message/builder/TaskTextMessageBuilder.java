package ru.hse_se_podbel.bot.message.builder;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import ru.hse_se_podbel.bot.structures.AnswerOption;
import ru.hse_se_podbel.bot.structures.Task;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskTextMessageBuilder {

    private MessageEntity codeEntity(int offset, String text) {
        MessageEntity entity = new MessageEntity();
        entity.setOffset(offset);
        entity.setText(text);
        entity.setLength(text.length());
        entity.setType("pre");
        return entity;
    }

    public SendMessage build(Task task, boolean addOptions) {
        SendMessage sendMessage = new SendMessage();
        //sendMessage.setChatId(chatId);

        List<MessageEntity> entities = new ArrayList<>();
        String messageString = task.getQuestion();
        messageString = messageString.concat("\n");
        int offset = messageString.length();
        messageString = messageString.concat(task.getCode());

        entities.add(codeEntity(offset, task.getCode()));
        if (task.optionsContainCode() || addOptions) {
            messageString = messageString.concat("\n\n");
            int index = 1;
            for (AnswerOption answerOption : task.answerOptions) {
                messageString = messageString.concat(String.format("%d. ", index));
                if (answerOption.is_code()) {
                    entities.add(codeEntity(messageString.length(), answerOption.getText()));
                }
                messageString = messageString.concat(String.format("%s\n", answerOption.getText()));
                index += 1;
            }
        }
        sendMessage.setText(messageString);
        sendMessage.setEntities(entities);
        return sendMessage;
    }





}
