package ru.yarullin.docflow.executors.enums;

public enum OperationStatus {
    IN_PROGRESS("ВЫПОЛНЯЕТСЯ"),
    WAIT("ОЖИДАЕТ");

    private final String text;

    OperationStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
