package org.example.globalgoodsindex.core.models;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.example.globalgoodsindex.core.services.CSVReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    List<Salarie> salaries;
    List<Product> products;

    //public ObservableList<XYChart.Data<Salarie, Product>> data; // update this data ?

    public void updateChart(){

    }

    public DataHandler() {
        this.salaries = CSVReader.readCSVToList("/data/salaries/salaries.txt");
        populateProducts();
    }

    private void populateProducts() {
        this.products = new ArrayList<>();
        File folder = new File(getClass().getResource("/data/products/").getPath());
        File[] listOfFiles = folder.listFiles();
        for (var file : listOfFiles) {
            this.products.add(new Product(file.getName()));
        }
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public List<Product> getProducts() {
        return products;
    }
}
