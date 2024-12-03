package org.example.globalgoodsindex.models;

public class Salary extends Entry {

    private final double salary;

    public Salary(String name, double price) {
        super(name);
        this.salary = price;
    }

    public double getSalary() {
        return salary;
    }
}
