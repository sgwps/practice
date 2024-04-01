package ru.hse_se_podbel.bot.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BotProperties {  // TODO: вынести в properties

    String name;

    String token;

    public BotProperties() {
        BotPropertiesValues values = new BotPropertiesValues();
        name = values.getName();
        token = values.getToken();
    }

}