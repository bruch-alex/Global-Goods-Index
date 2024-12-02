package org.example.globalgoodsindex.core.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.services.ScraperManager;

import java.util.concurrent.CompletableFuture;

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

    public static CompletableFuture<DataHandler> loadDataAsync(String salariesFilePath, String productsFilePath, ScraperManager scraperManager) {
        CompletableFuture<ObservableList<Salaries>> salariesFuture = scraperManager.prepareSalariesAsync(salariesFilePath);
        CompletableFuture<ObservableList<Product>> productsFuture = scraperManager.prepareProductsAsync(productsFilePath);

        return CompletableFuture.allOf(salariesFuture, productsFuture).thenApply(ignored -> {
            try {
                ObservableList<Salaries> salaries = salariesFuture.get();
                ObservableList<Product> products = productsFuture.get();

                if (salaries.isEmpty() || products.isEmpty()) {
                    throw new RuntimeException("Failed to prepare required data.");
                }

                return new DataHandler(salaries, products);
            } catch (Exception e) {
                throw new RuntimeException("Error while preparing data: " + e.getMessage(), e);
            }
        });
    }
}

