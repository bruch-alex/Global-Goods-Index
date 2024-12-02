package org.example.globalgoodsindex.core.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.services.CSVReader;

import java.util.List;

public class Product extends Entry{

    protected final ObservableList<Salary> prices;

    public Product(String name) {
        super(name.replace(".txt", ""));
        this.prices = FXCollections.observableArrayList(CSVReader.readCSVToList("/data/products/" + name));
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

    public double getPrice(String countryName){
        return prices.stream()
                .filter(s -> s.getName().equalsIgnoreCase(countryName))
                .toList()
                .getFirst()
                .getSalary();
    }
}
