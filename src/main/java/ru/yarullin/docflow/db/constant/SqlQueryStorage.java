package ru.yarullin.docflow.db.constant;

/**
 * Класс для хранения sql запросов в виде статик констант
 */
public class SqlQueryStorage {
    public static final String CREATE_TABLE_LETTER = """
            CREATE TABLE IF NOT EXISTS letter (
            id SERIAL PRIMARY KEY,
            number INT NOT NULL,
            name VARCHAR(50) NOT NULL,
            to_emp VARCHAR(50) NOT NULL,
            from_emp VARCHAR(50) NOT NULL
            )
            """;
    public static final String CREATE_DISMISSAL_ORDER_TABLE = """
            CREATE TABLE IF NOT EXISTS dismissal_order (
            id SERIAL PRIMARY KEY,
            number INT NOT NULL,
            name VARCHAR(50) NOT NULL,
            employee VARCHAR(50) NOT NULL,
            text VARCHAR(255) NOT NULL,
            status VARCHAR(15) NOT NULL,
            reason VARCHAR(255) NOT NULL
            )
            """;
    public static final String CREATE_RECEPTION_ORDER_TABLE = """
            CREATE TABLE IF NOT EXISTS reception_order (
            id SERIAL PRIMARY KEY,
            number INT NOT NULL,
            name VARCHAR(50) NOT NULL,
            employee VARCHAR(50) NOT NULL,
            text VARCHAR(255) NOT NULL,
            status VARCHAR(15) NOT NULL
            )
            """;

    public static final String INSERT_INTO_LETTER = """
            INSERT INTO letter(number , name , to_emp, from_emp)
            VALUES(?, ?, ?, ?)
            """;
    public static final String INSERT_INTO_DISMISSAL_ORDER = """
            INSERT INTO dismissal_order (number , name , employee, text, status, reason)
            VALUES(?, ?, ?, ?, ?, ?)
            """;
    public static final String INSERT_INTO_RECEPTION_ORDER = """
            INSERT INTO reception_order (number , name , employee, text, status)
            VALUES(?, ?, ?, ?, ?)
            """;
}
