package ru.yarullin.docflow.executors.enums;

import java.util.Arrays;

/**
 * Enum для хранения команд
 */
public enum Command {
    READ("read", 3, "[тип хранилища] [путь]", "считать документы из хранилища"),
    WRITE("write", 3, "[тип хранилища] [путь]", "Записать документы в хранилище"),
    VIEW("view", 1, "", "посмотреть доступные документы"),
    CLEAR("clear", 1, "", "удалить все доступные документы"),
    STAT("stat", 2, "[тип статистики]", "посмотреть статистику"),
    HELP("help", 1, "", "посмотреть документацию"),
    STATUS("status", 1, "", "посмотреть выполняющиеся операции"),
    EXIT("exit", 1, "", "Прекратить работу docflow");

    private final String name;
    private final Integer minCmdLength;
    private final String attrInfo;
    private final String description;

    Command(String name, Integer minCmdLength, String attrInfo, String description) {
        this.name = name;
        this.minCmdLength = minCmdLength;
        this.attrInfo = attrInfo;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Integer getMinCmdLength() {
        return minCmdLength;
    }

    public String getAttrInfo() {
        return attrInfo;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Метод для получения команды по названию
     *
     * @param name название команды
     */
    public static Command getByName(String name) {
        return Arrays.stream(Command.values())
                .filter(type -> name.equalsIgnoreCase(type.getName()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return getName() + " " + getAttrInfo() + "       -      " + getDescription();
    }
}
