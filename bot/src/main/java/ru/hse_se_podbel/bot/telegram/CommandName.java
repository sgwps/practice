package ru.hse_se_podbel.bot.telegram;

public enum CommandName {

    START("/start"),
    TEST("/test");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}
