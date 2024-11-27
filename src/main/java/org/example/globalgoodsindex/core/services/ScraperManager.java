package org.example.globalgoodsindex.core.services;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class ScraperManager {

    public CompletableFuture<Boolean> prepareSalariesFileAsync(String filePath) {
        return CompletableFuture.supplyAsync(() -> {
            File salariesFile = new File(filePath);


            if (!salariesFile.exists() || salariesFile.length() == 0) {
                System.out.println("Salaries file not found or empty. Starting scraping process...");
                FetchData fetchData = new FetchData();
                boolean scrapeSuccess = fetchData.scrapeSalaries(filePath);

                if (!scrapeSuccess) {
                    System.err.println("Scraping failed. Please check the URL or network connection.");
                    return false;
                }

                System.out.println("Salaries file prepared successfully.");
            } else {
                System.out.println("Salaries file exists and is valid.");
            }

            return true;
        });
    }
}
