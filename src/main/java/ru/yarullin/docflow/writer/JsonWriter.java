package ru.yarullin.docflow.writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.yarullin.docflow.entity.DismissalOrder;
import ru.yarullin.docflow.entity.Document;
import ru.yarullin.docflow.entity.Letter;
import ru.yarullin.docflow.entity.Order;
import ru.yarullin.docflow.entity.enums.DocumentType;
import ru.yarullin.docflow.exceptions.DataException;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;

import static ru.yarullin.docflow.entity.constants.DocumentFieldName.*;

/**
 * Класс для записи документов в json файл
 *
 * @see Writer
 */
@SuppressWarnings({"unchecked"})
public class JsonWriter implements Writer<Document> {

    private static final Logger logger = LogManager.getLogger(JsonWriter.class);

    /**
     * Метод для записи документов в json файл
     *
     * @param path      путь к файлу
     * @param documents список документов для записи
     */
    @Override
    public void write(String path, List<Document> documents) {
        try (FileWriter file = new FileWriter(path, true)) {
            JSONArray jsonDocuments = new JSONArray();
            documents.forEach(document -> {
                JSONObject jsonDoc = new JSONObject();
                JSONObject jsonDocDetail = new JSONObject();
                jsonDocDetail.put(NUMBER, document.getNumber());
                jsonDocDetail.put(NAME, document.getName());
                if (document instanceof Letter letter) {
                    jsonDocDetail.put(TYPE, DocumentType.LETTER.getName());
                    jsonDocDetail.put(FROM, letter.getFrom());
                    jsonDocDetail.put(TO, letter.getTo());
                } else if (document instanceof Order order) {
                    jsonDocDetail.put(EMPLOYEE, order.getEmployee());
                    jsonDocDetail.put(TEXT, order.getText());
                    jsonDocDetail.put(STATUS, order.getStatus().getText());
                    if (order instanceof DismissalOrder) {
                        jsonDocDetail.put(REASON, ((DismissalOrder) order).getReason());
                        jsonDocDetail.put(TYPE, DocumentType.DISMISSAL_ORDER.getName());
                    } else jsonDocDetail.put(TYPE, DocumentType.RECEPTION_ORDER.getName());

                }
                jsonDoc.put(DOCUMENT, jsonDocDetail);
                jsonDocuments.add(jsonDoc);
            });
            file.write(jsonDocuments.toJSONString());
            file.flush();
            logger.debug("Документы записаны в файл " + path);
        }
        catch (Exception e) {
            logger.error("При записи в файл " + path + " произошла ошибка", e);
            throw new DataException("Не удалось записать в файл " + path, e);
        }
    }
}
