package org.example.globalgoodsindex.core.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.services.CSVReader;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class DataHandler {

    private final ObservableList<Salaries> salaries;
    private final ObservableList<Product> products;

    public DataHandler() {

        this.salaries = FXCollections.observableArrayList();
        populateCountries();

        this.products = FXCollections.observableArrayList();
        populateProducts();
    }


    private void populateCountries() {
        try {
            List<Salaries> loadedSalaries = CSVReader.readCSVToList("/data/salaries/salaries.csv");
            if (loadedSalaries.isEmpty()) {
                throw new IOException("Salaries CSV is empty.");
            }
            salaries.setAll(loadedSalaries);
        } catch (IOException e) {
            System.err.println("Error loading salaries: " + e.getMessage());
        }
    }


    private void populateProducts() {
        try {
            File folder = new File(getClass().getResource("/data/products/").getPath());
            File[] listOfFiles = folder.listFiles();
            for (var file : listOfFiles) {
                this.products.add(new Product(file.getName()));
            }
        } catch (NullPointerException e) {
            System.out.println("Products folder not found.");
        }
    }

    public List<Salaries> getSalaries() {
        return salaries;
    }

    public List<Product> getProducts() {
        return products;
    }
}

