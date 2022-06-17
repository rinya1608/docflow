package ru.yarullin.docflow.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import ru.yarullin.docflow.entity.DismissalOrder;
import ru.yarullin.docflow.entity.Document;
import ru.yarullin.docflow.entity.Letter;
import ru.yarullin.docflow.entity.ReceptionOrder;
import ru.yarullin.docflow.entity.constants.DocumentFieldName;
import ru.yarullin.docflow.entity.enums.DocumentType;
import ru.yarullin.docflow.entity.enums.OrderStatus;

public class JsonObjectToDocument implements Converter<Document, JSONObject> {

    private static final Logger logger = LogManager.getLogger(JsonObjectToDocument.class);

    @Override
    public Document convert(JSONObject jsonObject) {
        Document document = null;
        try {
            JSONObject jsonDoc = (JSONObject) jsonObject.get(DocumentFieldName.DOCUMENT);
            DocumentType type = DocumentType.getByName((String) jsonDoc.get(DocumentFieldName.TYPE));
            Integer number = ((Long) jsonDoc.get(DocumentFieldName.NUMBER)).intValue();
            String name = (String) jsonDoc.get(DocumentFieldName.NAME);
            if (type != null) {
                if (DocumentType.LETTER.equals(type)) {
                    String from = (String) jsonDoc.get(DocumentFieldName.FROM);
                    String to = (String) jsonDoc.get(DocumentFieldName.TO);
                    document = new Letter(number, name, to, from);
                } else if (DocumentType.RECEPTION_ORDER.equals(type) || DocumentType.DISMISSAL_ORDER.equals(type)) {
                    String employee = (String) jsonDoc.get(DocumentFieldName.EMPLOYEE);
                    String text = (String) jsonDoc.get(DocumentFieldName.TEXT);
                    OrderStatus orderStatus = OrderStatus.getByText((String) jsonDoc.get(DocumentFieldName.STATUS));
                    if (DocumentType.RECEPTION_ORDER.equals(type))
                        document = new ReceptionOrder(number, name, employee, text, orderStatus);
                    else {
                        String reason = (String) jsonDoc.get(DocumentFieldName.REASON);
                        document = new DismissalOrder(number, name, employee, text, orderStatus, reason);
                    }
                }
            }
            logger.error("json объекта конвертирован в Document");
        } catch (Exception e) {
            logger.error("При конвертации json объекта (" + jsonObject.toJSONString() + ") в Document произошла ошибка", e);
            throw new RuntimeException(e);
        }
        return document;
    }
}
