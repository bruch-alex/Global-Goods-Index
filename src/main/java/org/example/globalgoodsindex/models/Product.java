package org.example.globalgoodsindex.models;

import java.util.ArrayList;
import java.util.List;

public class Product extends Entry {

    public final String databaseName;
    protected final List<Salary> prices;

    public Product(String name) {
        super(name);
        this.prices = new ArrayList<>();
        databaseName = name;
    }

    public List<Salary> getPrices() {
        return prices;
    }

    @Override
    public String toString() {
        return "\n\nProduct{" +
                "name='" + this.nameProperty + '\'' +
                ", prices=" + prices +
                '}';
    }

    public double getPrice(String countryName) {
        return prices.stream()
                .filter(s -> s.getName().equalsIgnoreCase(countryName))
                .toList()
                .getFirst()
                .getSalary();
    }

    public void addPrices(List<Salary> prices) {
        this.prices.addAll(prices);
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
