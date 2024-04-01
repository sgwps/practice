package ru.hse_se_podbel.bot.message.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

@Component
public class StartMessageBuilder {
    

    @Value("classpath:start_message.txt")
    Resource resourceFile;

    public SendMessage build(String chatId) throws IOException {


        Scanner scanner = new Scanner(resourceFile.getInputStream());
        String reply = scanner.nextLine().strip();
        return new SendMessage(chatId, reply);
    }
}
