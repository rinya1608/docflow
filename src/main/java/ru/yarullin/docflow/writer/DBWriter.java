package ru.yarullin.docflow.writer;

import ru.yarullin.docflow.db.DBManager;
import ru.yarullin.docflow.entity.Document;

import java.util.List;

/**
 * Класс для записи документов в базу данных
 *
 * @see Writer
 */
public class DBWriter implements Writer<Document> {
    private final DBManager<Document> dbManager;

    public DBWriter(DBManager<Document> dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Метод для записи документов в базу данных
     *
     * @param url       путь к базе данных
     * @param documents список документов для записи
     */
    @Override
    public void write(String url, List<Document> documents) {
        dbManager.put(url, documents);
    }
}
