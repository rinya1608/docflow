package ru.yarullin.docflow.entity;


import ru.yarullin.docflow.entity.enums.OrderStatus;

/**
 * Абстрактный класс для представления сущности <b>Приказ</b>
 *
 * @see Document
 */
public abstract class Order extends Document {
    protected final String employee;
    protected String text;
    protected OrderStatus status = OrderStatus.CREATED;

    public Order(Integer number, String employee) {
        super(number);
        this.employee = employee;
    }

    public Order(Integer number, String name, String employee, String text) {
        super(number, name);
        this.employee = employee;
        this.text = text;
    }

    public Order(Integer number, String name, String employee, String text, OrderStatus status) {
        super(number, name);
        this.employee = employee;
        this.text = text;
        this.status = status;
    }

    public String getEmployee() {
        return employee;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public final void changeStatus() {
        if (OrderStatus.CREATED.equals(this.status))
            this.status = OrderStatus.EXECUTED;
    }
}
