package org.example.globalgoodsindex.core.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataHandler {

    private final ObservableList<Salaries> salaries;
    private final ObservableList<Product> products;

    public DataHandler(ObservableList<Salaries> salaries, ObservableList<Product> products) {
        this.salaries = FXCollections.observableArrayList(salaries);
        this.products = FXCollections.observableArrayList(products);
    }

    public ObservableList<Salaries> getSalaries() {
        return salaries;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }
}

