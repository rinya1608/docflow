package ru.yarullin.docflow.db;

import java.util.List;

/**
 * Итерфейс для взаимодействия с базой данных
 *
 * @param <T> тип используемого объекта
 */
public interface DBManager<T> {
    /**
     * Метод для содания таблиц в базе данных
     *
     * @param url адрес для подключения к базе данных
     */
    void create(String url);

    /**
     * Метод для получения спика объектов типа T из всех строк таблиц базы данных
     *
     * @param url адрес для подключения к базе данных
     */
    List<T> getAll(String url);

    /**
     * Метод для добавления объектов в базу данных
     *
     * @param url        адрес для подключения к базе данных
     * @param objectList список объектов типа T которые будут добавленны в базу данныъ
     */
    void put(String url, List<T> objectList);
}
