package org.example.globalgoodsindex.core.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.services.CSVReader;

import java.io.File;
import java.util.List;


public class DataHandler {

    private final ObservableList<Salaries> salaries;
    private final ObservableList<Product> products;


    public DataHandler() {
        this.salaries = FXCollections.observableArrayList(CSVReader.readCSVToList("/data/salaries/salaries.txt"));
        this.products = FXCollections.observableArrayList();
        populateProducts();

    }

    private void populateProducts() {
        File folder = new File(getClass().getResource("/data/products/").getPath());
        File[] listOfFiles = folder.listFiles();
        for (var file : listOfFiles) {
            this.products.add(new Product(file.getName()));
        }
    }

    public List<Salaries> getSalaries() {
        return salaries;
    }

    public List<Product> getProducts() {
        return products;
    }

}
