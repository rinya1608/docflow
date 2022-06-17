package ru.yarullin.docflow.entity;

import ru.yarullin.docflow.entity.enums.OrderStatus;

/**
 * Абстрактный класс для представления сущности <b>Приказ о увальнение</b>
 *
 * @see Order
 */
public class DismissalOrder extends Order {

    private String reason;

    public DismissalOrder(Integer number, String employee) {
        super(number, employee);
    }

    public DismissalOrder(Integer number, String name, String employee, String text, String reason) {
        super(number, name, employee, text);
        this.reason = reason;
    }

    public DismissalOrder(Integer number, String name, String employee, String text, OrderStatus status, String reason) {
        super(number, name, employee, text, status);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "number=" + number +
                ", name='" + name + '\'' +
                ", employee='" + employee + '\'' +
                ", text='" + text + '\'' +
                ", status=" + status.getText() +
                ", reason='" + reason + '\'';
    }
}
