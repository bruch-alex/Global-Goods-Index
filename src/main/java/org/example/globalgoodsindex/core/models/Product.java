package org.example.globalgoodsindex.core.models;

import org.example.globalgoodsindex.core.services.CSVReader;

import java.util.List;

public class Product extends Entry{

    List<Salaries> prices;

    public Product(String name) {
        super(name.replace(".txt", ""));
        this.prices = CSVReader.readCSVToList("/data/products/" + name);
    }

    public List<Salaries> getPrices() {
        return prices;
    }

    @Override
    public String toString() {
        return "\n\nProduct{" +
                "name='" + this.nameProperty + '\'' +
                ", prices=" + prices +
                '}';
    }

    public double getPrice(String countryName){
        return prices.stream()
                .filter(s -> s.getName().equals(countryName))
                .toList()
                .getFirst()
                .getSalary();
    }
}
