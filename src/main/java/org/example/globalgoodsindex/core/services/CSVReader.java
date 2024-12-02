package org.example.globalgoodsindex.core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.models.Salary;

import java.io.*;
import java.util.List;

public class CSVReader {
    public static List<Salary> readCSVToList(String filePath){
        InputStream inputStream = CSVReader.class.getResourceAsStream(filePath);

        if (inputStream == null) {
            System.out.println("File not found in resources");
            return null;
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

}

