package ru.yarullin.docflow.executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yarullin.docflow.analyzer.DocumentAnalyzer;
import ru.yarullin.docflow.db.PostgresqlManager;
import ru.yarullin.docflow.entity.Document;
import ru.yarullin.docflow.exceptions.DataException;
import ru.yarullin.docflow.executors.enums.Command;
import ru.yarullin.docflow.reader.DBReader;
import ru.yarullin.docflow.reader.JsonReader;
import ru.yarullin.docflow.reader.Reader;
import ru.yarullin.docflow.writer.DBWriter;
import ru.yarullin.docflow.writer.JsonWriter;
import ru.yarullin.docflow.writer.Writer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yarullin.docflow.executors.enums.Command.*;

/**
 * Класс для выполнения команд
 *
 * @see ru.yarullin.docflow.executors.Executor
 */
public class CommandExecutor implements Executor<String> {

    private static final Logger logger = LogManager.getLogger(CommandExecutor.class);

    private final List<Document> documents;
    private final Map<String, Reader<Document>> readerByCmd;
    private final Map<String, Writer<Document>> writerByCmd;


    public CommandExecutor(List<Document> documents) {
        this.documents = documents;
        readerByCmd = new HashMap<>();
        writerByCmd = new HashMap<>();
        initReader();
        initWriter();

    }

    private void initReader() {
        readerByCmd.put("json", new JsonReader());
        readerByCmd.put("psql", new DBReader(new PostgresqlManager()));
    }

    private void initWriter() {
        writerByCmd.put("json", new JsonWriter());
        writerByCmd.put("psql", new DBWriter(new PostgresqlManager()));
    }

    /**
     * Метод выполняющий команды
     *
     * @param cmd команда
     */
    @Override
    public void execute(String cmd) {
        String[] attrs = cmd.split(" ");
        try {
            if (attrs.length > 0) {
                Command command = getByName(attrs[0]);
                if (command != null && command.getMinCmdLength() == attrs.length) {
                    logger.debug("Выполнение команды " + command);
                    if (HELP.equals(command)) {
                        help();
                    } else if (READ.equals(command)) {
                        documents.addAll(readerByCmd.get(attrs[1]).read(attrs[2]));
                    } else if (WRITE.equals(command)) {
                        writerByCmd.get(attrs[1]).write(attrs[2], documents);
                    } else if (VIEW.equals(command)) {
                        System.out.println(documents);
                    } else if (CLEAR.equals(command)) {
                        clear();
                    } else if (STAT.equals(command)) {
                        if ("type".equalsIgnoreCase(attrs[1]))
                            System.out.println(DocumentAnalyzer.analyzeDocumentByType(documents));
                        else if ("employee".equalsIgnoreCase(attrs[1]))
                            System.out.println(DocumentAnalyzer.analyzeDocumentByEmployee(documents));
                        else System.out.println("Такой статистики не существует. Обратитесь к команде help");
                    }
                } else
                    System.out.println("Такой команды не существует. Введите help что бы посмотреть доступные команды.");
            }
        } catch (DataException e) {
            logger.error(e.getMessage(), e);
            e.printMessage();
        }
        catch (Exception e) {
            String message = "При выполнении команды " + cmd + " произошла ошибка";
            System.out.println(message);
            logger.error(message, e);

        }
    }

    /**
     * Метод выполняющий команду help
     */
    protected void help() {
        StringBuilder builder = new StringBuilder();
        builder.append("---------------- Доступные типы хранилищ ----------------\n");
        builder.append("json - файл формата json\n");
        builder.append("psql - база данных postgresql\n");
        builder.append("---------------- Доступная статистика ----------------\n");
        builder.append("type - количество документов по типу\n");
        builder.append("employee - количество документов по сотруднику\n");
        builder.append("---------------- Команды ----------------\n");
        Arrays.stream(values()).forEach(command -> builder.append(command.toString()).append("\n"));
        System.out.println(builder);
    }

    /**
     * Метод выполняющий команду clear
     */
    protected void clear() {
        documents.clear();
    }
}
