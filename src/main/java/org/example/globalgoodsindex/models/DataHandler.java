package org.example.globalgoodsindex.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.services.CSVReader;
import org.example.globalgoodsindex.services.CSVWriter;
import org.example.globalgoodsindex.services.FetchData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataHandler {

    // Load data
    private final String SALARIES_RESOURCE_PATH = "/data/salaries/salaries.csv";
    private final String PRODUCTS_RESOURCE_PATH = "/data/products/products.csv";

    private ObservableList<Salary> salaries;
    private ObservableList<Product> products;

    public DataHandler() {
        this.salaries = FXCollections.observableArrayList();
        this.products = FXCollections.observableArrayList();
        initialization();
        //parseProducts(FetchData.scrapeProducts());
    }

    private void initialization(){
        var salariesList = FetchData.scrapeSalaries();
        var productsData = FetchData.scrapeProducts();

        if(salariesList == null){
            this.salaries = FXCollections.observableArrayList(CSVReader.readCSVToList(SALARIES_RESOURCE_PATH));
        } else {
            this.salaries = FXCollections.observableArrayList(salariesList);
            CSVWriter.writeSalariesToCSV(this.salaries);
        }

        if (productsData == null){
            List<Product> temp;
            try{
                temp = CSVReader.readProductsFromCSV(PRODUCTS_RESOURCE_PATH);
                this.products = FXCollections.observableArrayList(temp);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            parseProducts(productsData);
            CSVWriter.writeProductsToCSV(productsData);
        }
    }

    public void parseProducts(List<List<String>> products) {
        products.forEach(System.out::println);
        for (int i = 0; i < products.getFirst().size(); i++) {
            Product p = new Product(products.getFirst().get(i));

            List<Salary> tempPrices = new ArrayList<>();

            for (int j = 1; j < products.size(); j++) {
                String countryName = products.get(j).getFirst();
                double price = products.get(j).get(i + 1).equals("-") ? -1 : Double.parseDouble(products.get(j).get(i + 1));
                tempPrices.add(new Salary(countryName, price));
            }
            p.addPrices(tempPrices);
            this.products.add(p);
        }
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    public List<Product> getProducts() {
        return products;
    }

}
