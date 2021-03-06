package ru.yarullin.docflow;


import ru.yarullin.docflow.entity.Document;
import ru.yarullin.docflow.executors.CommandExecutor;
import ru.yarullin.docflow.ui.ConsoleUI;
import ru.yarullin.docflow.ui.UserInterface;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainApp {

    public static void main(String[] args) {
        List<Document> documents = new CopyOnWriteArrayList<>();

        UserInterface ui = new ConsoleUI(new CommandExecutor(documents));
        ui.start();


    }
}
