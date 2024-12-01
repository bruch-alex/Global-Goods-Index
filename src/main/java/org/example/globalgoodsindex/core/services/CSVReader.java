package org.example.globalgoodsindex.core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.models.Product;
import org.example.globalgoodsindex.core.models.Salaries;
import org.example.globalgoodsindex.core.utils.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSVReader {
    public static ObservableList<Salaries> readCSVToList(String filePath) {
        InputStream inputStream = CSVReader.class.getResourceAsStream(filePath);

        if (inputStream == null) {
            System.out.println("File not found in resources: " + filePath);
            return FXCollections.observableArrayList(); // return an empty list to prevent null issues
        }

        ObservableList<Salaries> lines = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String name = data[0].replace(" ", "_");
                    double number = Double.parseDouble(data[1].trim());
                    lines.add(new Salaries(name, number));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.printf("CSVReader class: Found %d lines in %s file", lines.size(), filePath);
        return lines;
    }

    public static Map<String, List<Salaries>> readCSVForProducts(String filePath) throws IOException {
        Map<String, List<Salaries>> productData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(CSVReader.class.getResourceAsStream(filePath)))) {
            String headerLine = br.readLine();
            if (headerLine == null) throw new IOException("CSV file is empty");

            List<String> headers = StringUtils.extractQuotedValues(headerLine);
            initializeProductKeys(headers, productData);

            String line;
            while ((line = br.readLine()) != null) {
                processProductRow(line, headers, productData);
            }
        }
        return productData;
    }

    private static void initializeProductKeys(List<String> headers, Map<String, List<Salaries>> productData) {
        for (int i = 1; i < headers.size(); i++) {
            String key = StringUtils.generateKey(headers.get(i).trim());
            productData.put(key, new ArrayList<>());
        }
    }

    private static void processProductRow(String line, List<String> headers, Map<String, List<Salaries>> productData) {
        String[] parts = line.split(",", -1);
        if (parts.length < headers.size()) return;

        String country = parts[0].trim();
        for (int i = 1; i < parts.length; i++) {
            String productKey = StringUtils.generateKey(headers.get(i).trim());
            String priceStr = parts[i].trim();

            if (!priceStr.isEmpty() && !"-".equals(priceStr)) {
                try {
                    double price = Double.parseDouble(priceStr);
                    Salaries salaryEntry = new Salaries(country, price);
                    productData.computeIfAbsent(productKey, k -> new ArrayList<>()).add(salaryEntry);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid price format for product: " + headers.get(i) + ", country: " + country);
                }
            }
        }
    }
}

//    public static Map<String, List<Salaries>> readProductsFromCSV(String filePath) throws IOException {
//        Map<String, List<Salaries>> productData = new HashMap<>();
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(CSVReader.class.getResourceAsStream(filePath)))) {
//            String headerLine = br.readLine(); // Read the header line
//            if (headerLine == null) {
//                throw new IOException("CSV file is empty");
//            }
//
//            List<String> headers = StringUtils.extractQuotedValues(headerLine); // Extract headers
//            for (int i = 1; i < headers.size(); i++) { // Skip the first column (Country)
//                String key = StringUtils.generateKey(headers.get(i).trim());
//                productData.put(key, new ArrayList<>());
//            }
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",", -1); // Preserve empty values
//                if (parts.length < headers.size()) {
//
//                    continue;
//                }
//
//                String country = parts[0].trim(); // First column is the country
//                for (int i = 1; i < parts.length; i++) {
//                    String productKey = StringUtils.generateKey(headers.get(i).trim());
//                    String priceStr = parts[i].trim();
//
//                    if ("-".equals(priceStr) || priceStr.isEmpty()) {
//
//                        continue;
//                    }
//
//                    try {
//                        double price = Double.parseDouble(priceStr);
//                        Salaries salaryEntry = new Salaries(country, price);
//                        productData.computeIfAbsent(productKey, k -> new ArrayList<>()).add(salaryEntry);
//                    } catch (NumberFormatException e) {
//
//                    }
//                }
//            }
//        }
//
//        return productData;
//    }


