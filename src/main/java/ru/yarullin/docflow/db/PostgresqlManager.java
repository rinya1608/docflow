package ru.yarullin.docflow.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yarullin.docflow.converter.ResultSetToDocumentListConverter;
import ru.yarullin.docflow.entity.DismissalOrder;
import ru.yarullin.docflow.entity.Document;
import ru.yarullin.docflow.entity.Letter;
import ru.yarullin.docflow.entity.Order;
import ru.yarullin.docflow.exceptions.DataException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.yarullin.docflow.db.constant.SqlQueryStorage.*;

/**
 * Класс для взаимодействия с СУБД postgresql
 *
 * @see ru.yarullin.docflow.db.DBManager
 */
public class PostgresqlManager implements DBManager<Document> {

    private static final Logger logger = LogManager.getLogger(PostgresqlManager.class);

    private final ResultSetToDocumentListConverter converter;


    public PostgresqlManager() {
        this.converter = new ResultSetToDocumentListConverter();
    }

    @Override
    public void create(String url) {
        try (Connection connection = DriverManager.getConnection(url)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(CREATE_TABLE_LETTER);
                statement.execute(CREATE_DISMISSAL_ORDER_TABLE);
                statement.execute(CREATE_RECEPTION_ORDER_TABLE);
            }
        } catch (Exception e) {
            logger.error("При попытке создать таблицы произошла ошибка", e);
            throw new DataException("Не удалось создать таблицы в базе данных с url " + url, e);
        }
    }

    @Override
    public List<Document> getAll(String url) {
        List<Document> documents = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url)) {
            try (Statement statement = connection.createStatement()) {
                documents.addAll(getFromTable(statement, "letter"));
                documents.addAll(getFromTable(statement, "dismissal_order"));
                documents.addAll(getFromTable(statement, "reception_order"));
            }
        } catch (Exception e) {
            logger.error("При попытке получить все документы произошла ошибка", e);
            throw new DataException("Не удалось получить данные из бд с url " + url, e);
        }

        return documents;
    }

    @Override
    public void put(String url, List<Document> objectList) {
        create(url);
        try (Connection connection = DriverManager.getConnection(url)) {
            for (Document document :
                    objectList) {
                String insertSql = document instanceof Letter ?
                        INSERT_INTO_LETTER :
                        document instanceof DismissalOrder ?
                                INSERT_INTO_DISMISSAL_ORDER :
                                INSERT_INTO_RECEPTION_ORDER;

                try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
                    statement.setInt(1, document.getNumber());
                    statement.setString(2, document.getName());
                    if (document instanceof Letter letter) {
                        statement.setString(3, letter.getTo());
                        statement.setString(4, letter.getFrom());
                    } else if (document instanceof Order order) {
                        statement.setString(3, order.getEmployee());
                        statement.setString(4, order.getText());
                        statement.setString(5, order.getStatus().getText());

                        if (order instanceof DismissalOrder dismissalOrder) {
                            statement.setString(6, dismissalOrder.getReason());
                        }
                    }
                    statement.executeUpdate();
                }
            }
        } catch (Exception e) {
            logger.error("При попытке добавить документы в таблицу произошла ошибка", e);
            throw new DataException("Не удалось записать данные в бд с url " + url, e);
        }
    }

    private List<Document> getFromTable(Statement statement, String tableName) throws SQLException {
        String selectAllSql = String.format("""
                SELECT * FROM %s
                """, tableName);
        try (ResultSet resultSet = statement.executeQuery(selectAllSql)) {
            logger.debug("Получены строки из таблицы " + tableName);
            return converter.convert(resultSet);
        }
    }
}
