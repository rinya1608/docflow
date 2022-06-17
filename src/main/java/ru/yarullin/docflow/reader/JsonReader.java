package ru.yarullin.docflow.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.yarullin.docflow.converter.Converter;
import ru.yarullin.docflow.converter.JsonObjectToDocument;
import ru.yarullin.docflow.entity.Document;
import ru.yarullin.docflow.exceptions.DataException;
import ru.yarullin.docflow.writer.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для чтения json файла
 *
 * @see ru.yarullin.docflow.reader.Reader
 */
public class JsonReader implements Reader<Document> {

    private static final Logger logger = LogManager.getLogger(JsonReader.class);

    private final JSONParser jsonParser;
    private final Converter<Document, JSONObject> converter;

    public JsonReader() {
        jsonParser = new JSONParser();
        converter = new JsonObjectToDocument();
    }

    /**
     * Метод для чтения json файла
     *
     * @param path путь к json файлу
     * @return список документоа
     */
    @Override
    public List<Document> read(String path) {
        List<Document> documents = new ArrayList<>();
        try (java.io.Reader reader = new FileReader(path)) {
            JSONArray jsonDocuments = (JSONArray) jsonParser.parse(reader);
            jsonDocuments.forEach(doc -> {
                Document document = converter.convert((JSONObject) doc);
                if (document != null) documents.add(document);
            });
        } catch (Exception e){
            logger.error("При чтении из файла " + path + " произошла ошибка", e);
            throw new DataException("Не удалось прочитать из файла " + path, e);
        }
        return documents;
    }
}
