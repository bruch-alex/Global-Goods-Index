package org.example.globalgoodsindex.core.models;

public class Salaries extends Entry {

    private final double salary;

    public Salaries(String name, double price) {
        super(name);
        this.salary = price;
    }

    public double getSalary() {
        return salary;
    }
}
