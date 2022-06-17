package ru.yarullin.docflow.exceptions;

public class DataException extends RuntimeException {

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public void printMessage(){
        System.out.println(getMessage());
    }
}
