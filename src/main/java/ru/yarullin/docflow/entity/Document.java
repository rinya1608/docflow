package ru.yarullin.docflow.entity;

/**
 * Абстрактный класс для представления сущности <b>Документ</b>
 */
public abstract class Document {
    protected final Integer number;
    protected String name;

    public Document(Integer number) {
        this.number = number;
    }

    protected Document(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printGeneralInformation() {
        System.out.println(
                "number=" + number +
                        ", name='" + name + '\''
        );
    }
}
