package ru.yarullin.docflow.analyzer;

import ru.yarullin.docflow.entity.Document;
import ru.yarullin.docflow.entity.Letter;
import ru.yarullin.docflow.entity.Order;
import ru.yarullin.docflow.entity.enums.DocumentType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yarullin.docflow.entity.enums.DocumentType.*;

/**
 * Класс для анализа данных с доукементами
 */
public class DocumentAnalyzer {
    /**
     * Метод для подсчета количества документов для каждого типа
     *
     * @param documents список документоа
     * @return строка со статистикой
     */
    public static String analyzeDocumentByType(List<Document> documents) {
        List<DocumentType> documentTypes = documents.stream()
                .map(DocumentType::getByDocumentClassType)
                .toList();

        return LETTER.getName() + " - " + getDocumentTypeCount(documentTypes, LETTER) + "\n" +
                DISMISSAL_ORDER.getName() + " - " + getDocumentTypeCount(documentTypes, DISMISSAL_ORDER) + "\n" +
                RECEPTION_ORDER.getName() + " - " + getDocumentTypeCount(documentTypes, RECEPTION_ORDER) + "\n";
    }

    /**
     * Метод для подсчета количества документов для каждого сотрудника
     *
     * @param documents список документоа
     * @return строка со статистикой
     */
    public static String analyzeDocumentByEmployee(List<Document> documents) {
        Map<String, Long> countByEmployee = new HashMap<>();
        documents.forEach(doc -> {
            if (doc instanceof Letter letter) {
                countByEmployee.put(letter.getFrom(), countByEmployee.getOrDefault(letter.getFrom(), 0L) + 1);
                if (!letter.getFrom().equals(letter.getTo())) {
                    countByEmployee.put(letter.getTo(), countByEmployee.getOrDefault(letter.getTo(), 0L) + 1);
                }
            } else
                countByEmployee.put(((Order) doc).getEmployee(), countByEmployee.getOrDefault(((Order) doc).getEmployee(), 0L) + 1);
        });
        StringBuilder stringBuilder = new StringBuilder();
        countByEmployee.forEach((key, val) -> stringBuilder.append(key).append(" - ").append(val).append("\n"));
        return stringBuilder.toString();
    }

    private static Long getDocumentTypeCount(List<DocumentType> documentsType, DocumentType documentType) {
        return documentsType.stream().filter(type -> type.equals(documentType)).count();
    }
}
