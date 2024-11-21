package org.example.globalgoodsindex.core.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.services.CSVReader;

import java.io.File;


public class DataHandler {

    private final ObservableList<Salarie> salaries;
    private final ObservableList<Product> products;

    private final ObservableList<Salarie> selectedSalaries;
    private final ObservableList<Product> selectedProducts;


    public DataHandler() {
        this.salaries = FXCollections.observableArrayList(CSVReader.readCSVToList("/data/salaries/salaries.txt"));
        this.products = FXCollections.observableArrayList();
        this.selectedSalaries = FXCollections.observableArrayList();
        this.selectedProducts = FXCollections.observableArrayList();

        populateProducts();

        // Set up listeners to dynamically track and update selected items
        // Called in the constructor to ensure selections stay in sync with the UI
        setupSelectionListeners();

    }

    private void populateProducts() {
        File folder = new File(getClass().getResource("/data/products/").getPath());
        File[] listOfFiles = folder.listFiles();
        for (var file : listOfFiles) {
            this.products.add(new Product(file.getName()));
        }
    }

    private void setupSelectionListeners() {
        // Track selected Salaries
        for (Salarie salarie : salaries) {
            salarie.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    selectedSalaries.add(salarie);
                } else {
                    selectedSalaries.remove(salarie);
                }
            });
        }

        // Track selected Products
        for (Product product : products) {
            product.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    selectedProducts.add(product);
                } else {
                    selectedProducts.remove(product);
                }
            });
        }
    }

    public ObservableList<Salarie> getSalaries() {
        return salaries;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public ObservableList<Salarie> getSelectedSalaries() {
        return selectedSalaries;
    }

    public ObservableList<Product> getSelectedProducts() {
        return selectedProducts;
    }
}
