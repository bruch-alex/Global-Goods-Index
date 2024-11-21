package org.example.globalgoodsindex.core.models;

import org.example.globalgoodsindex.core.services.CSVReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    List<Salarie> salaries;
    List<Product> products;

    public DataHandler() {
        this.salaries = CSVReader.readCSVToList("/data/salaries/salaries.txt");
        populateProducts();
    }

    private void populateProducts() {
        this.products = new ArrayList<>();

        //System.out.println("dataHandler getPath: " + Files.isDirectory(Path.of(getClass().getResource("/data/products/").getPath())));
        File folder = new File(getClass().getResource("/data/products/").getPath());
        File[] listOfFiles = folder.listFiles();
        for (var file : listOfFiles) {
            this.products.add(new Product(file.getName()));
        }
        //System.out.println("Done");
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public List<Product> getProducts() {
        return products;
    }
}
