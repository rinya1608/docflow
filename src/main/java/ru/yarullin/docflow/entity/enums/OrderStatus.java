package ru.yarullin.docflow.entity.enums;

import java.util.Arrays;

/**
 * Enum для хранения статусов приказа
 */
public enum OrderStatus {
    CREATED("СОЗДАН"), EXECUTED("ВЫПОЛНЕН");

    private final String text;

    OrderStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    /**
     * Метод для получения типа статуса по тексту
     *
     * @param text текст статуса
     * @return статус
     */
    public static OrderStatus getByText(String text) {
        return Arrays.stream(OrderStatus.values())
                .filter(status -> text.equalsIgnoreCase(status.getText()))
                .findFirst()
                .orElse(null);
    }
}
