package ru.yarullin.docflow.reader;

import ru.yarullin.docflow.db.DBManager;
import ru.yarullin.docflow.entity.Document;

import java.util.List;

/**
 * Класс для чтения из базы данных
 *
 * @see ru.yarullin.docflow.reader.Reader
 */
public class DBReader implements Reader<Document> {
    private final DBManager<Document> dbManager;

    public DBReader(DBManager<Document> dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Метод для чтения строк из базы данных
     *
     * @param url путь к базе данных
     * @return список документоа
     */
    @Override
    public List<Document> read(String url) {
        return dbManager.getAll(url);
    }
}
