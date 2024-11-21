package org.example.globalgoodsindex.core.models;

public class Salarie extends Entry{

    private final double salary;

    public Salarie(String name, double price) {
        super(name);
        this.salary = price;
    }

    public double getSalary() {
        return salary;
    }
}
