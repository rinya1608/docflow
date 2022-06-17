package ru.yarullin.docflow.reader;

import java.util.List;

/**
 * Интерфейс для чтения данных
 *
 * @param <T> тип возвращаемых объектов
 */
public interface Reader<T> {
    /**
     * Метод для чтения данных
     *
     * @param path путь к данным
     * @return список считанных объектов
     */
    List<T> read(String path);
}
