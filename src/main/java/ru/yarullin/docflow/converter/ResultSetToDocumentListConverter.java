package ru.yarullin.docflow.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yarullin.docflow.entity.DismissalOrder;
import ru.yarullin.docflow.entity.Document;
import ru.yarullin.docflow.entity.Letter;
import ru.yarullin.docflow.entity.ReceptionOrder;
import ru.yarullin.docflow.entity.enums.OrderStatus;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static ru.yarullin.docflow.entity.constants.DocumentFieldName.*;

/**
 * Конвертер из ResultSet в список документов
 *
 * @see ru.yarullin.docflow.converter.Converter
 */
public class ResultSetToDocumentListConverter implements Converter<List<Document>, ResultSet> {

    private static final Logger logger = LogManager.getLogger(ResultSetToDocumentListConverter.class);

    /**
     * Метод конвертирует объект типа V в объект типа T
     *
     * @param resultSet resultSet строк из бд
     * @return список документов
     */
    @Override
    public List<Document> convert(ResultSet resultSet) {
        List<Document> documents = new ArrayList<>();
        try {
            String tableName = resultSet.getMetaData().getTableName(1);
            while (resultSet.next()) {
                Document document;
                int number = resultSet.getInt(NUMBER);
                String name = resultSet.getString(NAME);
                if ("letter".equalsIgnoreCase(tableName)) {
                    String to = resultSet.getString(TO + "_emp");
                    String from = resultSet.getString(FROM + "_emp");
                    document = new Letter(number, name, to, from);
                } else {
                    String employee = resultSet.getString(EMPLOYEE);
                    String text = resultSet.getString(TEXT);
                    OrderStatus status = OrderStatus.getByText(resultSet.getString(STATUS));
                    if ("dismissal_order".equalsIgnoreCase(tableName)) {
                        String reason = resultSet.getString(REASON);
                        document = new DismissalOrder(number, name, employee, text, status, reason);
                    } else {
                        document = new ReceptionOrder(number, name, employee, text, status);
                    }
                }
                documents.add(document);
            }
            logger.debug("ResultSet конвертирован в list of Document");
        } catch (Exception e) {
            logger.error("При конвертации ResultSet в list of Document произошла ошибка", e);
            throw new RuntimeException(e);
        }
        return documents;
    }
}
