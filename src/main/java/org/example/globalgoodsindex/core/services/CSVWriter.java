package org.example.globalgoodsindex.core.services;

import org.example.globalgoodsindex.core.models.Salaries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVWriter {

    private static final String OUTPUT_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/data";

    public static void writeSalariesToCSV(List<Salaries> salaries) {
        String filePath = OUTPUT_DIRECTORY + "/salaries/salaries.csv";
        System.out.println("Writing salaries to: " + filePath);
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Salaries salary : salaries) {
                writer.println(salary.getName() + "," + salary.getSalary());
            }
            System.out.println("Salaries saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    public static void writeProductsToCSV(List<List<String>> products) {
        String filePath = OUTPUT_DIRECTORY + "/products/products.csv";
        System.out.println("Writing products to: " + filePath);
        File file = new File(filePath);
        file.getParentFile().mkdirs();


        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write header row
            if (!products.isEmpty()) {
                writer.println(String.join(",", products.get(0)));
            }

            // Write data rows
            for (int i = 1; i < products.size(); i++) {
                writer.println(String.join(",", products.get(i)));
            }

            System.out.println("Products saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }
}