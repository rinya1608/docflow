package ru.yarullin.docflow.converter;

/**
 * Итерфейс для преобразования из одного типа в другой
 *
 * @param <V> тип входного объекта
 * @param <T> тип выходного объекта
 */
public interface Converter<T, V> {
    /**
     * Метод конвертирует объект типа V в объект типа T
     *
     * @param v объект исходного типа
     * @return выходной объект типа T
     */
    T convert(V v);
}
