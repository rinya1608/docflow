package ru.yarullin.docflow.writer;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Интерфейс для записи данных
 *
 * @param <T> тип принимаемых объектов
 */
public interface Writer<T> {
    /**
     * Метод для записи данных
     *
     * @param path       путь для записи данных
     * @param objectList список объектов для записи
     */
    void write(String path, List<T> objectList);
}
