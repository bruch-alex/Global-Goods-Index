package org.example.globalgoodsindex.core.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.models.Entry;

import java.io.*;

public class CSVReader {
    public static ObservableList<Entry> readCSV(String filePath) {
        // Access the file as a resource
        InputStream inputStream = CSVReader.class.getResourceAsStream(filePath);

        if (inputStream == null) {
            System.out.println("File not found in resources");
        }

        ObservableList<Entry> lines = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String name = data[0];
                    double number = Double.parseDouble(data[1].trim());
                    lines.add(new Entry(name, number));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

}

