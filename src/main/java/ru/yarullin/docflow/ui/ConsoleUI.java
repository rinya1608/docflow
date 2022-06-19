package ru.yarullin.docflow.ui;

import ru.yarullin.docflow.executors.Executor;
import ru.yarullin.docflow.executors.enums.Command;

import java.util.Scanner;

/**
 * Класс консольного интерфейса
 *
 * @see ru.yarullin.docflow.ui.UserInterface
 */
public class ConsoleUI implements UserInterface {

    private final Executor<String> executor;

    public ConsoleUI(Executor<String> commander) {
        this.executor = commander;
    }

    @Override
    public void start() {
        try {
            while (true) {
                System.out.print("docflow: ");
                Scanner in = new Scanner(System.in);
                String cmd = in.nextLine();
                if (cmd.equalsIgnoreCase(Command.EXIT.getName())) break;
                executor.execute(cmd);
            }
        } finally {
            executor.shutdown();
        }
    }
}
