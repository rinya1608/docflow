package ru.yarullin.docflow.entity;

import ru.yarullin.docflow.entity.enums.OrderStatus;

/**
 * Абстрактный класс для представления сущности <b>Приказ о приеме на работу</b>
 *
 * @see Order
 */
public class ReceptionOrder extends Order {
    public ReceptionOrder(Integer number, String employee) {
        super(number, employee);
    }

    public ReceptionOrder(Integer number, String name, String employee, String text) {
        super(number, name, employee, text);
    }

    public ReceptionOrder(Integer number, String name, String employee, String text, OrderStatus status) {
        super(number, name, employee, text, status);
    }

    @Override
    public String toString() {
        return "number=" + number +
                ", name='" + name + '\'' +
                ", employee='" + employee + '\'' +
                ", text='" + text + '\'' +
                ", status=" + status.getText();
    }
}
