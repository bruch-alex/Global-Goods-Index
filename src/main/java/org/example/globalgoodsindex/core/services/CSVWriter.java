package org.example.globalgoodsindex.core.services;

import org.example.globalgoodsindex.core.models.Product;
import org.example.globalgoodsindex.core.models.Salaries;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVWriter {

    public static void writeSalariesToCSV(List<Salaries> salaries) {
        String filePath = "src/main/resources/data/salaries/salaries.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Salaries salary : salaries) {
                writer.println(salary.getName() + "," + salary.getSalary());
            }
            System.out.println("Salaries saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    public static void writeProductsToCSV(List<List<String>> data) {
        String filePath = "src/main/resources/data/products/products.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write header row
            if (!data.isEmpty()) {
                writer.println(String.join(",", data.get(0))); // First row as header
            }

            // Write data rows
            for (int i = 1; i < data.size(); i++) {
                writer.println(String.join(",", data.get(i)));
            }

            System.out.println("Products saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }
}