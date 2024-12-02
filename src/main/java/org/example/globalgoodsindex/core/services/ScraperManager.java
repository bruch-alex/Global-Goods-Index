package org.example.globalgoodsindex.core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.models.Product;
import org.example.globalgoodsindex.core.models.Salaries;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// TODO: ProgressBar: inform till the data is loaded
public class ScraperManager {

    public CompletableFuture<ObservableList<Salaries>> prepareSalariesAsync(String resourcePath) {
        return CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis();

            try {
                URL resourceUrl = getClass().getResource(resourcePath);
                System.out.println("resourceUrl => prepareSalariesAsync: " + resourceUrl);

                if (resourceUrl != null) {
                    System.out.println("Salaries resource found. Loading data from resource...");
                    ObservableList<Salaries> salaries = CSVReader.readCSVToList(resourcePath);
                    return salaries;
                }

                System.out.println("No valid salaries file found. Starting scraping process...");
                FetchData fetchData = new FetchData();
                List<Salaries> scrapedData = fetchData.scrapeSalaries();

                ObservableList<Salaries> salaries = FXCollections.observableArrayList(scrapedData);

                if (salaries.isEmpty()) {
                    System.err.println("Scraping failed or returned no data. Please check the URL or network connection.");
                    return FXCollections.observableArrayList();
                }

                System.out.println("Saving scraped salaries to CSV...");
                CSVWriter.writeSalariesToCSV(salaries);

                return salaries;
            } finally {
                long endTime = System.currentTimeMillis();
                System.out.println("Salaries preparation took: " + (endTime - startTime) + "ms");
            }
        });
    }

    public CompletableFuture<ObservableList<Product>> prepareProductsAsync(String resourcePath) {
        return CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis(); // Start timing
            try {
                URL resourceUrl = getClass().getResource(resourcePath);
                System.out.println("resourceUrl => prepareProductsAsync: " + resourceUrl);

                if (resourceUrl != null) {
                    System.out.println("Products file found. Loading data from CSV...");
                    List<Product> products = null;
                    try {
                        products = CSVReader.readProductsFromCSV(resourcePath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return FXCollections.observableArrayList(products);

                }

                System.out.println("No valid products file found. Starting scraping process...");
                FetchData fetchData = new FetchData();
                List<List<String>> scrapedData = fetchData.scrapeProducts();

                if (scrapedData.isEmpty()) {
                    System.err.println("Scraping failed or returned no data. Please check the URL or network connection.");
                    return FXCollections.observableArrayList();
                }


                System.out.println("Saving scraped products to CSV...");
                CSVWriter.writeProductsToCSV(scrapedData);

                try {
                    List<Product> products = CSVReader.readProductsFromCSV(resourcePath);
                    return FXCollections.observableArrayList(products);
                } catch (IOException e) {
                    System.err.println("Error in prepareProductsAsync: " + e.getMessage());
                    e.printStackTrace();
                    return FXCollections.observableArrayList(); // Return empty list on error
                }
            } finally {
                long endTime = System.currentTimeMillis(); // End timing
                System.out.println("prepareProductsAsync completed in: " + (endTime - startTime) + "ms");
            }
        });
    }
}
