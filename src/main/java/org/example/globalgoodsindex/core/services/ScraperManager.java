package org.example.globalgoodsindex.core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.models.Salaries;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

// TODO: ProgressBar: inform till the data is loaded
public class ScraperManager {

    public CompletableFuture<ObservableList<Salaries>> prepareSalariesAsync(String filePath) {
        return CompletableFuture.supplyAsync(() -> {
            File csvFile = new File(filePath);

            if (csvFile.exists() && csvFile.length() > 0) {
                System.out.println("Salaries file found. Loading data from CSV...");
                ObservableList<Salaries> salaries = CSVReader.readCSVToList(filePath);
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
        });
    }

    public CompletableFuture<Map<String, List<Salaries>>> prepareProductsAsync(String filePath) {
        return CompletableFuture.supplyAsync(() -> {
            File csvFile = new File(filePath);

            if (csvFile.exists() && csvFile.length() > 0) {
                System.out.println("CSV file found. Loading product data...");
                try {
                    return CSVReader.readCSVForProducts(filePath);
                } catch (IOException e) {
                    System.err.println("Error reading product CSV: " + e.getMessage());
                }
            }

            System.out.println("CSV file not found. Starting scraping process...");
            try {
                FetchData fetchData = new FetchData();
                List<List<String>> scrapedData = fetchData.scrapeProducts();

                CSVWriter.writeProductsToCSV(scrapedData);

                return CSVReader.readCSVForProducts(filePath);
            } catch (Exception e) {
                System.err.println("Scraping failed: " + e.getMessage());
                return new HashMap<>();
            }
        });
    }

}
