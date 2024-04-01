package ru.hse_se_podbel.bot.message.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.hse_se_podbel.bot.data.SubjectGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class SubjectGroupMessageBuilder {
    private InlineKeyboardMarkup setButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (SubjectGroup subjectGroup : SubjectGroup.values()) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(subjectGroup.getSubjectGroupName());
            inlineKeyboardButton.setCallbackData(subjectGroup.getSubjectGroupName());
            List<InlineKeyboardButton> buttonList = new ArrayList<>();
            buttonList.add(inlineKeyboardButton);
            keyboard.add(buttonList);
        }

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }


    @Value("classpath:new_session.txt")
    Resource resourceFile;

    public SendMessage build(String chatId) throws IOException {
        Scanner scanner = new Scanner(resourceFile.getInputStream());
        String reply = scanner.nextLine().strip();
        SendMessage sendMessage = new SendMessage(chatId, reply);
        sendMessage.setReplyMarkup(setButtons());
        return sendMessage;
    }
}
