package ru.yarullin.docflow.entity.enums;

import ru.yarullin.docflow.entity.DismissalOrder;
import ru.yarullin.docflow.entity.Document;
import ru.yarullin.docflow.entity.ReceptionOrder;

import java.util.Arrays;

/**
 * Enum для хранения типов документов
 */
public enum DocumentType {
    LETTER("letter"),
    DISMISSAL_ORDER("dismissalOrder"),
    RECEPTION_ORDER("receptionOrder");

    private final String name;

    DocumentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Метод для получения типа документа по его имени
     *
     * @param name имя документа
     * @return тип документа
     */
    public static DocumentType getByName(String name) {
        return Arrays.stream(DocumentType.values())
                .filter(type -> name.equalsIgnoreCase(type.getName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Метод для получения типа документа по типу класса объекта
     *
     * @param document входной объект для определения типа
     * @return тип документа
     */
    public static DocumentType getByDocumentClassType(Document document) {
        DocumentType type = LETTER;
        if (document instanceof DismissalOrder) type = DISMISSAL_ORDER;
        else if (document instanceof ReceptionOrder) type = RECEPTION_ORDER;
        return type;
    }
}
