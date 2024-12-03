package org.example.globalgoodsindex.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.helpers.StringUtils;
import org.example.globalgoodsindex.models.Product;
import org.example.globalgoodsindex.models.Salary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static ObservableList<Salary> readCSVToList(String filePath) throws IllegalArgumentException{
        InputStream inputStream = CSVReader.class.getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found: " + filePath);
        }

        ObservableList<Salary> lines = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String countryName = data[0].replace(" ","_");
                    double number = Double.parseDouble(data[1].trim());
                    lines.add(new Salary(countryName, number));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.printf("CSVReader class: Found %d lines in %s file", lines.size(), filePath);
        return lines;
    }

    public static List<Product> readProductsFromCSV(String resourcePath) throws IOException {
        InputStream inputStream = CSVReader.class.getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found in resources: " + resourcePath);
        }

        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IOException("CSV file is empty");
            }

            // Extract headers (product names) and initialize products
            List<String> headers = StringUtils.extractQuotedValues(headerLine);
            for (int i = 1; i < headers.size(); i++) { // Skip the Country
                String productName = StringUtils.generateKey(headers.get(i).trim());
                products.add(new Product(productName));
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1); // preserve empty values
                if (parts.length < headers.size()) {
                    continue;
                }

                String country = parts[0].trim();
                for (int i = 1; i < parts.length; i++) {
                    String priceStr = parts[i].trim();

                    if ("-".equals(priceStr) || priceStr.isEmpty()) {
                        continue;
                    }

                    try {
                        double price = Double.parseDouble(priceStr);
                        Salary salaryEntry = new Salary(country, price);

                        products.get(i - 1).getPrices().add(salaryEntry);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid price format for product: " + headers.get(i) + ", country: " + country);
                    }
                }
            }
        }

        return products;
    }

}

