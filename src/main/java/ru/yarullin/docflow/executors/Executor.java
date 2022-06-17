package ru.yarullin.docflow.executors;

/**
 * Интерфейс Исполнителя
 *
 * @param <T> Тип исполняемого события
 */
public interface Executor<T> {
    /**
     * Метод исполнения событий
     *
     * @param t исполняемое событие
     */
    void execute(T t);
}
