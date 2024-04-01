package ru.hse_se_podbel.bot.message.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class TaskCountChoiceMessageBuilder {

    private InlineKeyboardMarkup setButtons(int maxQuestionCount) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboard = new ArrayList<>();

        for (int i = 5; i <= maxQuestionCount; i = Integer.max(Integer.min(i + 5, maxQuestionCount), i + 1)) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(Integer.toString(i));
            inlineKeyboardButton.setCallbackData(Integer.toString(i));
            keyboard.add(inlineKeyboardButton);
        }
        List<List<InlineKeyboardButton>> finalKeyboard = new ArrayList<>();
        finalKeyboard.add(new ArrayList<>());
        for (int i = 0; i < keyboard.size(); i++) {
            finalKeyboard.get(finalKeyboard.size() - 1).add(keyboard.get(i));
            if (finalKeyboard.get(finalKeyboard.size() - 1).size() == 3) {
                finalKeyboard.add(new ArrayList<>());
            }
        }
        inlineKeyboardMarkup.setKeyboard(finalKeyboard);
        return inlineKeyboardMarkup;
    }

    @Value("classpath:select_count.txt")
    Resource resourceFile;

    public SendMessage build(String chatId, int maxQuestionsCount) throws IOException {

        Scanner scanner = new Scanner(resourceFile.getInputStream());
        String reply = scanner.nextLine().strip();
        SendMessage sendMessage = new SendMessage(chatId, reply);
        sendMessage.setReplyMarkup(setButtons(maxQuestionsCount));
        return sendMessage;

    }

}
